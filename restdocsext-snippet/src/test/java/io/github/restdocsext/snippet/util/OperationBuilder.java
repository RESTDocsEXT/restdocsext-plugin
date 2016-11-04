
package io.github.restdocsext.snippet.util;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.restdocs.ManualRestDocumentation;
import org.springframework.restdocs.RestDocumentationContext;
import org.springframework.restdocs.operation.Operation;
import org.springframework.restdocs.operation.OperationRequest;
import org.springframework.restdocs.operation.OperationRequestFactory;
import org.springframework.restdocs.operation.OperationRequestPart;
import org.springframework.restdocs.operation.Parameters;
import org.springframework.restdocs.operation.StandardOperation;

import io.github.restdocsext.snippet.RestdocsextSnippet;

import static org.springframework.restdocs.generate.RestDocumentationGenerator.ATTRIBUTE_NAME_URL_TEMPLATE;

/**
 * Builder for mock {@link Operation}s.
 *
 * @author Paul Samsotha
 */
public class OperationBuilder {

    private final String name;
    private final File outputDir;
    private final Map<String, Object> attributes = new HashMap<>();

    private byte[] content = new byte[0];
    private HttpMethod method = HttpMethod.GET;
    private URI uri = URI.create("http://localhost/");

    public OperationBuilder(String name, File outputDir) {
        this.name = name;
        this.outputDir = outputDir;
    }

    public Operation build() {
        this.attributes.put(RestDocumentationContext.class.getName(), createContext());
        this.attributes.put(RestdocsextSnippet.ATTRIBUTE_PRETTIFY_SNIPPET, true);
        this.attributes.put(ATTRIBUTE_NAME_URL_TEMPLATE, "/cats");
        
        final OperationRequest request = new OperationRequestFactory()
                .create(this.uri, this.method, this.content, 
                        new HttpHeaders(), new Parameters(), new ArrayList<OperationRequestPart>());
        return new StandardOperation(this.name, request, null, this.attributes);
    }

    private RestDocumentationContext createContext() {
		ManualRestDocumentation manualRestDocumentation = new ManualRestDocumentation(
				this.outputDir.getAbsolutePath());
		manualRestDocumentation.beforeTest(null, null);
		RestDocumentationContext context = manualRestDocumentation.beforeOperation();
		return context;
	}

    public OperationBuilder content(byte[] content) {
        this.content = content;
        return this;
    }

    public OperationBuilder method(String method) {
        this.method = HttpMethod.valueOf(method);
        return this;
    }

    public OperationBuilder uri(String uri) {
        this.uri = URI.create(uri);
        return this;
    }
}

