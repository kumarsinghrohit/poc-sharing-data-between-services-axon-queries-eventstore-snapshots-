package com.example.service1.projection;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import lombok.AccessLevel;
import lombok.Getter;

@Getter(value = AccessLevel.PACKAGE)
class AppVersionEntity {

    private String versionId;
    private double price;
    private String shortDescription;
    private String longDescription;
    private List<AppVersionGalleryImageEntity> galleryImages;
    private String updateInformation;
    private String versionNumber;
    private String binaryName;
    private String versionLifeCycle;
    private String visibility;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate createdOn;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    AppVersionEntity(@JsonProperty("versionId") String versionId, @JsonProperty("price") double price,
            @JsonProperty("shortDescription") String shortDescription, @JsonProperty("longDescription") String longDescription,
            @JsonProperty("galleryImages") List<AppVersionGalleryImageEntity> galleryImages,
            @JsonProperty("updateInformation") String updateInformation, @JsonProperty("versionNumber") String versionNumber,
            @JsonProperty("binaryName") String binaryName, @JsonProperty("versionLifeCycle") String versionLifeCycle,
            @JsonProperty("visibility") String visibility, @JsonProperty("createdOn") LocalDate createdOn) {
        super();
        this.versionId = versionId;
        this.price = price;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.galleryImages = galleryImages;
        this.updateInformation = updateInformation;
        this.versionNumber = versionNumber;
        this.binaryName = binaryName;
        this.versionLifeCycle = versionLifeCycle;
        this.visibility = visibility;
        this.createdOn = createdOn;
    }
}
