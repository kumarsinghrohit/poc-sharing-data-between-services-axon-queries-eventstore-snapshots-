package com.example.commonapi.valueobjects;

import lombok.Value;

@Value
public final class AppVersionShortDescription {

    private String value;

    public AppVersionShortDescription(String value) {

        this.value = value;
    }

}
