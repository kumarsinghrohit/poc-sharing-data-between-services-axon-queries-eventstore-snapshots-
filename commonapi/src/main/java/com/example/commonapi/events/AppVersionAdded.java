package com.example.commonapi.events;

import java.time.LocalDate;
import java.util.List;

import org.axonframework.serialization.Revision;

import com.example.commonapi.valueobjects.AppBinaryName;
import com.example.commonapi.valueobjects.AppId;
import com.example.commonapi.valueobjects.AppVersionGalleryImage;
import com.example.commonapi.valueobjects.AppVersionId;
import com.example.commonapi.valueobjects.AppVersionLifeCycle;
import com.example.commonapi.valueobjects.AppVersionLongDescription;
import com.example.commonapi.valueobjects.AppVersionNumber;
import com.example.commonapi.valueobjects.AppVersionPrice;
import com.example.commonapi.valueobjects.AppVersionShortDescription;
import com.example.commonapi.valueobjects.AppVersionUpdateInfo;
import com.example.commonapi.valueobjects.AppVersionVisibility;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

/**
 * An Axon event which will be published over event bus when an new version of
 * app/library is added.
 *
 */
@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Revision("1.0")
public final class AppVersionAdded {

    private AppId id;
    private AppVersionId versionId;
    private AppVersionPrice price;
    private AppVersionShortDescription shortDescription;
    private AppVersionLongDescription longDescription;
    private @Singular List<AppVersionGalleryImage> galleryImages;
    private AppVersionUpdateInfo updateInformation;
    private AppVersionNumber versionNumber;
    private AppBinaryName binaryName;
    private AppVersionLifeCycle versionLifeCycle;
    private AppVersionVisibility visibility;
    private LocalDate createdOn;

    public static class AppVersionAddedBuilder {
        private AppVersionAddedBuilder() {
        }
    }

    public static AppVersionAddedBuilder builder(AppId id, AppVersionId versionId, AppVersionNumber versionNumber,
            AppBinaryName binaryName, AppVersionLifeCycle versionLifeCycle, AppVersionVisibility visibility,
            LocalDate createdOn) {
        return new AppVersionAddedBuilder().id(id).versionId(versionId).versionNumber(versionNumber).binaryName(binaryName)
                .versionLifeCycle(versionLifeCycle).visibility(visibility).createdOn(createdOn);
    }
}
