package com.miu.orderservice.Enum;

public enum Status {
    SUCCESS("SUCCESS"), CANCEL("CANCEL");

    private String name;

    Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}







