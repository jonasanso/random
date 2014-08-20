import java.util.*;

public class Sequential {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Random random = new Random();
        List<Long> uuids = new ArrayList<Long>();

        for (int i = 0; i < 10000; i++) {
            uuids.add(random.nextLong());
        }

        HashSet<Long> uniqueIds = new HashSet<Long>(uuids);
        System.out.println("Unique uuids "+uniqueIds.size());
        System.out.println("Took "+(System.currentTimeMillis() - start) + " milis");
    }
}
