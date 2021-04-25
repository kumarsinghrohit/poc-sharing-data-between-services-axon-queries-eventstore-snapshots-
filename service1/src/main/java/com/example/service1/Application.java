package com.example.service1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.commonapi.valueobjects.AppBinaryName;
import com.example.commonapi.valueobjects.AppId;
import com.example.commonapi.valueobjects.AppName;
import com.example.commonapi.valueobjects.AppType;
import com.example.commonapi.valueobjects.AppVersionGalleryImage;
import com.example.commonapi.valueobjects.AppVersionId;
import com.example.commonapi.valueobjects.AppVersionLongDescription;
import com.example.commonapi.valueobjects.AppVersionNumber;
import com.example.commonapi.valueobjects.AppVersionPrice;
import com.example.commonapi.valueobjects.AppVersionShortDescription;
import com.example.commonapi.valueobjects.AppVersionUpdateInfo;
import com.example.commonapi.valueobjects.DeveloperId;
import com.example.service1.command.AddApp1VersionCommand;
import com.example.service1.command.AddAppVersion;
import com.example.service1.command.CreateApp1Command;
import com.example.service1.command.CreateApp;
import com.example.service1.command.UpdateApp1VersionCommand;
import com.example.service1.command.UpdateAppVersion;

@SpringBootApplication
public class Application implements CommandLineRunner {
    @Autowired
    private CommandGateway commandGateway;
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_RESET = "\u001B[0m";

    private Map<String, List<String>> appIdsWithVersionMap = new HashMap<>();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        runApp1Aggregate();// with snapshot
       // runAppAggregate();// without snapshot
    }

    public void runApp1Aggregate() {
        AppType apptype = AppType.LIBRARY;
        for (int i = 0; i < 30; i++) {
            if (i % 2 == 0) {
                apptype = AppType.APP;
            }
            String appName = "app" + i;
            String appId = UUID.randomUUID().toString();
            String appVersionId = UUID.randomUUID().toString();
            List<String> versionIds = new ArrayList<>();
            versionIds.add(appVersionId);
            appIdsWithVersionMap.put(appId, versionIds);
            commandGateway.sendAndWait(CreateApp1Command
                    .builder(new AppId(appId), new AppName(appName), AppType.get(apptype.getValue()),
                            new AppVersionId(appVersionId), new AppVersionNumber(Integer.toString(1)),
                            new AppBinaryName("binary_1"), new DeveloperId("deverloper"),
                            LocalDate.now())
                    .price(new AppVersionPrice(Currency.getInstance("EUR"), 10.0))
                    .shortDescription(new AppVersionShortDescription("shortDescription"))
                    .longDescription(new AppVersionLongDescription("shortDescription"))
                    .galleryImages(Arrays.asList(new AppVersionGalleryImage("image_" + "thumbnail",
                            "image_" + "profile", "image_" + "original")))
                    .build());
        }
        LOGGER.info(ANSI_GREEN_BACKGROUND + "ALL APPS CREATED" + ANSI_RESET);
        Set<String> appIds = appIdsWithVersionMap.keySet();
        addApp1Version(appIds);
        LOGGER.info(ANSI_GREEN_BACKGROUND + "VERSIONS ADDED" + ANSI_RESET);
        String appId1 = appIds.stream().findFirst().get();
        updateApp1Version(appId1, appIdsWithVersionMap.get(appId1).get(0));
        LOGGER.info(ANSI_GREEN_BACKGROUND + "VERSIONS UPDATED" + ANSI_RESET);
    }

    private void updateApp1Version(String appId, String versionId) {
        LOGGER.info("updating app with id {} and versionId {}", appId, versionId);
        for (int i = 0; i < 4000; i++) {
            commandGateway.sendAndWait(UpdateApp1VersionCommand
                    .builder(new AppId(appId), new AppVersionId(versionId),
                            new AppVersionNumber(Integer.toString(i)),
                            new AppBinaryName("binaryName" + i))
                    .price(new AppVersionPrice(Currency.getInstance("EUR"), (double) i))
                    .shortDescription(new AppVersionShortDescription("appShortDescription" + i))
                    .longDescription(new AppVersionLongDescription("appLongDescription" + i))
                    .galleryImages(Arrays.asList(new AppVersionGalleryImage("image" + i + "thumbnail",
                            "image" + i + "profile", "image" + i + "original")))
                    .updateInformation(new AppVersionUpdateInfo("whatsNewInVersion" + i))
                    .build());
        }
        try (FileWriter fw = new FileWriter(new File("a.txt"))) {
            fw.write("appId is:: " + appId + " version id:: " + versionId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addApp1Version(Set<String> appIds) {
        appIds.forEach(appId -> {
            String appVersionId = UUID.randomUUID().toString();
            appIdsWithVersionMap.get(appId).add(appVersionId);
            commandGateway.sendAndWait(AddApp1VersionCommand.builder(new AppId(appId),
                    new AppVersionId(appVersionId), new AppVersionNumber(Integer.toString(2)),
                    new AppBinaryName("binary_2"),
                    LocalDate.now(),
                    new DeveloperId("developerId"))
                    .price(new AppVersionPrice(Currency.getInstance("EUR"), 11.0))
                    .shortDescription(new AppVersionShortDescription("appShortDescription"))
                    .longDescription(new AppVersionLongDescription("appLongDescription"))
                    .galleryImages(Arrays.asList(new AppVersionGalleryImage("image_" + "thumbnail",
                            "image_" + "profile", "image_" + "original")))
                    .updateInformation(new AppVersionUpdateInfo("whatsNewInVersion")).build());
        });
    }

    public void runAppAggregate() {
        AppType apptype = AppType.LIBRARY;
        for (int i = 0; i < 30; i++) {
            if (i % 2 == 0) {
                apptype = AppType.APP;
            }
            String appName = "app" + i;
            String appId = UUID.randomUUID().toString();
            String appVersionId = UUID.randomUUID().toString();
            List<String> versionIds = new ArrayList<>();
            versionIds.add(appVersionId);
            appIdsWithVersionMap.put(appId, versionIds);
            commandGateway.sendAndWait(CreateApp
                    .builder(new AppId(appId), new AppName(appName), AppType.get(apptype.getValue()),
                            new AppVersionId(appVersionId), new AppVersionNumber(Integer.toString(1)),
                            new AppBinaryName("binary_1"), new DeveloperId("deverloper"),
                            LocalDate.now())
                    .price(new AppVersionPrice(Currency.getInstance("EUR"), 10.0))
                    .shortDescription(new AppVersionShortDescription("shortDescription"))
                    .longDescription(new AppVersionLongDescription("shortDescription"))
                    .galleryImages(Arrays.asList(new AppVersionGalleryImage("image_" + "thumbnail",
                            "image_" + "profile", "image_" + "original")))
                    .build());
        }
        LOGGER.info(ANSI_GREEN_BACKGROUND + "ALL APPS CREATED" + ANSI_RESET);
        Set<String> appIds = appIdsWithVersionMap.keySet();
        addAppVersion(appIds);
        LOGGER.info(ANSI_GREEN_BACKGROUND + "VERSIONS ADDED" + ANSI_RESET);
        String appId1 = appIds.stream().findFirst().get();
        updateAppVersion(appId1, appIdsWithVersionMap.get(appId1).get(0));
        LOGGER.info(ANSI_GREEN_BACKGROUND + "VERSIONS UPDATED" + ANSI_RESET);
    }

    private void updateAppVersion(String appId, String versionId) {
        LOGGER.info("updating app with id {} and versionId {}", appId, versionId);
        for (int i = 0; i < 30; i++) {
            commandGateway.sendAndWait(UpdateAppVersion
                    .builder(new AppId(appId), new AppVersionId(versionId),
                            new AppVersionNumber(Integer.toString(i)),
                            new AppBinaryName("binaryName" + i))
                    .price(new AppVersionPrice(Currency.getInstance("EUR"), (double) i))
                    .shortDescription(new AppVersionShortDescription("appShortDescription" + i))
                    .longDescription(new AppVersionLongDescription("appLongDescription" + i))
                    .galleryImages(Arrays.asList(new AppVersionGalleryImage("image" + i + "thumbnail",
                            "image" + i + "profile", "image" + i + "original")))
                    .updateInformation(new AppVersionUpdateInfo("whatsNewInVersion" + i))
                    .build());
        }
        try (FileWriter fw = new FileWriter(new File("a.txt"))) {
            fw.write("appId is:: " + appId + " version id:: " + versionId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addAppVersion(Set<String> appIds) {
        appIds.forEach(appId -> {
            String appVersionId = UUID.randomUUID().toString();
            appIdsWithVersionMap.get(appId).add(appVersionId);
            commandGateway.sendAndWait(AddAppVersion.builder(new AppId(appId),
                    new AppVersionId(appVersionId), new AppVersionNumber(Integer.toString(2)),
                    new AppBinaryName("binary_2"),
                    LocalDate.now(),
                    new DeveloperId("developerId"))
                    .price(new AppVersionPrice(Currency.getInstance("EUR"), 11.0))
                    .shortDescription(new AppVersionShortDescription("appShortDescription"))
                    .longDescription(new AppVersionLongDescription("appLongDescription"))
                    .galleryImages(Arrays.asList(new AppVersionGalleryImage("image_" + "thumbnail",
                            "image_" + "profile", "image_" + "original")))
                    .updateInformation(new AppVersionUpdateInfo("whatsNewInVersion")).build());
        });
    }
}
