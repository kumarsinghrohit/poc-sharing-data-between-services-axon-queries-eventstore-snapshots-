package com.example.commonapi.valueobjects;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum AppVersionVisibility {

    PUBLIC("Public"),
    ORGANISATION("Organisation");

    private String value;
}
