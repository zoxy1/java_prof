package concurrency;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by saturn on 07.11.2016.
 * Copyright http://stackoverflow.com/questions/22944918/why-does-the-code-hang-with-hashmap-put-from-multiple-threads
 */
public class HangMap {


    public static void main(String... args) throws Exception {
        final Random rnd = new Random();
        final Map<String, Object> map = Collections.synchronizedMap(new HashMap<String, Object>());
        ExecutorService executor = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 200; i++) {
            final int counter = i;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int j = 0; j < 1000; j++) {
                            map.put(String.valueOf(rnd.nextLong()), new Object());
                        }
                        System.out.println("Thread " + counter + " finished");
                    } catch (Exception e) {
                        System.out.println("Thread " + counter + " failed with exception: ");
                        e.printStackTrace();
                    }
                }
            });
        }
        executor.shutdown();
        int i = 0;
        while (!executor.isTerminated()) {
            i++;
            Thread.sleep(1000);
            System.out.println("Waited " + i + " seconds");
        }
    }
}
