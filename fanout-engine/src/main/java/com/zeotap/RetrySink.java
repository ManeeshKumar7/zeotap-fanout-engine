package com.zeotap;

public class RetrySink implements Sink {

    private final Sink inner;
    private final int retries;

    public RetrySink(Sink inner, int retries) {
        this.inner = inner;
        this.retries = retries;
    }

    @Override
    public void send(String record) throws Exception {

        int attempt = 0;

        while (true) {
            try {
                inner.send(record);
                return;
            } catch (Exception e) {
                attempt++;
                if (attempt > retries) {
                    System.out.println("FAILED after retries: " + record);
                    return;
                }
                Thread.sleep(500); // backoff
            }
        }
    }
}
