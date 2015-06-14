import java.math.BigInteger;
import java.util.ArrayList;

public class Board {

    public static int[][] data;
    public static int height;
    public static int width;
    public static int depth;

    public Board(int m, int n) {
        Board.height = m > n ? m : n;
        Board.width = n < m ? n : m;

    }

    public BigInteger calculateMutations() {
        Board.data = new int[height][width];
        Area area = new Area(0, 0, width, height);
        depth = 1;
        return processNextPosition(findNextPosition(area), area);
    }

    BigInteger processNextPosition(Integer[] position, Area area) {
        BigInteger result = BigInteger.ZERO;
        if (position == null) {
            if (isFull(area)) {
                return BigInteger.ONE;
            }
            if (!area.solvable()) {
                return BigInteger.ZERO;
            }
            Area[] areas = shrinkArea(area);
            if (areas[0].solvable() && areas[1].solvable()) {
                BigInteger resultA = processNextPosition(findNextPosition(areas[0]), areas[0]);
                BigInteger resultB = processNextPosition(findNextPosition(areas[1]), areas[1]);
                return resultA.multiply(resultB);
            } else {
                return BigInteger.ZERO;
            }
        }
        BigInteger cacheValue = Tetris.getCache(data, area);
        if (cacheValue != null) {
            return cacheValue;
        }
        for (Block block : Tetris.blocks) {
            ArrayList<Integer[]> validOffsets = findValidOffsets(block, position);
            for (Integer[] offset : validOffsets) {
                placeBlockAt(block, offset);
                print(result, area);
                if (isFull(area)) {
                    result = result.add(BigInteger.ONE);
                } else {
                    Integer[] nextPosition = findNextPosition(area);
                    if (nextPosition == null) {
                        Area[] areas = shrinkArea(area);

                        if (areas[0].solvable() && areas[1].solvable()) {
                            BigInteger resultA = processNextPosition(findNextPosition(areas[0]), areas[0]);
                            BigInteger resultB = processNextPosition(findNextPosition(areas[1]), areas[1]);
                            result = result.add(resultA.multiply(resultB));
                            if (depth == 1 && areas[0].freeBlocks() == 4 * 3) {
                                print(resultA, areas[0]);
                                print(resultB, areas[1]);
                                print(resultA.multiply(resultB), area);
                            }

                        }

                    } else {
                        result = result.add(processNextPosition(nextPosition, area));
                    }
                }
                print(result, area);
                removeBlockAt(block, offset);
            }
        }
        Tetris.setCache(data, result, area);
        return result;
    }

    private Area[] shrinkArea(Area area) {
        Area[] areas = new Area[2];
        if (area.width < area.height) {
            areas[0] = new Area(area.x1, area.y1, area.x2, area.y1 + area.height / 2);
            areas[1] = new Area(area.x1, area.y1 + area.height / 2, area.x2, area.y2);
        } else {
            areas[0] = new Area(area.x1, area.y1, area.x1 + area.width / 2, area.y2);
            areas[1] = new Area(area.x1 + area.width / 2, area.y1, area.x2, area.y2);
        }
        return areas;
    }

    protected Integer[] findNextPosition(Area area) {
        if (area.width < 4 || area.height < 4) {
            for (int i = area.y1; i < area.y2; i++) {
                for (int j = area.x1; j < area.x2; j++) {
                    if (data[i][j] == 0) {
                        return new Integer[]{i, j};
                    }
                }
            }
            return null;
        }

        if (area.width < area.height) {
            int j = area.y1 + area.height / 2;
            for (int i = area.x1; i < area.x2; i++) {
                if (data[j][i] == 0) {
                    return new Integer[]{j, i};
                }
            }
        } else {
            int j = area.x1 + area.width / 2;
            for (int i = area.y1; i < area.y2; i++) {
                if (data[i][j] == 0) {
                    return new Integer[]{i, j};
                }
            }
        }
        return null;
    }

    protected void placeBlockAt(Block block, Integer[] offset) {
        for (int i = 0; i < block.width; i++) {
            for (int j = 0; j < block.height; j++) {
                if (block.data[i][j] != 0) {
                    data[i + offset[0]][j + offset[1]] = block.data[i][j];
                }
            }
        }
    }

    protected void removeBlockAt(Block block, Integer[] offset) {
        for (int i = 0; i < block.width; i++) {
            for (int j = 0; j < block.height; j++) {
                if (block.data[i][j] != 0) {
                    data[i + offset[0]][j + offset[1]] = 0;
                }
            }
        }
    }

    ArrayList<Integer[]> findValidOffsets(Block block, Integer[] pos) {
        ArrayList<Integer[]> validOffsets = new ArrayList<Integer[]>();
        for (int[] coordinate : block.coordinates) {
            Integer[] offset = new Integer[]{
                    pos[0] - coordinate[0],
                    pos[1] - coordinate[1]
            };
            if (blockPlaceableAt(block, offset)) {
                validOffsets.add(offset);
            }
        }
        return validOffsets;
    }

    private boolean blockPlaceableAt(Block block, Integer[] offset) {
        if (offset[0] < 0 || offset[1] < 0) {
            return false;
        }
        for (int i = 0; i < block.width; i++) {
            for (int j = 0; j < block.height; j++) {
                if (i + offset[0] >= height) {
                    // out of bounds
                    if (block.data[i][j] != 0) {
                        // but the block might have a zero here, so
                        // just return if this is not the case
                        return false;
                    } else {
                        continue;
                    }
                }
                if (j + offset[1] >= width) {
                    // out of bounds
                    if (block.data[i][j] != 0) {
                        // but the block might have a zero here, so
                        // just return if this is not the case
                        return false;
                    } else {
                        continue;
                    }
                }
                if (block.data[i][j] != 0 && data[i + offset[0]][j + offset[1]] != 0) {
                    // there is already a block; can't place this one
                    return false;
                }
            }
        }
        // there was no collision with the board; block can be placed
        return true;
    }

    private boolean isFull(Area area) {
        for (int i = area.x1; i < area.x2; i++) {
            for (int j = area.y1; j < area.y2; j++) {
                if (data[j][i] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    void printArea(BigInteger result, Area area) {
        if (!Tetris.debugPrint) {
            return;
        }
        System.out.println();
        for (int i = area.y1; i < area.y2; i++) {
            for (int j = area.x1; j < area.x2; j++) {
                int value = data[i][j];
                System.out.format("\u001B[4%dm %d \u001B[0m", value, value);
            }
            System.out.println();
        }
        System.out.format("Solutions: %d\n", result);
        try {
            Thread.sleep(Tetris.printDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void print(BigInteger result) {
        print(result, new Area(0, 0, width, height));
    }

    public static void print(BigInteger result, Area area) {
        if (!Tetris.debugPrint || depth > 1) {
            return;
        }
        System.out.println();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int value = data[i][j];
                String paddingChar = "";
                String content = "   ";
                int color = 4; // dark bg colors
                if (i >= area.y1 && i < area.y2 && j >= area.x1 && j < area.x2) {
//                    paddingChar = "\u001B[30m"; // black font
                    content = String.format(" %d ", value);
//                    if (value != 0) {
//                        color = 10; // bright bg colors
//                    }
                }
                System.out.format("\u001B[%d%dm%s%s\u001B[0m", color, value, paddingChar, content);
            }
            System.out.println();
        }
        System.out.format("Solutions: %d\n", result);
        try {
            Thread.sleep(Tetris.printDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
