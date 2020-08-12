package com.nia.sovaultservermanager;

public enum SSMMeta {

    VERSION("v0.1.10dev"),
    NAME("ssm");

    private String value;

    SSMMeta(String value) {
        this.value = value;
    }

    public String get(){
        return value;
    }

}

