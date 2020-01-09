package engine.maths;


import org.apache.commons.lang3.time.StopWatch;

public class Timmer {
    public static StopWatch stopwatch = new StopWatch();

    public static void start() {
        stopwatch.start();

    }

    public static void stop() {
        stopwatch.stop();
        stopwatch.reset();
    }

    public static long getTime() {
        return stopwatch.getTime() / 1000;
    }

    public static void getStringTime() {
        System.out.println(stopwatch.toString());
    }
}