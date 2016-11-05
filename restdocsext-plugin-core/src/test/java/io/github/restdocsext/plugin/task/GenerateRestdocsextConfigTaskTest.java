
package io.github.restdocsext.plugin.task;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.restdocsext.model.RestdocsextConfig;
import io.github.restdocsext.model.RestdocsextOperation;
import io.github.restdocsext.model.RestdocsextOperationCollection;
import io.github.restdocsext.plugin.RestdocsextPluginContext;

import static io.github.restdocsext.plugin.RestdocsextPluginContext.ASSETS_SUBDIR;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 *
 * @author Paul Samsotha
 */
public class GenerateRestdocsextConfigTaskTest {
    
    private static final String RESTDOCSEXT_CONFIG_FILENAME = "restdocsext.conf.json";
    
    private static final String SNIPPETS_RESOURCE = "generated-snippets";
    
    private static final String OUTPUT_DIR = "generate-restdocsext-config-task";
    
    private final ObjectMapper mapper = new ObjectMapper();
    
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
    
    private File temp;
    
    @Before
    public void setUp() throws Exception {
        this.temp = tempFolder.newFolder(OUTPUT_DIR);
        FileUtils.forceMkdir(new File(this.temp, "assets/config"));
        
        URL snippetsUrl = Thread.currentThread().getContextClassLoader()
                .getResource(SNIPPETS_RESOURCE);
        File snippetsDir = new File(snippetsUrl.toURI());
        
        File unusedFile = new File(".");
        RestdocsextPluginContext context = RestdocsextPluginContext.builder()
                .baseUri("http://localhost")
                .snippetsDir(snippetsDir)
                .uiOutputDir(this.temp)
                .asciidoctorSourceDir(unusedFile)
                .asciidoctorOutputDir(unusedFile)
                .build();

        GenerateRestdocsextConfigTask task = new GenerateRestdocsextConfigTask();
        task.handle(context);
    }
    
    @Test
    public void should_produce_correct_JSON_output() throws Exception {
        File result = new File(this.temp + File.separator + ASSETS_SUBDIR, RESTDOCSEXT_CONFIG_FILENAME);
        RestdocsextConfig config = mapper.readValue(result, RestdocsextConfig.class);
        assertEquals("http://localhost", config.getBaseUri());
        
        List<RestdocsextOperationCollection> collections = config.getCollections();
        assertNotNull(collections);
        assertEquals(2, collections.size());
        
        RestdocsextOperationCollection collection1 =  collections.get(0);
        assertCollectionDetails(collection1, "Cats");
        
        RestdocsextOperationCollection collection2 = collections.get(1);
        assertCollectionDetails(collection2, "Dogs");
    }
    
    private void assertCollectionDetails(RestdocsextOperationCollection collection, String name) {
        assertEquals(name, collection.getName());
        
        List<RestdocsextOperation> apis = collection.getOperations();
        assertNotNull(apis);
        assertFalse(apis.isEmpty());
        
        RestdocsextOperation api = apis.get(0);
        assertEquals(name, api.getCollection());
        assertEquals("GET", api.getHttpMethod());
    }
}
