public class Tetris {
    static Block[] blocks;
    private static long[][] cache;

    public static void main(String[] args) {
        int m = Integer.parseInt(args[0]);
        int n = Integer.parseInt(args[1]);

        System.out.println(Tetris.solve(m, n));
    }

    public static long solve(int m, int n) {
        blocks = new Block[6];
        blocks[0] = new Block(new int[][]{
                {1, 0, 0},
                {1, 0, 0},
                {1, 0, 0}
        });
        blocks[1] = new Block(new int[][]{
                {2, 2, 2},
                {0, 0, 0},
                {0, 0, 0}
        });
        blocks[2] = new Block(new int[][]{
                {3, 3, 0},
                {3, 0, 0},
                {0, 0, 0}
        });
        blocks[3] = new Block(new int[][]{
                {4, 4, 0},
                {0, 4, 0},
                {0, 0, 0}
        });
        blocks[4] = new Block(new int[][]{
                {0, 5, 0},
                {5, 5, 0},
                {0, 0, 0}
        });
        blocks[5] = new Block(new int[][]{
                {6, 0, 0},
                {6, 6, 0},
                {0, 0, 0}
        });

        Tetris.cache = new long[m > n ? m : n][m > n ? n : m];
        Board board = new Board(m, n, blocks);
        return board.calculateMutations();
    }

    public static long getCache(int m, int n) {
        if (m > n) {
            return Tetris.cache[m][n];
        } else {
            return Tetris.cache[n][m];
        }
    }

    public static void setCache(int m, int n, long value) {
        if (m > n) {
            Tetris.cache[m][n] = value;
        } else {
            Tetris.cache[n][m] = value;
        }
    }
}