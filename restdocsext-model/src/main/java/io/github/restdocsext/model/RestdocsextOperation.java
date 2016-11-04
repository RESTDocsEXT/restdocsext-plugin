
package io.github.restdocsext.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

/**
 * Models an API operation.
 *
 * @author Paul Samsotha
 */
@JsonInclude(Include.NON_NULL)
@JsonDeserialize(builder = RestdocsextOperation.Builder.class)
public class RestdocsextOperation {

    private final String name;
    private final String collection;
    private final String path;
    private final String httpMethod;
    private final String description;
    private final String requestBody;
    
    private final Map<String, Object> pathParameters;
    private final Map<String, Object> requestParameters;
    private final Map<String, Object> requestParts;
    private final Map<String, Object> requestHeaders;
    private final Map<String, Object> responseHeaders;
    private final Map<String, Object> requestFields;
    private final Map<String, Object> responseFields;
    
    private RestdocsextOperation(Builder builder) {
        this.name = builder.name;
        this.collection = builder.collection;
        this.path = builder.path;
        this.httpMethod = builder.httpMethod;
        this.description = builder.description;
        this.requestBody = builder.requestBody;
        this.requestParameters = builder.requestParameters;
        this.requestParts = builder.requestParts;
        this.pathParameters = builder.pathParameters;
        this.requestHeaders = builder.requestHeaders;
        this.responseHeaders = builder.responseHeaders;
        this.requestFields = builder.requestFields;
        this.responseFields = builder.responseFields;
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    @JsonPOJOBuilder(buildMethodName = "buildApi", withPrefix = "")
    public static class Builder {
        
        private String name;
        private String collection;
        private String path;
        private String httpMethod;
        private String description;
        private String requestBody;
        
        private Map<String, Object> pathParameters;
        private Map<String, Object> requestParameters;
        private Map<String, Object> requestParts;
        private Map<String, Object> requestHeaders;
        private Map<String, Object> responseHeaders;
        private Map<String, Object> requestFields;
        private Map<String, Object> responseFields;
        
        private Builder() {
            
        }
        
        public RestdocsextOperation buildApi() {
            return new RestdocsextOperation(this);
        }
        
        public Builder name(String name) {
            this.name = name;
            return this;
        }
        
        public Builder collection(String collection) {
            this.collection = collection;
            return this;
        }
        
        public Builder path(String path) {
            this.path = path;
            return this;
        }
        
        public Builder httpMethod(String httpMethod) {
            this.httpMethod = httpMethod;
            return this;
        }
        
        public Builder description(String description) {
            this.description = description;
            return this;
        }
        
        public Builder requestBody(String requestBody) {
            this.requestBody = requestBody;
            return this;
        }
        
        public Builder requestParameters(Map<String, Object> requestParameters) {
            this.requestParameters = requestParameters;
            return this;
        }
        
        public Builder pathParameters(Map<String, Object> pathParameters) {
            this.pathParameters = pathParameters;
            return this;
        }
        
        public Builder requestParts(Map<String, Object> requestParts) {
            this.requestParts = requestParts;
            return this;
        }
        
        public Builder requestHeaders(Map<String, Object> requestHeaders) {
            this.requestHeaders = requestHeaders;
            return this;
        }
        
        public Builder responseHeaders(Map<String, Object> responseHeaders) {
            this.responseHeaders = responseHeaders;
            return this;
        }
        
        public Builder requestFields(Map<String, Object> requestFields) {
            this.requestFields = requestFields;
            return this;
        }
        
        public Builder responseFields(Map<String, Object> responseFields) {
            this.responseFields = responseFields;
            return this;
        }
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getCollection() {
        return this.collection;
    }

    public String getPath() {
        return this.path;
    }

    public String getHttpMethod() {
        return this.httpMethod;
    }

    public String getDescription() {
        return this.description;
    }

    public String getRequestBody() {
        return this.requestBody;
    }

    public Map<String, Object> getRequestParameters() {
        return this.requestParameters;
    }

    public Map<String, Object> getPathParameters() {
        return this.pathParameters;
    }
    
    public Map<String, Object> getRequestParts() {
        return this.requestParts;
    }

    public Map<String, Object> getRequestHeaders() {
        return this.requestHeaders;
    }

    public Map<String, Object> getResponseHeaders() {
        return this.responseHeaders;
    }
    
    public Map<String, Object> getRequestFields() {
        return this.requestFields;
    }
    
    public Map<String, Object> getResponseFields() {
        return this.responseFields;
    }

    @Override
    public String toString() {
        return "PlaygroundApi{" 
                + "name=" + collection
                + ", collection=" + collection
                + ", path=" + path 
                + ", httpMethod=" + httpMethod 
                + ", description=" + description 
                + ", requestBody=" + requestBody 
                + ", pathParameters=" + pathParameters 
                + ", requestParameters=" + requestParameters 
                + ", requestParts=" + requestParts 
                + ", requestHeaders=" + requestHeaders 
                + ", responseHeaders=" + responseHeaders + '}';
    }
}
