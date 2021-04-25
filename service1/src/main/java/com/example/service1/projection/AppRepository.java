package com.example.service1.projection;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.xcontent.DeprecationHandler;
import org.elasticsearch.common.xcontent.NamedXContentRegistry;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentParser;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableMap;

/**
 * App repository stores the {@link AppEntity} related information.
 */
@Repository
class AppRepository {

    private static final String INDEX = "indexapps";

    private static final Logger LOGGER = LoggerFactory.getLogger(AppRepository.class);

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String TYPE = "type";
    private static final String APP_VERSIONS = "appVersions";
    private static final String CREATED_ON = "createdOn";
    private static final String DEVELOPER_ID = "developerId";

    /********** App Version Content ************/
    private static final String VERSION_ID = "versionId";
    private static final String PRICE = "price";
    private static final String SHORT_DESCRIPTION = "shortDescription";
    private static final String LONG_DESCRIPTION = "longDescription";
    private static final String GALLERY_IMAGES = "galleryImages";
    private static final String UPDATE_INFORMATION = "updateInformation";
    private static final String VERSION_NUMBER = "versionNumber";
    private static final String BINARY_NAME = "binaryName";
    private static final String VERSION_LIFE_CYCLE = "versionLifeCycle";
    private static final String LIFE_CYCLE = "lifeCycle";
    private static final String VISIBILITY = "visibility";
    private static final int SIZE = 200;
    private static final String SORT_FIELD_KEYWORD = "name.keyword";

    private final RestHighLevelClient client;

    AppRepository(RestHighLevelClient client) {
        this.client = client;
    }

    void save(AppEntity appEntity) throws IOException {
        LOGGER.info("creating  app in app repository with name: {}", appEntity.getName());
        IndexRequest indexRequest = new IndexRequest(INDEX).id(appEntity.getId());
        indexRequest.source(getAppContent(appEntity));
        client.index(indexRequest, RequestOptions.DEFAULT);
    }

    void addVersion(String id, AppVersionEntity appVersionEntity) throws IOException {
        LOGGER.info("adding a new version with number: {} to app with id: {}", appVersionEntity.getVersionNumber(), id);
        UpdateRequest updateRequest = new UpdateRequest(INDEX, id);
        Map<String, Object> parameters = Collections.singletonMap("appVersion",
                getAppVersionContent(appVersionEntity).map());
        Script inline = new Script(ScriptType.INLINE, "painless", "ctx._source.appVersions.add(params.appVersion)", parameters);
        updateRequest.script(inline);
        client.update(updateRequest, RequestOptions.DEFAULT);
    }

    void updateVersionGalleryImages(String appId, String versionId, List<AppVersionGalleryImageEntity> galleryImages)
            throws IOException {
        LOGGER.info("updating the version's galleryImages with id: {} from app with id: {}", versionId, appId);

        // To convert List<AppVersionGalleryImageEntity> to List<Map<String, String>>,
        // keeping structure same.
        List<Map<String, String>> galleryImagesMap = galleryImages.stream().map(appVersionGalleryImageEntity -> {
            Map<String, String> hashMap = new HashMap<>();
            hashMap.put("profileImageName", appVersionGalleryImageEntity.getProfileImageName());
            hashMap.put("thumbnailImageName", appVersionGalleryImageEntity.getThumbnailImageName());
            hashMap.put("originalImageName", appVersionGalleryImageEntity.getOriginalImageName());
            return hashMap;
        }).collect(Collectors.toList());

        updateGalleryImagesInIndex(appId, versionId, galleryImagesMap);
    }

    void updateVersionShortDescription(String appId, String versionId, String shortDescription) throws IOException {
        LOGGER.info("updating the version's short description with id: {} from app with id: {}", appId, versionId);
        UpdateRequest updateRequest = new UpdateRequest(INDEX, appId);
        Map<String, Object> params = ImmutableMap.of(VERSION_ID, versionId, "value", shortDescription);
        Script inline = new Script(ScriptType.INLINE, "painless", createScriptForUpdateVersion(SHORT_DESCRIPTION), params);
        updateRequest.script(inline);
        client.update(updateRequest, RequestOptions.DEFAULT);

    }

    void updateVersionLongDescription(String appId, String versionId, String longDescription) throws IOException {
        LOGGER.info("updating the version's long description with id: {} from app with id: {}", appId, versionId);
        UpdateRequest updateRequest = new UpdateRequest(INDEX, appId);
        Map<String, Object> params = ImmutableMap.of(VERSION_ID, versionId, "value", longDescription);
        Script inline = new Script(ScriptType.INLINE, "painless", createScriptForUpdateVersion(LONG_DESCRIPTION), params);
        updateRequest.script(inline);
        client.update(updateRequest, RequestOptions.DEFAULT);

    }

    void updateVersionPrice(String appId, String versionId, Double price) throws IOException {
        LOGGER.info("updating the version's price with id: {} from app with id: {}", appId, versionId);
        UpdateRequest updateRequest = new UpdateRequest(INDEX, appId);
        Map<String, Object> params = ImmutableMap.of(VERSION_ID, versionId, "value", price);
        Script inline = new Script(ScriptType.INLINE, "painless", createScriptForUpdateVersion(PRICE), params);
        updateRequest.script(inline);
        client.update(updateRequest, RequestOptions.DEFAULT);

    }

    void updateVersionUpdateInformation(String appId, String versionId, String updateInformation) throws IOException {
        LOGGER.info("updating the version's update information with id: {} from app with id: {}", appId, versionId);
        UpdateRequest updateRequest = new UpdateRequest(INDEX, appId);
        Map<String, Object> params = ImmutableMap.of(VERSION_ID, versionId, "value", updateInformation);
        Script inline = new Script(ScriptType.INLINE, "painless", createScriptForUpdateVersion(UPDATE_INFORMATION), params);
        updateRequest.script(inline);
        client.update(updateRequest, RequestOptions.DEFAULT);

    }

    void updateVersionNumber(String appId, String versionId, String versionNumber) throws IOException {
        LOGGER.info("updating the version's number with id: {} from app with id: {}", appId, versionId);
        UpdateRequest updateRequest = new UpdateRequest(INDEX, appId);
        Map<String, Object> params = ImmutableMap.of("versionId", versionId, "value", versionNumber);
        Script inline = new Script(ScriptType.INLINE, "painless", createScriptForUpdateVersion(VERSION_NUMBER), params);
        updateRequest.script(inline);
        client.update(updateRequest, RequestOptions.DEFAULT);

    }

    void updateBinaryName(String appId, String versionId, String binaryName) throws IOException {
        LOGGER.info("updating the version's binary Name with id: {} from app with id: {}", versionId, appId);
        UpdateRequest updateRequest = new UpdateRequest(INDEX, appId);
        Map<String, Object> params = ImmutableMap.of("versionId", versionId, "value", binaryName);
        Script inline = new Script(ScriptType.INLINE, "painless", createScriptForUpdateVersion(BINARY_NAME), params);
        updateRequest.script(inline);
        client.update(updateRequest, RequestOptions.DEFAULT);

    }

    GetResponse findById(String appId) throws IOException {
        GetRequest getRequest = new GetRequest(INDEX, appId);
        return client.get(getRequest, RequestOptions.DEFAULT);
    }

    String getAppTypeById(String appId) throws IOException {
        GetRequest getRequest = new GetRequest(INDEX, appId);
        return client.get(getRequest, RequestOptions.DEFAULT).getSource().get(TYPE).toString();
    }

    private String createScriptForUpdateVersion(String field) {
        return "ctx._source." + APP_VERSIONS + ".stream()"
                + ".filter(appVersion -> Objects.equals(appVersion." + VERSION_ID + " , params.versionId))"
                + ".forEach( version ->{ version." + field + "= params.value;});";
    }

    private void updateGalleryImagesInIndex(String appId, String versionId, List<Map<String, String>> galleryImagesList)
            throws IOException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(GALLERY_IMAGES, galleryImagesList);
        parameters.put("emptyMap", new HashMap<>());
        parameters.put("versionToUpdate", versionId);
        parameters.put("length", galleryImagesList.size());

        String script = "for(int i = 0; i < ctx._source.appVersions.length; i++) {" +
                "  if(Objects.equals(ctx._source.appVersions[i].versionId, params.versionToUpdate)) {" +
                "    ctx._source.appVersions[i].galleryImages.clear();" +
                "    for(int j = 0; j < params.length; j++) {" +
                "       ctx._source.appVersions[i].galleryImages.add(params.galleryImages[j]);" +
                "    }" +
                "  }" +
                "}";

        UpdateRequest updateRequest = new UpdateRequest(INDEX, appId);
        Script inline = new Script(ScriptType.INLINE, "painless", script, parameters);
        updateRequest.script(inline);
        client.update(updateRequest, RequestOptions.DEFAULT);
    }

    private XContentBuilder getAppContent(AppEntity appEntity) throws IOException {
        return jsonBuilder().startObject()
                .field(ID, appEntity.getId())
                .field(NAME, appEntity.getName())
                .field(TYPE, appEntity.getType())
                .startArray(APP_VERSIONS)
                .copyCurrentStructure(getAppVersionContent(appEntity.getAppVersions().get(0)))
                .endArray()
                .timeField(CREATED_ON, appEntity.getCreatedOn())
                .field(DEVELOPER_ID, appEntity.getDeveloperId())
                .field(LIFE_CYCLE, appEntity.getLifeCycle())
                .endObject();
    }

    @SuppressWarnings("unchecked")
    private XContentParser getAppVersionContent(AppVersionEntity appVersionEntity) throws IOException {
        XContentBuilder contentBuilder = jsonBuilder().startObject();
        contentBuilder.field(VERSION_ID, appVersionEntity.getVersionId());
        contentBuilder.field(PRICE, appVersionEntity.getPrice());
        contentBuilder.field(SHORT_DESCRIPTION, appVersionEntity.getShortDescription());
        contentBuilder.field(LONG_DESCRIPTION, appVersionEntity.getLongDescription());
        contentBuilder.field(BINARY_NAME, appVersionEntity.getBinaryName());
//                .array(GALLERY_IMAGES, getAppVersionGalleryImageContent(appVersionEntity.getGalleryImages()))
        contentBuilder.startArray(GALLERY_IMAGES);
        appVersionEntity.getGalleryImages().forEach(galleryImageEntity -> {
            try {
                contentBuilder.startObject();
                contentBuilder.field("thumbnailImageName", galleryImageEntity.getThumbnailImageName());
                contentBuilder.field("profileImageName", galleryImageEntity.getProfileImageName());
                contentBuilder.field("originalImageName", galleryImageEntity.getOriginalImageName());
                contentBuilder.endObject();
            } catch (IOException e) {
                LOGGER.error("Error while mapping the app version gallery content");
            }
        });
        contentBuilder.endArray();
        contentBuilder.field(UPDATE_INFORMATION, appVersionEntity.getUpdateInformation());
        contentBuilder.field(VERSION_NUMBER, appVersionEntity.getVersionNumber());
        contentBuilder.field(VERSION_LIFE_CYCLE, appVersionEntity.getVersionLifeCycle());
        contentBuilder.field(VISIBILITY, appVersionEntity.getVisibility());
        contentBuilder.timeField(CREATED_ON, appVersionEntity.getCreatedOn());
        contentBuilder.endObject();
        return XContentFactory.xContent(XContentType.JSON).createParser(NamedXContentRegistry.EMPTY,
                DeprecationHandler.THROW_UNSUPPORTED_OPERATION,
                Strings.toString(contentBuilder));
    }
}
