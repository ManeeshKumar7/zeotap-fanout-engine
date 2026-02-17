package com.zeotap;

public class ConsoleSink implements Sink {

    @Override
    public void send(String record) {
        System.out.println("ConsoleSink -> " + record);
    }
}
