package com.example.service1.command;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.EntityId;

import com.example.commonapi.events.AppVersionBinaryUpdated;
import com.example.commonapi.events.AppVersionGalleryImageUpdated;
import com.example.commonapi.events.AppVersionLongDescriptionUpdated;
import com.example.commonapi.events.AppVersionNumberUpdated;
import com.example.commonapi.events.AppVersionPriceUpdated;
import com.example.commonapi.events.AppVersionShortDescriptionUpdated;
import com.example.commonapi.events.AppVersionUpdateInformationUpdated;
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

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class AppVersion {

    @EntityId
    private AppVersionId versionId;
    private AppVersionPrice price;
    private AppVersionShortDescription shortDescription;
    private AppVersionLongDescription longDescription;
    private List<AppVersionGalleryImage> galleryImages;
    private AppVersionUpdateInfo updateInformation;
    private AppVersionNumber versionNumber;
    private AppBinaryName binaryName;
    private AppVersionLifeCycle versionLifeCycle;
    private AppVersionVisibility visibility;
    private LocalDate createdOn;

    @CommandHandler
    private void handle(UpdateAppVersion UpdateAppVersion) {
        AppId appId = UpdateAppVersion.getId();
        AppVersionId versionIdToUpdate = UpdateAppVersion.getVersionId();

        if (!Objects.equals(this.shortDescription, UpdateAppVersion.getShortDescription())) {
            apply(new AppVersionShortDescriptionUpdated(appId, versionIdToUpdate,
                    UpdateAppVersion.getShortDescription()));
        }
        if (!Objects.equals(this.longDescription, UpdateAppVersion.getLongDescription())) {
            apply(new AppVersionLongDescriptionUpdated(appId, versionIdToUpdate,
                    UpdateAppVersion.getLongDescription()));
        }
        if (!Objects.equals(this.price.getValue(), UpdateAppVersion.getPrice().getValue())) {
            apply(new AppVersionPriceUpdated(appId, versionIdToUpdate, UpdateAppVersion.getPrice()));
        }

        if (!Objects.equals(this.updateInformation, UpdateAppVersion.getUpdateInformation())) {
            apply(new AppVersionUpdateInformationUpdated(appId, versionIdToUpdate,
                    UpdateAppVersion.getUpdateInformation()));
        }
        if (!Objects.equals(this.versionNumber, UpdateAppVersion.getVersionNumber())) {
            apply(new AppVersionNumberUpdated(appId, versionIdToUpdate, UpdateAppVersion.getVersionNumber()));
        }
        List<AppVersionGalleryImage> galleryImagesToUpdate = UpdateAppVersion.getGalleryImages();
        if (Objects.nonNull(galleryImagesToUpdate)) {
            apply(new AppVersionGalleryImageUpdated(appId, versionIdToUpdate, galleryImagesToUpdate));
        }
        if (Objects.nonNull(UpdateAppVersion.getBinaryName().getValue())) {
            apply(new AppVersionBinaryUpdated(appId, versionIdToUpdate, UpdateAppVersion.getBinaryName()));
        }
    }

    @EventSourcingHandler
    private void on(AppVersionGalleryImageUpdated AppVersionGalleryImageUpdated) {
        this.galleryImages = AppVersionGalleryImageUpdated.getGalleryImages();
    }

    @EventSourcingHandler
    private void on(AppVersionNumberUpdated AppVersionNumberUpdated) {
        this.versionNumber = AppVersionNumberUpdated.getVersionNumber();
    }

    @EventSourcingHandler
    private void on(AppVersionBinaryUpdated AppVersionBinaryUpdated) {
        this.binaryName = AppVersionBinaryUpdated.getBinaryName();
    }

    @EventSourcingHandler
    private void on(AppVersionUpdateInformationUpdated AppVersionUpdateInformationUpdated) {
        this.updateInformation = AppVersionUpdateInformationUpdated.getUpdateInformation();
    }

    @EventSourcingHandler
    private void on(AppVersionPriceUpdated AppVersionPriceUpdated) {
        this.price = AppVersionPriceUpdated.getPrice();
    }

    @EventSourcingHandler
    private void on(AppVersionShortDescriptionUpdated AppVersionShortDescriptionUpdated) {
        this.shortDescription = AppVersionShortDescriptionUpdated.getShortDescription();
    }

    @EventSourcingHandler
    private void on(AppVersionLongDescriptionUpdated AppVersionLongDescriptionUpdated) {
        this.longDescription = AppVersionLongDescriptionUpdated.getLongDescription();
    }
}
