package com.example.commonapi.valueobjects;

import java.util.Currency;

import lombok.ToString;
import lombok.Value;

@Value
@ToString(includeFieldNames = true)
public final class AppVersionPrice {

    private Currency currency;
    private Double value;

    public AppVersionPrice(Currency currency, Double value) {
        this.currency = currency;
        this.value = value;
    }

}
