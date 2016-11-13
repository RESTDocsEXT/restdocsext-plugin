
package io.github.restdocsext.snippet;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.restdocs.RestDocumentationContext;
import org.springframework.restdocs.generate.RestDocumentationGenerator;
import org.springframework.restdocs.headers.HeaderDescriptor;
import org.springframework.restdocs.operation.Operation;
import org.springframework.restdocs.operation.OperationRequest;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.restdocs.request.RequestPartDescriptor;
import org.springframework.restdocs.snippet.AbstractDescriptor;
import org.springframework.restdocs.snippet.IgnorableDescriptor;
import org.springframework.restdocs.snippet.ModelCreationException;
import org.springframework.restdocs.snippet.PlaceholderResolverFactory;
import org.springframework.restdocs.snippet.RestDocumentationContextPlaceholderResolver;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.restdocs.snippet.StandardWriterResolver;
import org.springframework.restdocs.snippet.WriterResolver;
import org.springframework.restdocs.templates.TemplateFormat;
import org.springframework.util.Assert;
import org.springframework.util.PropertyPlaceholderHelper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.github.restdocsext.model.RestdocsextOperation;

/**
 * Spring REST Docs snippet for generating  snippets that will be used for the configuration
 * of the RESTDocsEXT UI. All of the snippets will be compiled into one file using a build
 * system plugin.
 * 
 * A {@code RestdocsextSnippet} should be created by calling one of the static factory methods
 * in {@link RestdocsextDocumentation}, passing in the collection name as the first argument
 * and the operation name as the second argument.
 * 
 * <pre>
 * RestdocsextDocumentation.restdocsextSnippet("Dogs", "Get a Dog")
 * </pre>
 * 
 * This is the minimum required. In this snippet, there are fluent calls that allow to pass
 * different types of descriptors, namely for request headers, response headers,
 * path parameter descriptors, request parameter descriptors, and request part (multipart)
 * descriptors.
 * 
 * Descriptors should be reused from within the same operations. For example
 * 
 * <pre>
 * HeaderDescriptor[] headerDescriptors = {
 *     headerWithName("X-Custom-Header").description("A custom header")
 * };
 * 
 * document("get-all-cats", 
 *         requestHeaders(headerDescriptors), 
 *         restdocsextSnippet("Cats", "Get a Cat").requestHeaders(headerDescriptors))
 * </pre>
 * 
 * Only applicable descriptors need to be set.
 *
 * @author Paul Samsotha
 */
public class RestdocsextSnippet implements Snippet {
    
    /**
     * The attribute for prettifying the snippet JSON output. Normally this would not be needed, but
     * may be useful for debugging.
     */
    public static final String ATTRIBUTE_PRETTIFY_SNIPPET
            = "io.github.restdocsext.snippet.prettifySnippetOutput";
    
    /**
     * The name of the snippet. This will translate into the snippet file name.
     */
    private static final String SNIPPET_NAME = "restdocsext-ui";
    
    /**
     * The template format for this snippet. We are not using any templates,
     * but this will allow us to produce .json files
     */
    private static final TemplateFormat TEMPLATE_FORMAT = new TemplateFormat() {

		@Override
		public String getId() {
			return "json";
		}

		@Override
		public String getFileExtension() {
			return "json";
		}
	};
    
    /**
     * Request headers descriptors.
     */
    private List<HeaderDescriptor> requestHeaderDescriptors;

    /**
     * Response header descriptors.
     */
    private List<HeaderDescriptor> responseHeaderDescriptors;

    /**
     * The path parameter descriptors.
     */
    private List<ParameterDescriptor> pathParameterDescriptors;

    /**
     * The request parameter descriptors.
     */
    private List<ParameterDescriptor> requestParameterDescriptors;

    /**
     * The multipart request part descriptors.
     */
    private List<RequestPartDescriptor> requestPartDescriptors;
    
    /**
     * The request field descriptors.
     */
    private List<FieldDescriptor> requestFieldDescriptors;
    
    /**
     * The response field descriptors.
     */
    private List<FieldDescriptor> responseFieldDescriptors;

    /**
     * The name of the collection. Each snippet is grouped into a collection, which which are
     * displayed in groups in the UI.
     */
    private final String collectionName;
    
    /**
     * The description of the operation. This is mandatory, as it is required for the UI.
     */
    private final String operationName;

    /**
     * Extra snippet attributes.
     */
    private final Map<String, Object> attributes = new HashMap<>();
    
    /**
     * Constructs this snippet with the collection name. This is the minimum required for
     * construction.
     *
     * @param collectionName the name of the collection
     * @param operationName the short name of the operation (e.g. "Get a dog")
     */
    protected RestdocsextSnippet(String collectionName, String operationName) {
        this(collectionName, operationName, null);
    }

    /**
     * Constructs this snippet with the name of the collection, a description, and any extra attributes.
     *
     * @param collectionName the collection name.
     * @param operationName the short name of the operation (e.g. "Get a dog")
     * @param attributes attributes to add to the snippet
     */
    protected RestdocsextSnippet(String collectionName, String operationName, Map<String, Object> attributes) {
        if (attributes != null) {
            this.attributes.putAll(attributes);
        }
        Assert.notNull(collectionName, "Did you forget to specify a collection name via the"
                + " PlaygroundSnippet.ATTRIBUTE_COLLECTION_NAME attribute?");
        this.collectionName = collectionName;
        this.operationName = operationName;
    }
    
 

    @Override
    public void document(Operation operation) throws IOException {
        RestDocumentationContext context = (RestDocumentationContext) operation
                .getAttributes().get(RestDocumentationContext.class.getName());
        
        PlaceholderResolverFactory resolverFactory = new SingleInstancePlaceholderResolverFactory(
                new RestDocumentationContextPlaceholderResolver(context));
        WriterResolver writerResolver = new StandardWriterResolver(
				resolverFactory, "UTF-8", TEMPLATE_FORMAT);
        
        try (Writer writer = writerResolver.resolve(operation.getName(), SNIPPET_NAME, context)) {
            serializeApi(writer, getRestdocsextOperation(operation), isPrettify(operation));
        }
    }
    
    /**
     * Generate the {@link RestdocsextOperation} from the {@link Operation}.
     * 
     * @param operation the operation
     * @return the Playground API model
     */
    private RestdocsextOperation getRestdocsextOperation(Operation operation) {
        OperationRequest request = operation.getRequest();
        return RestdocsextOperation.builder()
                .collection(this.collectionName)
                .name(this.operationName)
                .path(getUriTemplate(operation))
                .httpMethod(request.getMethod().toString())
                .pathParameters(convertDescriptors(this.pathParameterDescriptors))
                .requestParameters(convertDescriptors(this.requestParameterDescriptors))
                .requestHeaders(convertDescriptors(this.requestHeaderDescriptors))
                .responseHeaders(convertDescriptors(this.responseHeaderDescriptors))
                .requestParts(convertDescriptors(this.requestPartDescriptors))
                .requestFields(convertDescriptors(this.requestFieldDescriptors))
                .responseFields(convertDescriptors(this.responseFieldDescriptors))
                .requestBody("".equals(request.getContentAsString()) ? null : request.getContentAsString())
                .buildApi();
    }
    
    /**
     * Set the request header parameter descriptors for this snippet.
     * 
     * @param requestHeaderDescriptors the request header descriptors
     * @return this snippet
     */
    public RestdocsextSnippet requestHeaders(HeaderDescriptor... requestHeaderDescriptors) {
        this.requestHeaderDescriptors = Arrays.asList(requestHeaderDescriptors);
        return this;
    }
    
    /**
     * Set the response header descriptors for this snippet.
     * 
     * @param responseHeaderDescriptors the response header descriptors
     * @return this snippet
     */
    public RestdocsextSnippet responseHeaders(HeaderDescriptor... responseHeaderDescriptors) {
        this.responseHeaderDescriptors = Arrays.asList(responseHeaderDescriptors);
        return this;
    }
    
    /**
     * Set the path parameter descriptors for this snippet.
     * 
     * @param pathParameterDescriptors the path parameter snippets
     * @return this snippet
     */
    public RestdocsextSnippet pathParameters(ParameterDescriptor... pathParameterDescriptors) {
        this.pathParameterDescriptors = Arrays.asList(pathParameterDescriptors);
        return this;
    }
    
    /**
     * Set the request parameter descriptors for this snippet.
     * 
     * @param requestParameterDescriptors the request parameter descriptors
     * @return this snippet
     */
    public RestdocsextSnippet requestParameters(ParameterDescriptor... requestParameterDescriptors) {
        this.requestParameterDescriptors = Arrays.asList(requestParameterDescriptors);
        return this;
    }
    
    /**
     * Set the multipart request part descriptors for this snippet.
     * 
     * @param requestPartDescriptors the request part descriptors
     * @return this snippet
     */
    public RestdocsextSnippet requestParts(RequestPartDescriptor... requestPartDescriptors) {
        this.requestPartDescriptors = Arrays.asList(requestPartDescriptors);
        return this;
    }
    
    /**
     * Set the request field descriptors for this snippet.
     * 
     * @param requestFieldDescriptors the request field descriptors
     * @return this snippet
     */
    public RestdocsextSnippet requestFields(FieldDescriptor... requestFieldDescriptors) {
        this.requestFieldDescriptors = Arrays.asList(requestFieldDescriptors);
        return this;
    }
   
    /**
     * Set the response field descriptors for this snippet.
     * 
     * @param responseFieldDescriptors the request field descriptors
     * @return this snippet
     */
    public RestdocsextSnippet responseFields(FieldDescriptor... responseFieldDescriptors) {
        this.responseFieldDescriptors = Arrays.asList(responseFieldDescriptors);
        return this;
    }
    
    private static String getUriTemplate(Operation operation) {
        return (String) operation.getAttributes()
                .get(RestDocumentationGenerator.ATTRIBUTE_NAME_URL_TEMPLATE);
    }

    private static boolean isPrettify(Operation operation) {
        Object prettify = operation.getAttributes().get(ATTRIBUTE_PRETTIFY_SNIPPET);
        if (prettify == null) {
            return true;
        }
        return (prettify instanceof Boolean) && ((Boolean) prettify);
    }
    
    /**
     * Convert a list of {@link AbstractDescriptor}s to a map of their name and descriptions.
     * 
     * @param descriptors the list of descriptors.
     * @return the map of name and descriptions.
     */
    private static Map<String, Object> convertDescriptors(List<? extends AbstractDescriptor> descriptors) {
        if (descriptors == null || descriptors.isEmpty()) {
            return null;
        }
        Map<String, Object> result = new LinkedHashMap<>();
        for (AbstractDescriptor descriptor : descriptors) {
            if ((descriptor instanceof IgnorableDescriptor)
                    && ((IgnorableDescriptor) descriptor).isIgnored()) {
                continue;
            }
            result.put(getKey(descriptor), descriptor.getDescription());
        }

        return result;
    }
    
    /**
     * Get the key from the descriptor. Instead of repeating the same operations in different
     * variation of the {@code convertDescriptor} method, we need to cast to get the key,
     * as the key property is only available in the abstract descriptor sub-classes.
     * 
     * @param descriptor the descriptor
     * @return the name of the descriptor
     */
    private static String getKey(AbstractDescriptor descriptor) {
        if (descriptor instanceof HeaderDescriptor) {
            return HeaderDescriptor.class.cast(descriptor).getName();
        } else if (descriptor instanceof ParameterDescriptor) {
            return ParameterDescriptor.class.cast(descriptor).getName();
        } else if (descriptor instanceof RequestPartDescriptor) {
            return RequestPartDescriptor.class.cast(descriptor).getName();
        } else if (descriptor instanceof FieldDescriptor) {
            return FieldDescriptor.class.cast(descriptor).getPath();
        }
        throw new IllegalArgumentException(
                "descriptor must be HeaderDescriptor or ParameterDescriptor or RequestPartDescriptor");
    }
    
    private static void serializeApi(Writer writer, RestdocsextOperation operation, boolean prettify) {
        try {
            new ObjectMapper()
                    .configure(SerializationFeature.INDENT_OUTPUT, prettify)
                    .writeValue(writer, operation);
        } catch (IOException ex) {
            throw new ModelCreationException("Cannot serialize api to JSON", ex);
        }
    }
    
    private static final class SingleInstancePlaceholderResolverFactory
			implements PlaceholderResolverFactory {

		private final PropertyPlaceholderHelper.PlaceholderResolver placeholderResolver;

		private SingleInstancePlaceholderResolverFactory(
				PropertyPlaceholderHelper.PlaceholderResolver placeholderResolver) {
			this.placeholderResolver = placeholderResolver;
		}

		@Override
		public PropertyPlaceholderHelper.PlaceholderResolver create(RestDocumentationContext context) {
			return this.placeholderResolver;
		}
	}
}
