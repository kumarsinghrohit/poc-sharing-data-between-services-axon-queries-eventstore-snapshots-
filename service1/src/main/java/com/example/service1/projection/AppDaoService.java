package com.example.service1.projection;

import java.io.IOException;
import java.util.List;

import org.elasticsearch.action.get.GetResponse;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AppDaoService {

    private final AppRepository appRepository;
    private final ObjectMapper objectMapper;

    AppDaoService(final AppRepository appRepository, ObjectMapper objectMapper) {
        this.appRepository = appRepository;
        this.objectMapper = objectMapper;
    }

    void createApp(AppEntity appEntity) throws IOException {
        appRepository.save(appEntity);
    }

    void addVersion(String id, AppVersionEntity appVersionEntity) throws IOException {
        appRepository.addVersion(id, appVersionEntity);
    }

    AppEntity findById(String appId) throws IOException {
        return getAppEntity(appRepository.findById(appId));
    }

    private AppEntity getAppEntity(GetResponse getResponse) {
        return objectMapper.convertValue(getResponse.getSourceAsMap(), AppEntity.class);
    }

    void updateVersionShortDescription(String appId, String versionId, String shortDescription) throws IOException {
        appRepository.updateVersionShortDescription(appId, versionId, shortDescription);
    }

    void updateVersionLongDescription(String appId, String versionId, String longDescription) throws IOException {
        appRepository.updateVersionLongDescription(appId, versionId, longDescription);
    }

    void updateVersionPrice(String appId, String versionId, Double price) throws IOException {
        appRepository.updateVersionPrice(appId, versionId, price);
    }

    void updateVersionGalleryImages(String appId, String versionId, List<AppVersionGalleryImageEntity> galleryImages)
            throws IOException {
        appRepository.updateVersionGalleryImages(appId, versionId, galleryImages);
    }

    void updateVersionUpdateInformation(String appId, String versionId, String updateInformation) throws IOException {
        appRepository.updateVersionUpdateInformation(appId, versionId, updateInformation);
    }

    void updateVersionNumber(String appId, String versionId, String versionNumber) throws IOException {
        appRepository.updateVersionNumber(appId, versionId, versionNumber);
    }

    void updateBinaryName(String appId, String versionId, String binaryName) throws IOException {
        appRepository.updateBinaryName(appId, versionId, binaryName);
    }

    String getAppTypeById(String appId) throws IOException {
        return appRepository.getAppTypeById(appId);
    }
}
