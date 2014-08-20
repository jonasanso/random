import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class Parallel {

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException  {
        long start = System.currentTimeMillis();

        List<Future<Long>> futureUuids = new ArrayList<Future<Long>>();

        ExecutorService executor = Executors.newFixedThreadPool(5);

        RandomGenerator generator = new RandomGenerator();

        for (int i = 0; i < 10000; i++) {
            futureUuids.add(executor.submit(generator));
        }

        List<Long> uuids = waiting(futureUuids, 1, TimeUnit.DAYS);

        HashSet<Long> uniqueIds = new HashSet<Long>(uuids);
        System.out.println("Unique uuids "+uniqueIds.size());

        executor.shutdown();
        System.out.println("Took "+(System.currentTimeMillis() - start) + " milis");
    }


    public static <V> List<V> waiting(List<Future<V>> futures, long timeout, TimeUnit unit)
            throws InterruptedException, ExecutionException, TimeoutException {
        List<V> result = new ArrayList<V>();
        long end = System.nanoTime() + unit.toNanos(timeout);
        for (Future<V> f: futures) {
            result.add(f.get(end - System.nanoTime(), TimeUnit.NANOSECONDS));
        }
        return result;
    }
}


class RandomGenerator implements Callable<Long> {

    private final Random random;

    RandomGenerator() {
        this.random = new Random();
    }


    @Override
    public Long call() throws Exception {
        return random.nextLong();
    }

}
