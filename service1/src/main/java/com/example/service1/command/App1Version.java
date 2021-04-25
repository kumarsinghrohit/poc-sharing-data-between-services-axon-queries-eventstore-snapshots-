package com.example.service1.command;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.EntityId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
class App1Version {
    private static final Logger LOGGER = LoggerFactory.getLogger(App1Version.class);
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
    private void handle(UpdateApp1VersionCommand updateAppVersionCommand) {
        AppId appId = updateAppVersionCommand.getId();
        AppVersionId versionIdToUpdate = updateAppVersionCommand.getVersionId();

        if (!Objects.equals(this.shortDescription, updateAppVersionCommand.getShortDescription())) {
            apply(new AppVersionShortDescriptionUpdated(appId, versionIdToUpdate,
                    updateAppVersionCommand.getShortDescription()));
        }
        if (!Objects.equals(this.longDescription, updateAppVersionCommand.getLongDescription())) {
            apply(new AppVersionLongDescriptionUpdated(appId, versionIdToUpdate,
                    updateAppVersionCommand.getLongDescription()));
        }
        if (!Objects.equals(this.price.getValue(), updateAppVersionCommand.getPrice().getValue())) {
            apply(new AppVersionPriceUpdated(appId, versionIdToUpdate, updateAppVersionCommand.getPrice()));
        }

        if (!Objects.equals(this.updateInformation, updateAppVersionCommand.getUpdateInformation())) {
            apply(new AppVersionUpdateInformationUpdated(appId, versionIdToUpdate,
                    updateAppVersionCommand.getUpdateInformation()));
        }
        if (!Objects.equals(this.versionNumber, updateAppVersionCommand.getVersionNumber())) {
            apply(new AppVersionNumberUpdated(appId, versionIdToUpdate, updateAppVersionCommand.getVersionNumber()));
        }
        List<AppVersionGalleryImage> galleryImagesToUpdate = updateAppVersionCommand.getGalleryImages();
        if (Objects.nonNull(galleryImagesToUpdate)) {
            apply(new AppVersionGalleryImageUpdated(appId, versionIdToUpdate, galleryImagesToUpdate));
        }
        if (Objects.nonNull(updateAppVersionCommand.getBinaryName().getValue())) {
            apply(new AppVersionBinaryUpdated(appId, versionIdToUpdate, updateAppVersionCommand.getBinaryName()));
        }
    }

    @EventSourcingHandler
    private void on(AppVersionGalleryImageUpdated appVersionGalleryImageUpdated) {
        LOGGER.info("AppVersionGalleryImageUpdated occured with appId {}",
                appVersionGalleryImageUpdated.getId().getValue());
        this.galleryImages = appVersionGalleryImageUpdated.getGalleryImages();
    }

    @EventSourcingHandler
    private void on(AppVersionNumberUpdated appVersionNumberUpdated) {
        LOGGER.info("AppVersionNumberUpdated occured with appId {} versionNumber {}",
                appVersionNumberUpdated.getId().getValue(), appVersionNumberUpdated.getVersionNumber().getValue());
        this.versionNumber = appVersionNumberUpdated.getVersionNumber();
    }

    @EventSourcingHandler
    private void on(AppVersionBinaryUpdated appVersionBinaryUpdated) {
        LOGGER.info("AppVersionBinaryUpdated occured with appId {}, binary {}",
                appVersionBinaryUpdated.getId().getValue(), appVersionBinaryUpdated.getBinaryName().getValue());
        this.binaryName = appVersionBinaryUpdated.getBinaryName();
    }

    @EventSourcingHandler
    private void on(AppVersionUpdateInformationUpdated appVersionUpdateInformationUpdated) {
        LOGGER.info("AppVersionUpdateInformationUpdated occured with appId {} updateInfo {}",
                appVersionUpdateInformationUpdated.getId().getValue(),
                appVersionUpdateInformationUpdated.getUpdateInformation().getValue());
        this.updateInformation = appVersionUpdateInformationUpdated.getUpdateInformation();
    }

    @EventSourcingHandler
    private void on(AppVersionPriceUpdated appVersionPriceUpdated) {
        LOGGER.info("AppVersionPriceUpdated occured with appId {} price {}",
                appVersionPriceUpdated.getId().getValue(), appVersionPriceUpdated.getPrice().getValue());
        this.price = appVersionPriceUpdated.getPrice();
    }

    @EventSourcingHandler
    private void on(AppVersionShortDescriptionUpdated appVersionShortDescriptionUpdated) {
        LOGGER.info("AppVersionShortDescriptionUpdated occured with appId {}, shortDesc {}",
                appVersionShortDescriptionUpdated.getId().getValue(),
                appVersionShortDescriptionUpdated.getShortDescription().getValue());
        this.shortDescription = appVersionShortDescriptionUpdated.getShortDescription();
    }

    @EventSourcingHandler
    private void on(AppVersionLongDescriptionUpdated appVersionLongDescriptionUpdated) {
        LOGGER.info("AppVersionLongDescriptionUpdated occured with appId {} longDescription {}",
                appVersionLongDescriptionUpdated.getId().getValue(),
                appVersionLongDescriptionUpdated.getLongDescription().getValue());
        this.longDescription = appVersionLongDescriptionUpdated.getLongDescription();
    }
}
