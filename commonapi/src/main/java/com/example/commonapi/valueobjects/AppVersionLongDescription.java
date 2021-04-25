package com.example.commonapi.valueobjects;

import lombok.Value;

@Value
public final class AppVersionLongDescription {
    private String value;

    public AppVersionLongDescription(String value) {
        this.value = value;
    }

}
