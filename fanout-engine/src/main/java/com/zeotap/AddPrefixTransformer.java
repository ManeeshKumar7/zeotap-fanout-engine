package com.zeotap;

public class AddPrefixTransformer implements Transformer {

    @Override
    public String transform(String record) {
        return "PREFIX_" + record;
    }
}
