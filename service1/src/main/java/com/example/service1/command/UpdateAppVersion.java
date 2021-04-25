package com.example.service1.command;

import java.util.List;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.example.commonapi.valueobjects.AppBinaryName;
import com.example.commonapi.valueobjects.AppId;
import com.example.commonapi.valueobjects.AppVersionGalleryImage;
import com.example.commonapi.valueobjects.AppVersionId;
import com.example.commonapi.valueobjects.AppVersionLongDescription;
import com.example.commonapi.valueobjects.AppVersionNumber;
import com.example.commonapi.valueobjects.AppVersionPrice;
import com.example.commonapi.valueobjects.AppVersionShortDescription;
import com.example.commonapi.valueobjects.AppVersionUpdateInfo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * An axon command which will be published over a axon command bus when an
 * existing app/library is updated.
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class UpdateAppVersion {

    @TargetAggregateIdentifier
    private AppId id;
    private AppVersionId versionId;
    private AppVersionPrice price;
    private AppVersionShortDescription shortDescription;
    private AppVersionLongDescription longDescription;
    private List<AppVersionGalleryImage> galleryImages;
    private AppVersionUpdateInfo updateInformation;
    private AppVersionNumber versionNumber;
    private AppBinaryName binaryName;

    public static class UpdateAppVersionBuilder {
        private UpdateAppVersionBuilder() {
        }
    }

    public static UpdateAppVersionBuilder builder(AppId id, AppVersionId versionId, AppVersionNumber versionNumber,
            AppBinaryName binaryName) {
        return new UpdateAppVersionBuilder().id(id).versionId(versionId).versionNumber(versionNumber)
                .binaryName(binaryName);
    }
}