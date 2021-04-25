package com.example.commonapi.valueobjects;

import lombok.Value;

@Value
public final class AppId {

    private String value;

    public AppId(String value) {
        this.value = value;
    }
}
