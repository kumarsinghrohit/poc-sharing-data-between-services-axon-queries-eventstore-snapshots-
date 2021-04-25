package com.example.commonapi.valueobjects;

import lombok.Value;

@Value
public final class AppVersionUpdateInfo {
    private String value;

    public AppVersionUpdateInfo(String value) {
        this.value = value;
    }

}
