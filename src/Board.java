import java.math.BigInteger;
import java.util.ArrayList;

public class Board {

    public static BoardData boardData;
    public static int height;
    public static int width;

    public Board(int m, int n) {
        if (m < 3 || n > m) {
            Board.height = n;
            Board.width = m;
        } else {
            Board.height = m;
            Board.width = n;
        }
        Board.boardData = new BoardData();
    }

    public BigInteger calculateMutations() {
        if (Board.width > 32) {
            // not possible to calculate since we are using an
            // int array to hold the data. Use long for bigger sizes
            return BigInteger.ZERO;
        }
        return calculateMutations(new Area(0, 0, width, height));
    }

    public BigInteger calculateMutations(Area area) {
        BigInteger result;
        int[] position;
        position = findNextPosition(area);
        if (position == null) {
            return area.isFull() ? BigInteger.ONE : BigInteger.ZERO;
        }
        area = new Area(area.x1, position[0], area.x2, area.y2);

        result = Tetris.getCache(area);
        if (result != null) {
            return result;
        }

        result = BigInteger.ZERO;
        int[] pos;
        for (Block block : Tetris.blocks) {
            pos = findValidOffset(block, position);
            if (pos[0] != -1) {
                placeBlockAt(block, pos);
                result = result.add(calculateMutations(area));
                removeBlockAt(block, pos);
            }
        }
//        if (area.height == 7) {
//            print(result, area);
//        }
//        System.out.println(area.height);
        Tetris.setCache(result, area);
        return result;
    }

    int[] findValidOffset(Block block, int[] pos) {
        for (int[] coordinate : block.coordinates) {
            int[] offset = new int[]{
                    pos[0] - coordinate[0],
                    pos[1] - coordinate[1]
            };
            if (blockPlaceableAt(block, offset)) {
                return offset;
            }
        }
        return new int[]{-1, -1};
    }

    int[] findNextPosition(Area area) {
        int row;
        for (int i = area.y1; i < area.y2; i++) {
            row = boardData.get(i);
            for (int j = area.x1; j < area.x2; j++) {
                if ((row & 1 << j) == 0) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    protected void placeBlockAt(Block block, int[] position) {
        Tetris.setBlocks++;
        for (int i = 0; i < block.height; i++) {
            for (int j = 0; j < block.width; j++) {
                if (block.data[i][j]) {
                    boardData.set(i + position[0], j + position[1], true);
                }
            }
        }
    }

    protected void removeBlockAt(Block block, int[] position) {
        for (int i = 0; i < block.height; i++) {
            for (int j = 0; j < block.width; j++) {
                if (block.data[i][j]) {
                    boardData.set(i + position[0], j + position[1], false);
                }
            }
        }
    }

    private boolean blockPlaceableAt(Block block, int[] offset) {
        if (offset[0] < 0 || offset[1] < 0) {
            return false;
        }
        for (int i = 0; i < block.height; i++) {
            for (int j = 0; j < block.width; j++) {
                if (i + offset[0] >= Board.height) {
                    // out of bounds
                    if (block.data[i][j]) {
                        // but the block might have a zero here, so
                        // just return if this is not the case
                        return false;
                    } else {
                        continue;
                    }
                }
                if (i + offset[0] < 0) {
                    // out of bounds
                    if (block.data[i][j]) {
                        // but the block might have a zero here, so
                        // just return if this is not the case
                        return false;
                    } else {
                        continue;
                    }
                }
                if (j + offset[1] >= Board.width) {
                    // out of bounds
                    if (block.data[i][j]) {
                        // but the block might have a zero here, so
                        // just return if this is not the case
                        return false;
                    } else {
                        continue;
                    }
                }
                if (j + offset[1] < 0) {
                    // out of bounds
                    if (block.data[i][j]) {
                        // but the block might have a zero here, so
                        // just return if this is not the case
                        return false;
                    } else {
                        continue;
                    }
                }
                if (block.data[i][j] && boardData.get(i + offset[0], j + offset[1])) {
                    // there is already a block; can't place this one
                    return false;
                }
            }
        }
        // there was no collision with the board / placed outside of area; block can be placed
        return true;
    }

    public static void print(BigInteger result, Area area) {
        print(result, boardData, area);
    }

    public static void print(BigInteger result, BoardData boardData, Area area) {
        if (!Tetris.debugPrint) {
            return;
        }
        System.out.println();
        for (int i = 0; i < Math.max(height, area.height); i++) {
            for (int j = 0; j < Math.max(width, area.width); j++) {
                boolean value = boardData.get(i, j);
                String fontColor = "\u001B[30m"; // black font
                String content = String.format(" %d ", value ? 1 : 0);
                int color = 4; // dark bg colors
                if (i >= area.y1 && i < area.y2 && j >= area.x1 && j < area.x2) {
                    fontColor = "";
//                    if (value != 0) {
//                        color = 10; // bright bg colors
//                    }
                }
                System.out.format("\u001B[%d%dm%s%s\u001B[0m", color, value ? 1 : 0, fontColor, content);
            }
            System.out.println();
        }
        System.out.format("Solutions: %15d\n", result);
        try {
            Thread.sleep(Tetris.printDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
