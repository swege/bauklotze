public class Test {

    static long[][] example_values = {
            {3, 1, 1},
            {3, 2, 3},
            {3, 3, 10},
            {3, 4, 23},
            {3, 5, 62},
            {6, 1, 1},
            {6, 2, 11},
            {6, 3, 170},
            {6, 4, 939},
            {6, 5, 8342},
            {6, 6, 80092},
            {6, 7, 614581},
            {6, 8, 5271923},
            {9, 1, 1},
            {9, 2, 41},
            {9, 3, 3127},
            {9, 4, 41813},
            {9, 5, 1269900},
            {9, 6, 45832761},
//            {9, 7, 0},
//            {9, 8, 0},
//            {9, 9, 0},
//            {9, 10, 0},
//            {9, 11, 0},
            {12, 1, 1},
            {12, 2, 153},
            {12, 3, 58234},
            {12, 4, 1895145},
            {12, 5, 198253934},
//            {12, 6, 27438555522L},
//            {12, 7, 0},
//            {12, 8, 0},
//            {12, 9, 0},
//            {12, 10, 0},
//            {12, 11, 0},
//            {12, 12, 0},
//            {12, 13, 0},
//            {12, 14, 0},
            {15, 1, 1},
            {15, 2, 571},
            {15, 3, 1086567},
            {15, 4, 86208957},
            {15, 5, 0},
            {15, 6, 0},
            {18, 1, 1},
            {18, 2, 0},
            {18, 3, 0},
//            {18, 4, 0},
            {21, 1, 1},
            {21, 2, 7953},
            {21, 3, 378522507},
            {21, 4, 0},
            {24, 1, 1},
            {24, 2, 29681},
            {24, 3, 7065162260L},
            {24, 4, 8135650498647L},
            {24, 5, 0},
            {24, 6, 0},
//            {12, 5, 198253934},
////            {24, 5, 0},
////            {15, 5, 0},
//            {18, 3, 20279829},
//            {19, 3, 53794224},
//            {42, 2, 80198051},
//            {45, 2, 299303201},
//            {2, 45, 299303201},
//            {9, 6, 0},
//            {9, 7, 0},
//            {9, 8, 0},
//            {9, 9, 0},
    };

    public static void main(String[] args) {
        long start;
        long stop;
        long delta;
        for (long[] values : Test.example_values) {
            System.out.format("%2d %2d - ", values[0], values[1]);
            start = System.currentTimeMillis();
            long res = Tetris.solve((int)values[0], (int)values[1]);
            stop = System.currentTimeMillis();
            delta = stop - start;
            if (values[2] == 0 || res == values[2]) {
                System.out.format("OK %6dms mutations: %15d setBlock: %15d getCache: %15d\n", delta, res, Tetris.setBlocks, Tetris.getCaches);
            } else {
                System.out.println("ERROR");
                System.out.format("Expected %d, got %d\n", values[2], res);
                return;
            }
        }
    }
}
