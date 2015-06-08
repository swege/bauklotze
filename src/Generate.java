import java.math.BigInteger;
import java.util.concurrent.*;

// stolen from http://stackoverflow.com/a/241534/458274
public class Generate {

    static int TIMELIMIT;
    static private int m;
    static private int n;

    public static void main(String[] args) {
        Callable<BigInteger> callable = new Callable<BigInteger>() {
            @Override
            public BigInteger call() throws Exception {
                return Tetris.solve(Generate.m, Generate.n);
            }
        };
        if (args.length == 1) {
            Generate.TIMELIMIT = Integer.valueOf(args[0]);
        } else {
            Generate.TIMELIMIT = 300000;
        }
        long start = 0;
        long stop = 0;
        String delta = "";
        Generate.m = 0;
        while (true) {
            Generate.m += 3;
            for (Generate.n = 1; Generate.n < Generate.m + 3; Generate.n++) {
                ExecutorService executorService = Executors.newCachedThreadPool();
                Future<BigInteger> task = executorService.submit(callable);
                String mutations = "";
                try {
                    start = System.currentTimeMillis();
                    BigInteger res = task.get(Generate.TIMELIMIT, TimeUnit.MILLISECONDS);
                    stop = System.currentTimeMillis();
                    delta = String.format("%7d", stop - start);
                    mutations = String.format("%50d", res);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (TimeoutException e) {
                    delta = String.format(">%6d", Generate.TIMELIMIT);
                    mutations = String.format("%50s", "TIMEOUT");
                } catch (InterruptedException e) {
                    System.out.println("interrupted");
                }
                System.out.format("%3d %3d - %sms mutations: %s\n", Generate.m, Generate.n, delta, mutations);
            }
            System.out.println();
        }
    }
}
