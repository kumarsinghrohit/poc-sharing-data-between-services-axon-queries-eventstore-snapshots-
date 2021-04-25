package com.example.service1.projection;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Getter;

@Getter(value = AccessLevel.PACKAGE)
class AppVersionGalleryImageEntity {

    private String thumbnailImageName;
    private String profileImageName;
    private String originalImageName;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    AppVersionGalleryImageEntity(@JsonProperty("thumbnailImageName") String thumbnailImageName,
            @JsonProperty("profileImageName") String profileImageName,
            @JsonProperty("originalImageName") String originalImageName) {
        this.thumbnailImageName = thumbnailImageName;
        this.profileImageName = profileImageName;
        this.originalImageName = originalImageName;
    }

}
