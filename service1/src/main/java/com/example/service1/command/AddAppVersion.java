package com.example.service1.command;

import java.time.LocalDate;
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
import com.example.commonapi.valueobjects.DeveloperId;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

/**
 * An axon command which will be published over a axon command bus when a new
 * app version of app/library is added.
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class AddAppVersion {

    @TargetAggregateIdentifier
    private AppId id;
    private AppVersionId versionId;
    private AppVersionPrice price;
    private AppVersionShortDescription shortDescription;
    private AppVersionLongDescription longDescription;
    private @Singular List<AppVersionGalleryImage> galleryImages;
    private AppVersionUpdateInfo updateInformation;
    private AppVersionNumber versionNumber;
    private AppBinaryName binaryName;
    private LocalDate createdOn;
    private DeveloperId developerId;

    public static class AddAppVersionBuilder {
        private AddAppVersionBuilder() {
        }
    }

    public static AddAppVersionBuilder builder(AppId id, AppVersionId versionId, AppVersionNumber versionNumber,
            AppBinaryName binaryName, LocalDate createdOn, DeveloperId developerId) {
        return new AddAppVersionBuilder().id(id).versionId(versionId).versionNumber(versionNumber).binaryName(binaryName)
                .createdOn(createdOn).developerId(developerId);
    }
}
