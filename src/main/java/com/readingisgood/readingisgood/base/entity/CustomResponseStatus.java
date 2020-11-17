package com.readingisgood.readingisgood.base.entity;

public enum CustomResponseStatus {
    SUCCESS("success"),
    ERROR("error");

    public final String value;

    private CustomResponseStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
