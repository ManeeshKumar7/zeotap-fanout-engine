package com.zeotap;

public class SlowSink implements Sink {

    @Override
    public void send(String record) throws Exception {
        Thread.sleep(500); // simulate slow API
        System.out.println("SlowSink -> " + record);
    }
}
