import java.math.BigInteger;
import java.util.HashMap;

// After the contest is over, the sourcecode will be available at
// https://github.com/swege/bauklotze

public class TetrisS {
    public static Block[] blocks;
    private static HashMap<BoardData, BigInteger> cache;
    private static BigInteger[][] rectCache;
    public static boolean debugPrint = false;
    public static int printDelay;
    public static int setCaches;
    public static int getCaches;
    public static int setBlocks;
    public static long fooCounter;

    public static BigInteger solve(int m, int n) {
        TetrisS.blocks = new Block[6];
        /// TODO: Could use binary literals (0b00101...), but stay compatible with Java 6. Not sure what version the contest server runs
        // X      100
        // X   -> 100 -> 001001001 -> 73
        // X      100
        blocks[0] = new Block((short) 73, 3, 1);
        // XXX    111
        //     -> 000 -> 000000111 -> 7
        //        000
        blocks[1] = new Block((short) 7, 1, 3);
        // XX     110
        // X   -> 100 -> 000001011 -> 11
        //        000
        blocks[2] = new Block((short) 11, 2, 2);
        // XX     110
        //  X  -> 010 -> 000010011 -> 19
        //        000
        blocks[3] = new Block((short) 19, 2, 2);
        //  X     010
        // XX  -> 110 -> 000011010 -> 26
        //        000
        blocks[4] = new Block((short) 26, 2, 2);
        // X      100
        // XX  -> 110 -> 000011001 -> 25
        //        000
        blocks[5] = new Block((short) 25, 2, 2);

        TetrisS.cache = new HashMap<BoardData, BigInteger>();
//        Tetris.rectCache = m > n ? new BigInteger[m][n] : new BigInteger[n][m];
        TetrisS.getCaches = 0;
        TetrisS.setCaches = 0;
        TetrisS.setBlocks = 0;
        TetrisS.fooCounter = 0;
        Board board = new Board(m, n);
        TetrisS.rectCache = new BigInteger[Board.height][Board.width];
        return board.calculateMutations();
    }


    public static void setCache(BigInteger value, Area area) {
        setCaches++;
        if (area.isEmpty()) {
            TetrisS.setCache(value, area.height, area.width);
        } else {
            Board.boardData.setArea(area);
            BoardData copy = new BoardData(Board.boardData);
            TetrisS.cache.put(copy, value);
        }
    }

    private static void setCache(BigInteger value, int m, int n) {
        // decrement since you should pass the width and height of the board to the function
        rectCache[m-1][n-1  ] = value;
    }

    public static BigInteger getCache(Area area) {
        // This method returns null if there is no solution available.
        // "0" as a solution is valid, since not all boards with pre set blocks are solvable!
        long start = System.currentTimeMillis();
        BigInteger result;
        if (area.isEmpty()) {
            result = TetrisS.getCache(area.height, area.width);
        } else {
            Board.boardData.setArea(area);
            result = TetrisS.cache.get(Board.boardData);
            if (result == null) {
                BoardData copy = new BoardData(Board.boardData);
                copy.mirrorData();
                result = TetrisS.cache.get(copy);
            }
        }
        if (result != null) {
            getCaches++;
        } else {
            TetrisS.fooCounter += System.currentTimeMillis() - start;
        }
        return result;
    }

    private static BigInteger getCache(int m, int n) {
//        if (m == 0 || n == 0) {
//            return BigInteger.ONE;
//        }
        // decrement since you should pass the width and height of the board to the function
        return rectCache[m-1][n-1];
    }
}
