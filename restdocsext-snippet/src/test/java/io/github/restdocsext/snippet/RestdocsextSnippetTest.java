
package io.github.restdocsext.snippet;

import java.io.File;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.springframework.restdocs.operation.Operation;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.restdocsext.model.RestdocsextOperation;
import io.github.restdocsext.snippet.util.OperationBuilder;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;

/**
 * Tests for {@link PlaygroundSnippet}.
 *
 * @author Paul Samsotha
 */
public class RestdocsextSnippetTest {
    
    private static final String OUTPUT_FILE = "restdocsext-ui.json";
    
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
    
    private final ObjectMapper mapper = new ObjectMapper();
    
    @Test
    public void should_have_default_values() throws IOException {
        File outputDir = tempFolder.newFolder();
        final Operation operation = new OperationBuilder("simple", outputDir)
                .build();
        RestdocsextDocumentation.restdocsextSnippet("Simple", "Simple").document(operation);
        
        File result = new File(outputDir + File.separator + "simple", OUTPUT_FILE);
        RestdocsextOperation api = mapper.readValue(result, RestdocsextOperation.class);
        
        // all defaults in OperationBuilder
        assertThat(api.getHttpMethod(), is("GET"));
        assertThat(api.getCollection(), is("Simple"));
        assertThat(api.getName(), is("Simple"));
        assertThat(api.getRequestBody(), is(nullValue()));
        assertThat(api.getPath(), is("/cats"));
    }
    
    @Test
    public void should_have_path_parameters() throws IOException{
        File outputDir = tempFolder.newFolder();
        Operation operation = new OperationBuilder("path-parameters", outputDir)
                .build();
        RestdocsextDocumentation.restdocsextSnippet("Cats", "Get a cat")
                .pathParameters(
                        parameterWithName("id").description("Id of the cat")
                )
                .document(operation);
        
        File result = new File(outputDir + File.separator + "path-parameters", OUTPUT_FILE);
        RestdocsextOperation api = mapper.readValue(result, RestdocsextOperation.class);
        
        assertThat(api.getPathParameters(), hasEntry("id", (Object) "Id of the cat"));
    }
    
    @Test
    public void should_have_request_parameters() throws IOException{
        File outputDir = tempFolder.newFolder();
        Operation operation = new OperationBuilder("request-parameters", outputDir)
                .build();
        RestdocsextDocumentation.restdocsextSnippet("Cats", "Get a cat")
                .requestParameters(
                        parameterWithName("filter").description("filter the cats")
                )
                .document(operation);
        
        File result = new File(outputDir + File.separator + "request-parameters", OUTPUT_FILE);
        RestdocsextOperation api = mapper.readValue(result, RestdocsextOperation.class);
        
        assertThat(api.getRequestParameters(), hasEntry("filter", (Object) "filter the cats"));
    }
    
    @Test
    public void should_have_request_headers() throws IOException {
        File outputDir = tempFolder.newFolder();
        Operation operation = new OperationBuilder("request-headers", outputDir)
                .build();
        RestdocsextDocumentation.restdocsextSnippet("Cats", "Get a cat")
                .requestHeaders(
                        headerWithName("X-Cat-Header").description("Heithcliff")
                )
                .document(operation);
        
        File result = new File(outputDir + File.separator + "request-headers", OUTPUT_FILE);
        RestdocsextOperation api = mapper.readValue(result, RestdocsextOperation.class);
        
        assertThat(api.getRequestHeaders(), hasEntry("X-Cat-Header", (Object) "Heithcliff"));
    }
    
    @Test
    public void should_have_response_headers() throws IOException {
        File outputDir = tempFolder.newFolder();
        Operation operation = new OperationBuilder("response-headers", outputDir)
                .build();
        RestdocsextDocumentation.restdocsextSnippet("Cats", "Get a cat")
                .responseHeaders(
                        headerWithName("X-Cat-Header").description("Heithcliff")
                )
                .document(operation);
        
        File result = new File(outputDir + File.separator + "response-headers", OUTPUT_FILE);
        RestdocsextOperation api = mapper.readValue(result, RestdocsextOperation.class);
        
        assertThat(api.getResponseHeaders(), hasEntry("X-Cat-Header", (Object) "Heithcliff"));
    }
    
    @Test
    public void should_have_request_parts() throws IOException {
        File outputDir = tempFolder.newFolder();
        Operation operation = new OperationBuilder("request-parts", outputDir)
                .build();
        RestdocsextDocumentation.restdocsextSnippet("Cats", "Get a cat")
                .requestParts(
                        partWithName("image").description("image of cat")
                )
                .document(operation);
        
        File result = new File(outputDir + File.separator + "request-parts", OUTPUT_FILE);
        RestdocsextOperation api = mapper.readValue(result, RestdocsextOperation.class);
        
        assertThat(api.getRequestParts(), hasEntry("image", (Object) "image of cat"));
    }
}
