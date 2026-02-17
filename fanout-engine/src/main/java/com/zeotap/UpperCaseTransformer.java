package com.zeotap;

public class UpperCaseTransformer implements Transformer {

    @Override
    public String transform(String record) {
        return record.toUpperCase();
    }
}
