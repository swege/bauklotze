import java.math.BigInteger;

/**
 * The OverlapBoard only calculates mutations which consist of blocks
 * overlapping the half of the board It will be splitted by side m,
 * remember that for calculation in Board.java!
 * <p/>
 * n
 * +------+
 * |222222|
 * |122233|v Over-
 * |122235|^ -lap   m
 * |122255|
 * +------+
 */
public class OverlapBoard extends Board {
    private Integer[][] positions;
    private int currentPosition;
    private int splitPosition;
    private BigInteger additionalResults = BigInteger.ZERO;

    public OverlapBoard(int m, int n, int splitPosition) {
        this(m, n, splitPosition, false);
    }

    public OverlapBoard(int m, int n, int splitPosition, boolean allowRotate) {
        super(m, n, allowRotate, false);
        this.positions = new Integer[n][2];
        this.currentPosition = 0;
        this.splitPosition = splitPosition;
        for (int i = 0; i < n; i++) {
            positions[i][0] = splitPosition - 1;
            positions[i][1] = i;
        }
    }

    public BigInteger calculateMutations() {
        BigInteger result = nextPosition(findNextPosition());
        return result;
    }

    @Override
    BigInteger nextPosition(Integer[] position) {
        BigInteger result = super.nextPosition(position);
        result = result.add(additionalResults);
        additionalResults = BigInteger.ZERO;
        return result;
    }

    @Override
    protected Integer[] findNextPosition() {
        int offset = 0;

        while (currentPosition + offset < positions.length) {
            Integer[] coordinate = positions[currentPosition + offset];
            int m = coordinate[0];
            int n = coordinate[1];
            if (data[m][n] == 0) {
                return coordinate;
            }
            offset++;
        }
        if (!this.correctlySplitted()) {
            return new Integer[]{-1, -1};
        }
        // the board is now separated in half;
        // we just need to calculate the combinations of the top and
        // the bottom half and multiply them
        Board topBoard = new Board(splitPosition, data[0].length, false, true);
        Board bottomBoard = new Board(height - splitPosition, data[0].length, false, true);
        // copy the data from our current board to the two new ones
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (i < splitPosition) {
                    // "7" for better visualization while debugging; could be any other number != 0
                    // mirror the data while copying; gives a little speedup
                    // due to the way the next position is being chosen
                    topBoard.data[i][j] = data[splitPosition - i - 1][j] != 0 ? 7 : 0;
//                  TODO: use this instead of the above for slightly better performance if needed:
//                  topBoard.data[i][j] = this.data[this.splitPosition - i - 1][j];
                } else {
                    // "7" for better visualization while debugging; could be any other number != 0
                    bottomBoard.data[i - splitPosition][j] = data[i][j] != 0 ? 7 : 0;
//                  TODO: use this instead of the above for slightly better performance if needed:
//                  bottomBoard.data[i - this.splitPosition][j] = this.data[i][j];
                }
            }
        }
        BigInteger top = topBoard.calculateMutations();
        BigInteger bottom = bottomBoard.calculateMutations();
        additionalResults = additionalResults.add(top.multiply(bottom));
        return new Integer[]{-1, -1};
    }

    private boolean correctlySplitted() {
        // we have now placed all blocks which are separating top and bottom rect.
        // To make sure they are overlapping, both top half and bottom half need to have
        // blocks inside them. Additionally, both of them need a number of blocks dividable
        // by 3 inside, otherwise, it won't be possible to find a solution
        int blocksInsideTopHalf = 0;
        int blocksInsideBottomHalf = 0;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (data[i][j] != 0) {
                    if (i < splitPosition) {
                        blocksInsideTopHalf++;
                    } else {
                        blocksInsideBottomHalf++;
                    }
                }
            }
        }
        if (blocksInsideTopHalf == 0 || blocksInsideBottomHalf == 0) {
            return false;
        }
        return blocksInsideTopHalf % 3 == 0;
    }

    @Override
    int[] isRect() {
        int[] res = super.isRect();
        int m = data.length;
        int n = data[0].length;
        if (res[0] == Math.max(m, n) && res[1] == Math.min(m, n)) {
            return new int[]{-1, -1};
        }
        return res;
    }

    @Override
    protected void placeBlockAt(Block block, Integer[] offset) {
        super.placeBlockAt(block, offset);
        currentPosition++;
    }

    @Override
    protected void removeBlockAt(Block block, Integer[] offset) {
        super.removeBlockAt(block, offset);
        currentPosition--;
    }
}
