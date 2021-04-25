package com.example.commonapi.events;

import java.util.List;

import org.axonframework.serialization.Revision;

import com.example.commonapi.valueobjects.AppId;
import com.example.commonapi.valueobjects.AppVersionGalleryImage;
import com.example.commonapi.valueobjects.AppVersionId;

import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

/**
 * An Axon event which will be published over event bus when galleryImages of an
 * existing version of app/library is updated.
 *
 */
@Value
@ToString(includeFieldNames = true)
@Revision("1.0")
public final class AppVersionGalleryImageUpdated {

    private AppId id;
    private AppVersionId versionId;
    private @NonNull List<AppVersionGalleryImage> galleryImages;
}
