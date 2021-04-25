package com.example.service1.projection;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import com.example.commonapi.events.AppCreated;
import com.example.commonapi.events.AppVersionAdded;
import com.example.commonapi.events.AppVersionBinaryUpdated;
import com.example.commonapi.events.AppVersionGalleryImageUpdated;
import com.example.commonapi.events.AppVersionLongDescriptionUpdated;
import com.example.commonapi.events.AppVersionNumberUpdated;
import com.example.commonapi.events.AppVersionPriceUpdated;
import com.example.commonapi.events.AppVersionShortDescriptionUpdated;
import com.example.commonapi.events.AppVersionUpdateInformationUpdated;
import com.example.commonapi.queries.FindAppShortDescriptionByIdQuery;
import com.example.commonapi.queries.FindAppTypeByIdQuery;
import com.example.commonapi.valueobjects.AppVersionGalleryImage;
import com.google.common.base.Objects;

@Component
class AppProjection {

    private final AppDaoService appDaoService;

    AppProjection(AppDaoService appDaoService) {
        this.appDaoService = appDaoService;
    }

    @EventHandler
    void on(AppCreated AppCreated) throws IOException {
        appDaoService.createApp(new AppEntity(
                AppCreated.getId().getValue(),
                AppCreated.getName().getValue(),
                AppCreated.getType().getValue(),
                Arrays.asList(
                        convertToAppVersionEntity(AppCreated)),
                AppCreated.getDeveloperId().getValue(),
                AppCreated.getLifeCycle().getValue(),
                AppCreated.getCreatedOn()));
    }

    @EventHandler
    void on(AppVersionAdded AppVersionAdded) throws IOException {
        appDaoService.addVersion(AppVersionAdded.getId().getValue(),
                convertToAppVersionEntity(AppVersionAdded));
    }

    @EventHandler
    void on(AppVersionShortDescriptionUpdated AppVersionShortDescriptionUpdated) throws IOException {
        appDaoService.updateVersionShortDescription(AppVersionShortDescriptionUpdated.getId().getValue(),
                AppVersionShortDescriptionUpdated.getVersionId().getValue(),
                AppVersionShortDescriptionUpdated.getShortDescription().getValue());
    }

    @EventHandler
    void on(AppVersionLongDescriptionUpdated AppVersionLongDescriptionUpdated) throws IOException {
        appDaoService.updateVersionLongDescription(AppVersionLongDescriptionUpdated.getId().getValue(),
                AppVersionLongDescriptionUpdated.getVersionId().getValue(),
                AppVersionLongDescriptionUpdated.getLongDescription().getValue());
    }

    @EventHandler
    void on(AppVersionPriceUpdated AppVersionPriceUpdated) throws IOException {
        appDaoService.updateVersionPrice(AppVersionPriceUpdated.getId().getValue(),
                AppVersionPriceUpdated.getVersionId().getValue(),
                AppVersionPriceUpdated.getPrice().getValue());
    }

    @EventHandler
    void on(AppVersionUpdateInformationUpdated AppVersionUpdateInformationUpdated) throws IOException {
        appDaoService.updateVersionUpdateInformation(AppVersionUpdateInformationUpdated.getId().getValue(),
                AppVersionUpdateInformationUpdated.getVersionId().getValue(),
                AppVersionUpdateInformationUpdated.getUpdateInformation().getValue());
    }

    @EventHandler
    void on(AppVersionNumberUpdated AppVersionNumberUpdated) throws IOException {
        appDaoService.updateVersionNumber(AppVersionNumberUpdated.getId().getValue(),
                AppVersionNumberUpdated.getVersionId().getValue(),
                AppVersionNumberUpdated.getVersionNumber().getValue());
    }

    @EventHandler
    void on(AppVersionBinaryUpdated AppVersionBinaryUpdated) throws IOException {
        appDaoService.updateBinaryName(AppVersionBinaryUpdated.getId().getValue(),
                AppVersionBinaryUpdated.getVersionId().getValue(),
                AppVersionBinaryUpdated.getBinaryName().getValue());
    }

    @EventHandler
    void on(AppVersionGalleryImageUpdated AppVersionGalleryImageUpdated) throws IOException {
        appDaoService.updateVersionGalleryImages(AppVersionGalleryImageUpdated.getId().getValue(),
                AppVersionGalleryImageUpdated.getVersionId().getValue(),
                mapToAppVersionGalleryImageEntity(AppVersionGalleryImageUpdated.getGalleryImages()));
    }

    private AppVersionEntity convertToAppVersionEntity(AppCreated AppCreated) {
        return new AppVersionEntity(AppCreated.getVersionId().getValue(),
                AppCreated.getPrice().getValue(),
                AppCreated.getShortDescription().getValue(),
                AppCreated.getLongDescription().getValue(),
                mapToAppVersionGalleryImageEntity(AppCreated.getGalleryImages()),
                null,
                AppCreated.getVersionNumber().getValue(),
                AppCreated.getBinaryName().getValue(),
                AppCreated.getVersionLifeCycle().getValue(),
                AppCreated.getVisibility().getValue(),
                AppCreated.getCreatedOn());
    }

    private AppVersionEntity convertToAppVersionEntity(AppVersionAdded AppVersionAdded) {
        return new AppVersionEntity(AppVersionAdded.getVersionId().getValue(),
                AppVersionAdded.getPrice().getValue(),
                AppVersionAdded.getShortDescription().getValue(),
                AppVersionAdded.getLongDescription().getValue(),
                mapToAppVersionGalleryImageEntity(AppVersionAdded.getGalleryImages()),
                AppVersionAdded.getUpdateInformation().getValue(),
                AppVersionAdded.getVersionNumber().getValue(),
                AppVersionAdded.getBinaryName().getValue(),
                AppVersionAdded.getVersionLifeCycle().getValue(),
                AppVersionAdded.getVisibility().getValue(),
                AppVersionAdded.getCreatedOn());
    }

    @QueryHandler
    String getAppTypeById(FindAppTypeByIdQuery getAppByIdQuery) throws IOException {
        return appDaoService.getAppTypeById(getAppByIdQuery.getAppId());
    }

    @QueryHandler
    String getAppShortDescriptionById(FindAppShortDescriptionByIdQuery getAppByIdQuery) throws IOException {
        Optional<AppVersionEntity> findAny = appDaoService.findById(getAppByIdQuery.getAppId()).getAppVersions().stream()
                .filter(av -> Objects.equal(getAppByIdQuery.getVersionId(), av.getVersionId())).findAny();
        return findAny.get().getShortDescription();
    }

    private List<AppVersionGalleryImageEntity> mapToAppVersionGalleryImageEntity(List<AppVersionGalleryImage> galleryImages) {
        return galleryImages.stream().map(galleryImage -> new AppVersionGalleryImageEntity(galleryImage.getThumbnailImageName(),
                galleryImage.getProfileImageName(), galleryImage.getOriginalImageName())).collect(Collectors.toList());
    }
}
