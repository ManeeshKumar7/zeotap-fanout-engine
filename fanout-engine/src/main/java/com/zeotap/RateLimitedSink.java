package com.zeotap;

import java.util.concurrent.Semaphore;

public class RateLimitedSink implements Sink {

    private final Sink inner;
    private final Semaphore permits;

    public RateLimitedSink(Sink inner, int maxPerSecond) {
        this.inner = inner;
        this.permits = new Semaphore(maxPerSecond);

        // refill permits every second
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    permits.release(maxPerSecond - permits.availablePermits());
                } catch (InterruptedException ignored) {}
            }
        }).start();
    }

    @Override
    public void send(String record) throws Exception {
        permits.acquire();
        inner.send(record);
    }
}
