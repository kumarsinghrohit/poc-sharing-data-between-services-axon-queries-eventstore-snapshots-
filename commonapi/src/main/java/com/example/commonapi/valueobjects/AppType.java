package com.example.commonapi.valueobjects;

import java.util.Arrays;
import java.util.Objects;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum AppType {

    APP("App"),
    LIBRARY("Library");

    private String value;

    public static AppType get(String text) {
        return Arrays.stream(AppType.values()).filter(a -> Objects.equals(a.getValue(), text)).findFirst().orElse(null);
    }
}
