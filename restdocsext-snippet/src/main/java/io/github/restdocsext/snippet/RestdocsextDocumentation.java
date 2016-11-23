
package io.github.restdocsext.snippet;

import java.util.Map;

/**
 * Public entry point for creating a RESTDocsEXT snippet.
 *
 * @author Paul Samsotha
 */
public class RestdocsextDocumentation {
    
    private RestdocsextDocumentation() {

    }

    /**
     * Factory method to create a {@link RestdocsextSnippet}. A collection name is required for 
     * creation. This collection name will separate different collections in the RESTDocsEXT UI.
     * 
     * @param collectionName the name of the collection
     * @param operationName the short name of the operation (e.g. "Get a dog")
     * @return the snippet
     */
    public static RestdocsextSnippet restdocsextSnippet(String collectionName, String operationName) {
        return new RestdocsextSnippet(collectionName, operationName);
    }

    /**
     * Factory method to create a {@link RestdocsextSnippet}. A collection name is required for 
     * creation. This collection name will separate different collections in the RESTDocsEXT UI.
     * 
     * Along with the collection name, this factory overload allows for specifying extra
     * attributes.
     * 
     * @param collectionName the name of the collection
     * @param operationName the short name of the operation (e.g. "Get a dog")
     * @return the snippet
     * @param attributes extra attributes.
     */
    public static RestdocsextSnippet restdocsextSnippet(String collectionName, String operationName,
            Map<String, Object> attributes) {
        return new RestdocsextSnippet(collectionName, operationName, attributes);
    }
}
