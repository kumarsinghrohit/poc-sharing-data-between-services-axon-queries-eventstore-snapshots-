package com.example.commonapi.valueobjects;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum AppLifeCycle {
    DRAFT("Draft"),
    PROTOTYPE("Prototype"),
    ACTIVE("Active"),
    PHASED_OUT("Phased-out"),
    DISCONTINUED("Discontinued");

    private String value;
}
