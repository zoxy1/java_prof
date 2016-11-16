package concurrency;

import java.util.TimerTask;

/**
 * Created by saturn on 08.11.2016.
 */
public class Timer {

    public static void main(String... args) throws InterruptedException {
        TimerTask timerTask = new Task();
        java.util.Timer timer = new java.util.Timer(true);
        timer.schedule(timerTask, 3000, 5000);

        for (;;) {
            Thread.sleep(1000);
            System.out.println("working in main");
        }
    }

    public static class Task extends TimerTask {

        @Override
        public void run() {
            System.err.println("Ooops!!! Something is up!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        }
    }

}
