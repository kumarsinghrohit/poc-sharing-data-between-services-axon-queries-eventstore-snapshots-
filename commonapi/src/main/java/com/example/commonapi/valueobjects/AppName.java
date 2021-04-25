package com.example.commonapi.valueobjects;

import lombok.ToString;
import lombok.Value;

@Value
@ToString(includeFieldNames = true)
public final class AppName {

    private String value;

    public AppName(String value) {
        this.value = value;
    }
}
