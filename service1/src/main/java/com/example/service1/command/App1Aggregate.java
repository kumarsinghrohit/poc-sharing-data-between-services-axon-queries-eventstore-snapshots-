package com.example.service1.command;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.commonapi.events.AppCreated;
import com.example.commonapi.events.AppVersionAdded;
import com.example.commonapi.valueobjects.AppId;
import com.example.commonapi.valueobjects.AppLifeCycle;
import com.example.commonapi.valueobjects.AppName;
import com.example.commonapi.valueobjects.AppType;
import com.example.commonapi.valueobjects.AppVersionLifeCycle;
import com.example.commonapi.valueobjects.AppVersionVisibility;
import com.example.commonapi.valueobjects.DeveloperId;

@Aggregate(snapshotTriggerDefinition = "app1SnapshotTrigger")
public class App1Aggregate {
    private static final Logger LOGGER = LoggerFactory.getLogger(App1Aggregate.class);
    @AggregateIdentifier
    private AppId id;
    private AppName name;
    private AppType type;
    private DeveloperId developerId;
    @AggregateMember
    private List<App1Version> appVersions = new ArrayList<>();
    private LocalDate createdOn;
    private LocalDate lastModifiedOn;
    private AppLifeCycle lifeCycle;

    private App1Aggregate() {

    }

    @CommandHandler
    private App1Aggregate(CreateApp1Command createAppCommand) {
        apply(AppCreated.builder(createAppCommand.getId(), createAppCommand.getName(), createAppCommand.getType(),
                createAppCommand.getVersionId(), createAppCommand.getVersionNumber(), createAppCommand.getBinaryName(),
                AppVersionLifeCycle.ACTIVE, AppVersionVisibility.PUBLIC, createAppCommand.getDeveloperId(), AppLifeCycle.ACTIVE,
                createAppCommand.getCreatedOn())
                .price(createAppCommand.getPrice())
                .shortDescription(createAppCommand.getShortDescription())
                .longDescription(createAppCommand.getLongDescription())
                .galleryImages(createAppCommand.getGalleryImages()).build());
    }

    @EventSourcingHandler
    private void on(AppCreated appCreated) {
        LOGGER.info("AppCreated occured with appId {}", appCreated.getId().getValue());
        this.id = appCreated.getId();
        this.name = appCreated.getName();
        this.type = appCreated.getType();
        this.appVersions.add(new App1Version(appCreated.getVersionId(), appCreated.getPrice(),
                appCreated.getShortDescription(), appCreated.getLongDescription(), appCreated.getGalleryImages(),
                null, appCreated.getVersionNumber(), appCreated.getBinaryName(), appCreated.getVersionLifeCycle(),
                appCreated.getVisibility(), appCreated.getCreatedOn()));
        this.lifeCycle = appCreated.getLifeCycle();
        this.createdOn = appCreated.getCreatedOn();
    }

    @CommandHandler
    private void handle(AddApp1VersionCommand addAppVersionCommand) {
        apply(AppVersionAdded.builder(addAppVersionCommand.getId(), addAppVersionCommand.getVersionId(),
                addAppVersionCommand.getVersionNumber(), addAppVersionCommand.getBinaryName(), AppVersionLifeCycle.ACTIVE,
                AppVersionVisibility.PUBLIC, addAppVersionCommand.getCreatedOn()).price(addAppVersionCommand.getPrice())
                .shortDescription(addAppVersionCommand.getShortDescription())
                .longDescription(addAppVersionCommand.getLongDescription())
                .galleryImages(addAppVersionCommand.getGalleryImages())
                .updateInformation(addAppVersionCommand.getUpdateInformation()).build());
    }

    @EventSourcingHandler
    private void on(AppVersionAdded appVersionAdded) {
        LOGGER.info("AppVersionAdded occured with appId {}", appVersionAdded.getId().getValue());
        this.appVersions.add(new App1Version(appVersionAdded.getVersionId(), appVersionAdded.getPrice(),
                appVersionAdded.getShortDescription(), appVersionAdded.getLongDescription(),
                appVersionAdded.getGalleryImages(),
                appVersionAdded.getUpdateInformation(), appVersionAdded.getVersionNumber(),
                appVersionAdded.getBinaryName(), appVersionAdded.getVersionLifeCycle(),
                appVersionAdded.getVisibility(), appVersionAdded.getCreatedOn()));
        this.lastModifiedOn = appVersionAdded.getCreatedOn();
    }
}
