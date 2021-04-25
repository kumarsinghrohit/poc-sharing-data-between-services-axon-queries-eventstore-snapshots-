package com.example.commonapi.events;

import java.time.LocalDate;
import java.util.List;

import org.axonframework.serialization.Revision;

import com.example.commonapi.valueobjects.AppBinaryName;
import com.example.commonapi.valueobjects.AppId;
import com.example.commonapi.valueobjects.AppLifeCycle;
import com.example.commonapi.valueobjects.AppName;
import com.example.commonapi.valueobjects.AppType;
import com.example.commonapi.valueobjects.AppVersionGalleryImage;
import com.example.commonapi.valueobjects.AppVersionId;
import com.example.commonapi.valueobjects.AppVersionLifeCycle;
import com.example.commonapi.valueobjects.AppVersionLongDescription;
import com.example.commonapi.valueobjects.AppVersionNumber;
import com.example.commonapi.valueobjects.AppVersionPrice;
import com.example.commonapi.valueobjects.AppVersionShortDescription;
import com.example.commonapi.valueobjects.AppVersionVisibility;
import com.example.commonapi.valueobjects.DeveloperId;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

/**
 * An Axon event which will be published over event bus when a app/library is
 * created.
 *
 */
@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Revision("1.0")
public final class AppCreated {

    private AppId id;
    private AppName name;
    private AppType type;
    private AppVersionId versionId;
    private AppVersionPrice price;
    private AppVersionShortDescription shortDescription;
    private AppVersionLongDescription longDescription;
    private @Singular List<AppVersionGalleryImage> galleryImages;
    private AppVersionNumber versionNumber;
    private AppBinaryName binaryName;
    private AppVersionLifeCycle versionLifeCycle;
    private AppVersionVisibility visibility;
    private DeveloperId developerId;
    private AppLifeCycle lifeCycle;
    private LocalDate createdOn;

    public static class AppCreatedBuilder {
        private AppCreatedBuilder() {
        }
    }

    public static AppCreatedBuilder builder(AppId id, AppName name, AppType type, AppVersionId versionId,
            AppVersionNumber versionNumber, AppBinaryName binaryName, AppVersionLifeCycle versionLifeCycle,
            AppVersionVisibility visibility, DeveloperId developerId, AppLifeCycle lifeCycle, LocalDate createdOn) {
        return new AppCreatedBuilder().id(id).name(name).type(type).versionId(versionId).versionNumber(versionNumber)
                .binaryName(binaryName).versionLifeCycle(versionLifeCycle).visibility(visibility).developerId(developerId)
                .lifeCycle(lifeCycle).createdOn(createdOn);
    }
}