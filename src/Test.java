import java.math.BigInteger;

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
            {9, 7, 1064557805},
            {9, 8, 30860212081L},
            {9, 9, 928789262080L},
            {9, 10, 25020222581494L},
            {9, 11, 714819627084057L},
            {12, 1, 1},
            {12, 2, 153},
            {12, 3, 58234},
            {12, 4, 1895145},
            {12, 5, 198253934},
            {12, 6, 27438555522L},
            {12, 7, 1949314526229L},
            {12, 8, 193553900967497L},
            {12, 9, 20574308184277971L},
            {12, 10, 1830607857363940042L},
            // TODO: Overflow! A long is not enough
            // {12, 11, -5675187654353494697},
//            {12, 12, 0},
//            {12, 13, 0},
//            {12, 14, 0},
            {15, 1, 1},
            {15, 2, 571},
            {15, 3, 1086567},
            {15, 4, 86208957},
            {15, 5, 31111319376L},
            {15, 6, 16593169804557L},
            {15, 7, 3619365754064658L},
            {15, 8, 1235348565576072999L},
//            {15, 9, new BigInteger("466431115279461257920")},
            // TODO: Overflow! A long is not enough
            // {15, 10, -9210086974413531734},
            {18, 1, 1},
            {18, 2, 2131},
            {18, 3, 20279829},
            {18, 4, 3924499731L},
            {18, 5, 4887323351972L},
            {18, 6, 10056816580083721L},
            {18, 7, 6743148875847013949L},
            // TODO: 18 8 is less than 18 7. Probably some overflow issue
//            {18, 8, 4962389223879298519L},
            {21, 1, 1},
            {21, 2, 7953},
            {21, 3, 378522507},
            {21, 4, 178682349823L},
            {21, 5, 767919868804309L},
            {21, 6, 6098207777286812381L},
            // TODO: Overflow! A long is not enough
            // {21, 7, -7282668717286566138},
            {24, 1, 1},
            {24, 2, 29681},
            {24, 3, 7065162260L},
            {24, 4, 8135650498647L},
            {24, 5, 120664440361104580L},
//            {24, 6, new BigInteger("3698195100855040716832")},
            // TODO: 24 7 is less than 24 6. Probably some overflow issue
            // {24, 7, 3858125783825068169L},
            {27, 1, 1},
            {27, 2, 110771},
            {27, 3, 131872134232L},
            {27, 4, 370429531112741L},
//            {27, 5, new BigInteger("18960353496615710993")},
            // TODO: Overflow! A long is not enough
            // {27, 6, -8218920711443338650},
            {30, 1, 1},
            {30, 2, 413403},
            {30, 3, 2461410223831L},
            {30, 4, 16866286184557689L},
            // TODO: Overflow! A long is not enough
            // {30, 5, -9072078150243050630},
            {33, 1, 1},
            {33, 2, 1542841},
            {33, 3, 45942537263742L},
            {33, 4, 767950873073579951L},
            {36, 1, 1},
            {36, 2, 5757961},
            {36, 3, 857523348947977L},
            // TODO: Overflow! A long is not enough
            // {36, 4, -1927368916977437637},
            {39, 1, 1},
            {39, 2, 0},
            {39, 3, 16005783272212985L},
            {42, 1, 1},
            {42, 2, 80198051},
            {42, 3, 298749997296405057L},
            {45, 1, 1},
            {45, 2, 299303201},
            {45, 3, 5576207010172198758L},
            {48, 1, 1},
            {48, 2, 1117014753},
            // TODO: Overflow! A long is not enough
            // {48, 3, -6599845524267142614},
            {51, 1, 1},
            {51, 2, 4168755811L},
            {54, 2, 15558008491L},
            {57, 2, 58063278153L},
            {60, 2, 216695104121L},
            {63, 2, 808717138331L},
            {66, 2, 3018173449203L},
            {69, 2, 11263976658481L},
            {72, 2, 42037733184721L},
            {75, 2, 156886956080403L},
            {78, 2, 585510091136891L},
            {81, 2, 2185153408467161L},
            {84, 2, 8155103542731753L},
            {87, 2, 30435260762459851L},
            {90, 2, 113585939507107651L},
            {93, 2, 423908497265970753L},
            {96, 2, 1582048049556775361L},
            {99, 2, 5904283700961130691L},
//            {102, 2, new BigInteger("22035086754287747403"},
//            {105, 2, },
            // TODO: Overflow! A long is not enough
            // {108, 2, -6685482742590689191L},
    };

    public static void main(String[] args) {
        long start;
        long stop;
        long delta;
        if (args.length == 2) {
            Test.example_values = new long[1][3];
            Test.example_values[0][0] = Long.valueOf(args[0]);
            Test.example_values[0][1] = Long.valueOf(args[1]);
            Test.example_values[0][2] = 0;
        }
        for (int i = 0; i < Test.example_values.length; i++) {
            long[] values = Test.example_values[i];
            start = System.currentTimeMillis();
            BigInteger res = Tetris.solve((int) values[0], (int) values[1]);
            stop = System.currentTimeMillis();
            delta = stop - start;
            if (values[2] == 0 || res.compareTo(BigInteger.valueOf(values[2])) == 0) {
//                System.out.format("OK %6dms mutations: %15d setBlock: %15d setCache: %15d\n", delta, res, Tetris.setBlocks, Tetris.getCaches);
                if (delta > 200 || true) {
                    System.out.format("%3d %3d - OK %6dms mutations: %20d\n", values[0], values[1], delta, res);
                }
            } else {
                System.out.format("%3d %3d - ERROR\n", values[0], values[1]);
                System.out.format("Expected %d, got %d\n", values[2], res);
                return;
            }
            if (i > 1 && i < Test.example_values.length - 1 && Test.example_values[i + 1][0] != values[0] && Test.example_values[i - 1][0] == values[0]) {
                System.out.println();
            }
        }
    }
}
