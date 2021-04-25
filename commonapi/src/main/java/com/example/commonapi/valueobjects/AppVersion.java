package com.example.commonapi.valueobjects;

import java.time.LocalDate;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

@Getter(value = AccessLevel.PUBLIC)
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AppVersion implements Comparable<AppVersion> {

    private String versionId;
    private AppVersionPrice price;
    private String shortDescription;
    private String longDescription;
    private @Singular List<AppVersionGalleryImage> galleryImages;
    private String updateInformation;
    private String versionNumber;
    private String binaryName;
    private String versionLifeCycle;
    private String visibility;
    private LocalDate createdOn;

    @Override
    public int compareTo(AppVersion appVersion) {
        return this.getVersionNumber().compareTo(appVersion.getVersionNumber());
    }

    public static class AppVersionBuilder {
        private AppVersionBuilder() {
        }
    }

    public static AppVersionBuilder builder(String versionId, String versionNumber, String binaryName,
            String versionLifeCycle, String visibility, LocalDate createdOn) {
        return new AppVersionBuilder().versionId(versionId).versionNumber(versionNumber).binaryName(binaryName)
                .versionLifeCycle(versionLifeCycle).visibility(visibility).createdOn(createdOn);
    }
}
