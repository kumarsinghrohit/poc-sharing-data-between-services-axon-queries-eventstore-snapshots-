package com.example.commonapi.valueobjects;

import lombok.ToString;
import lombok.Value;

@Value
@ToString(includeFieldNames = true)
public final class AppBinaryName {

    private String value;

}
