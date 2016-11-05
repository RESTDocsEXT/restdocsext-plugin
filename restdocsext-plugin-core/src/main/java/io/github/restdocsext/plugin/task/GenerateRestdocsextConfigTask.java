

package io.github.restdocsext.plugin.task;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.restdocsext.model.RestdocsextConfig;
import io.github.restdocsext.model.RestdocsextOperation;
import io.github.restdocsext.model.RestdocsextOperationCollection;
import io.github.restdocsext.plugin.PluginLogger;
import io.github.restdocsext.plugin.RestdocsextPluginContext;
import io.github.restdocsext.plugin.RestdocsextPluginException;

/**
 * A task to generate the {@code restdocsext.conf.json} file. The result should be the
 * {@code restdocsext.conf.json} file placed in the {@code $&#123;uiOutputDir$&#125;/assets/config}.
 *
 * @author Paul Samsotha
 */
public class GenerateRestdocsextConfigTask implements RestdocsextPluginTask {
    
     /**
     * The file name that all the RestDocs Playground snippet should produces for each operation.
     */
    private static final String RESTDOCSEXT_FILE_NAME = "restdocsext-ui.json";
    
    /**
     * The configuration file name for the Playground UI.
     */
    private static final String RESTDOCSEXT_CONFIG_FILENAME = "restdocsext.conf.json";

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void handle(RestdocsextPluginContext context) throws RestdocsextPluginException {
        context.getLogger().info("Starting to generate Playground configuration file.");
        RestdocsextConfig config = generate(context.getSnippetsDir(),context.getLogger())
                .setBaseUri(context.getBaseUri())
                .ui()
                .setPages(context.getDocPages())
                .homePage(context.getHomePage())
                .organizationName(context.getOrganizationName())
                .organizationLink(context.getOrganizationLink())
                .parent();

        File assetsOutputDir = context.getAssetsOutputDir();
        if (!assetsOutputDir.exists() || !assetsOutputDir.isDirectory()) {
            throw new RestdocsextPluginException(assetsOutputDir
                    + " does not exist or is not a directory");
        }
        File configFile = new File(context.getAssetsOutputDir(), RESTDOCSEXT_CONFIG_FILENAME);
        serializeConfig(config, configFile);
        context.getLogger().info("Finished generating " + configFile);
    }
    
    private void serializeConfig(RestdocsextConfig details, File outFile) {
        try {
            mapper.writeValue(outFile, details);
        } catch (IOException ex) {
            throw new RestdocsextPluginException("Could not serialize api details to " + outFile, ex);
        }
    }

    /*
     * Generate the {@link PlaygroundConfig} instance from a directory containing
     * playground-api.adoc files.
     *
     * @param sourceDirectory the directory of the generate playground-api.adoc files.
     * @param log the Maven Mojo logger.
     * @return the API details.
     */
    private static RestdocsextConfig generate(File snippetsDir, PluginLogger log) {
        RestdocsextConfig details = new RestdocsextConfig();

        Map<String, List<RestdocsextOperation>> collectionMap = mapApiCollections(getAllApis(snippetsDir, log));
        for (Map.Entry<String, List<RestdocsextOperation>> entry : collectionMap.entrySet()) {
            RestdocsextOperationCollection collection = new RestdocsextOperationCollection(entry.getKey(), entry.getValue());
            details.addCollection(collection);
        }
        return details;
    }

    /*
     * Maps {@code PLaygroundApis} to the collection. We want the JSON result
     * to have the name of the API group/collection as a key, with all the collection
     * members in a list as the value.
     */
    private static Map<String, List<RestdocsextOperation>> mapApiCollections(List<RestdocsextOperation> apis) {
        Map<String, List<RestdocsextOperation>> collectionMap = new HashMap<>();
        for (RestdocsextOperation api : apis) {
            String collection = api.getCollection();
            if (collectionMap.containsKey(collection)) {
                List<RestdocsextOperation> apiValue = collectionMap.get(collection);
                apiValue.add(api);
            } else {
                List<RestdocsextOperation> apiValue = new ArrayList<>();
                apiValue.add(api);
                collectionMap.put(collection, apiValue);
            }
        }
        return collectionMap;
    }

    /*
     * Create a list of {@code PlaygroundApis} from all the {@code playground-api.adoc} files
     */
    private static List<RestdocsextOperation> getAllApis(File inDir, PluginLogger log) {
        List<RestdocsextOperation> apis = new ArrayList<>();
        List<File> playgroundFiles = new ArrayList<>();
        getPlaygroundFiles(playgroundFiles, inDir);
        if (playgroundFiles.isEmpty()) {
            log.warn("There are no playground-api.adoc files in " + inDir);
        }
        for (File playgroundFile : playgroundFiles) {
            RestdocsextOperation api = deserializeApi(playgroundFile);
            apis.add(api);
        }
        return apis;
    }

    /*
     * Recursively get all the restdocsext-ui.json files.
     */
    private static void getPlaygroundFiles(List<File> list, File file) {
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                getPlaygroundFiles(list, f);
            }
        } else {
            if (RESTDOCSEXT_FILE_NAME.equals(file.getName())) {
                list.add(file);
            }
        }
    }

    private static RestdocsextOperation deserializeApi(File apiFile) {
        try {
            return mapper.readValue(apiFile, RestdocsextOperation.class);
        } catch (IOException ex) {
            throw new RestdocsextPluginException(
                    "Could not convert " + apiFile + " to java object. ", ex);
        }
    }
    
}
