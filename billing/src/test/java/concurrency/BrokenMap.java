package concurrency;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by saturn on 07.11.2016.
 */
public class BrokenMap implements Runnable {
    private static Map<Integer, String> map = new ConcurrentHashMap<>(4_000_000);
    private static final int limit = 2_000_000;

    public static void main (String... args) {
        Thread t1 = new Thread(new BrokenMap());
        Thread t2 = new Thread(new BrokenMapInner());
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("interrupt t1,t2 join :" + e.getMessage());
        }

        System.out.println("map.size() => " + customFormat("### ### ###,###", map.size()));

    }

    @Override
    public void run() {
        for (int i = 0; i < limit; i++) {
                map.put(i, "" + i);
        }
    }

    static public String customFormat(String pattern, double value ) {
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        myFormatter.setGroupingSize(3);
        String output = myFormatter.format(value);
        return output;
    }

    private static class BrokenMapInner implements Runnable{

        @Override
        public void run() {
            for (int i = limit; i < limit * 2; i++) {
                map.put(i, "" + i);
            }
        }
    }
}
