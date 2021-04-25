package com.example.commonapi.valueobjects;

import lombok.ToString;
import lombok.Value;

@Value
@ToString(includeFieldNames = true)
public final class DeveloperId {

    private String value;

    public DeveloperId(String value) {
        this.value = value;
    }

}
