package com.zeotap;

public interface Sink {
    void send(String record) throws Exception;
}
