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

import com.example.commonapi.events.AppCreated;
import com.example.commonapi.events.AppVersionAdded;
import com.example.commonapi.valueobjects.AppId;
import com.example.commonapi.valueobjects.AppLifeCycle;
import com.example.commonapi.valueobjects.AppName;
import com.example.commonapi.valueobjects.AppType;
import com.example.commonapi.valueobjects.AppVersionLifeCycle;
import com.example.commonapi.valueobjects.AppVersionVisibility;
import com.example.commonapi.valueobjects.DeveloperId;

@Aggregate
class AppAggregate {

    @AggregateIdentifier
    private AppId id;
    private AppName name;
    private AppType type;
    private DeveloperId developerId;
    @AggregateMember
    private List<AppVersion> appVersions = new ArrayList<>();
    private LocalDate createdOn;
    private LocalDate lastModifiedOn;
    private AppLifeCycle lifeCycle;

    private AppAggregate() {

    }

    @CommandHandler
    private AppAggregate(CreateApp CreateApp) {
        apply(AppCreated.builder(CreateApp.getId(), CreateApp.getName(), CreateApp.getType(),
                CreateApp.getVersionId(), CreateApp.getVersionNumber(), CreateApp.getBinaryName(),
                AppVersionLifeCycle.ACTIVE, AppVersionVisibility.PUBLIC, CreateApp.getDeveloperId(), AppLifeCycle.ACTIVE,
                CreateApp.getCreatedOn())
                .price(CreateApp.getPrice())
                .shortDescription(CreateApp.getShortDescription())
                .longDescription(CreateApp.getLongDescription())
                .galleryImages(CreateApp.getGalleryImages()).build());
    }

    @EventSourcingHandler
    private void on(AppCreated AppCreated) {
        this.id = AppCreated.getId();
        this.name = AppCreated.getName();
        this.type = AppCreated.getType();
        this.appVersions.add(new AppVersion(AppCreated.getVersionId(), AppCreated.getPrice(),
                AppCreated.getShortDescription(), AppCreated.getLongDescription(), AppCreated.getGalleryImages(),
                null, AppCreated.getVersionNumber(), AppCreated.getBinaryName(), AppCreated.getVersionLifeCycle(),
                AppCreated.getVisibility(), AppCreated.getCreatedOn()));
        this.lifeCycle = AppCreated.getLifeCycle();
        this.createdOn = AppCreated.getCreatedOn();
    }

    @CommandHandler
    private void handle(AddAppVersion addAppVersionCommand) {
        apply(AppVersionAdded.builder(addAppVersionCommand.getId(), addAppVersionCommand.getVersionId(),
                addAppVersionCommand.getVersionNumber(), addAppVersionCommand.getBinaryName(), AppVersionLifeCycle.ACTIVE,
                AppVersionVisibility.PUBLIC, addAppVersionCommand.getCreatedOn()).price(addAppVersionCommand.getPrice())
                .shortDescription(addAppVersionCommand.getShortDescription())
                .longDescription(addAppVersionCommand.getLongDescription())
                .galleryImages(addAppVersionCommand.getGalleryImages())
                .updateInformation(addAppVersionCommand.getUpdateInformation()).build());
    }

    @EventSourcingHandler
    private void on(AppVersionAdded AppVersionAdded) {
        this.appVersions.add(new AppVersion(AppVersionAdded.getVersionId(), AppVersionAdded.getPrice(),
                AppVersionAdded.getShortDescription(), AppVersionAdded.getLongDescription(),
                AppVersionAdded.getGalleryImages(),
                AppVersionAdded.getUpdateInformation(), AppVersionAdded.getVersionNumber(),
                AppVersionAdded.getBinaryName(), AppVersionAdded.getVersionLifeCycle(),
                AppVersionAdded.getVisibility(), AppVersionAdded.getCreatedOn()));
        this.lastModifiedOn = AppVersionAdded.getCreatedOn();
    }
}
