package com.example.commonapi.events;

import org.axonframework.serialization.Revision;

import com.example.commonapi.valueobjects.AppId;
import com.example.commonapi.valueobjects.AppVersionId;
import com.example.commonapi.valueobjects.AppVersionShortDescription;

import lombok.ToString;
import lombok.Value;

/**
 * An Axon event which will be published over event bus when shortDescription of
 * an existing version of app/library is updated.
 *
 */
@Value
@ToString(includeFieldNames = true)
@Revision("1.0")
public final class AppVersionShortDescriptionUpdated {

    private AppId id;
    private AppVersionId versionId;
    private AppVersionShortDescription shortDescription;
}
