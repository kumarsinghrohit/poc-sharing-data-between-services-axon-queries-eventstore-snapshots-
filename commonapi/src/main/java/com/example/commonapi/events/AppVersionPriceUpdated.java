package com.example.commonapi.events;

import org.axonframework.serialization.Revision;

import com.example.commonapi.valueobjects.AppId;
import com.example.commonapi.valueobjects.AppVersionId;
import com.example.commonapi.valueobjects.AppVersionPrice;

import lombok.ToString;
import lombok.Value;

/**
 * An Axon event which will be published over event bus when price of an
 * existing version of app/library is updated.
 *
 */
@Value
@ToString(includeFieldNames = true)
@Revision("1.0")
public final class AppVersionPriceUpdated {

    private AppId id;
    private AppVersionId versionId;
    private AppVersionPrice price;
}
