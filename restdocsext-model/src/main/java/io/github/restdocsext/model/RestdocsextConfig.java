

package io.github.restdocsext.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author Paul Samsotha
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestdocsextConfig {
    
    private String baseUri;
    private List<RestdocsextOperationCollection> collections;
    private UiConfig uiConfig = new UiConfig(this);

    public String getBaseUri() {
        return this.baseUri;
    }

    public RestdocsextConfig setBaseUri(String baseUri) {
        this.baseUri = baseUri;
        return this;
    }

    public List<RestdocsextOperationCollection> getCollections() {
        return this.collections;
    }

    public void addCollection(RestdocsextOperationCollection collection) {
        if (this.collections == null) {
            this.collections = new ArrayList<>();
        }
        this.collections.add(collection);
    }

    @JsonProperty("config")
    public UiConfig getUiConfig() {
        return this.uiConfig;
    }
    
    @JsonProperty("config")
    public void setUiConfig(UiConfig uiConfig) {
        this.uiConfig = uiConfig;
    }
    
    public UiConfig ui() {
        return this.uiConfig;
    }

    @Override
    public String toString() {
        return "RestdocsextConfig{" 
                + "baseUri=" + baseUri
                + ", collections=" + collections
                + ", uiConfig=" + uiConfig + '}';
    }
 
    public static class UiConfig {
        
        private String theme;
        private String homePage;
        private String organizationName;
        private String organizationLink;
        private List<String> pages;
        
        private RestdocsextConfig parent;
        
        public UiConfig() {}
        
        UiConfig(RestdocsextConfig parent) {
            this.parent = parent;
        }
        
        public UiConfig theme(String theme) {
            this.theme = theme;
            return this;
        }
        
        public String getTheme() {
            return this.theme;
        }
        
        public UiConfig homePage(String homePage) {
            this.homePage = homePage;
            return this;
        }
        
        public String getHomePage() {
            return this.homePage;
        }
        
        public UiConfig organizationName(String organizationName) {
            this.organizationName = organizationName;
            return this;
        }
        
        public String getOrganizationName() {
            return this.organizationName;
        }
        
        public UiConfig organizationLink(String organizationLink) {
            this.organizationLink = organizationLink;
            return this;
        }
        
        public String getOrganizationLink() {
            return this.organizationLink;
        }
        
        public UiConfig addPage(String page) {
            if (this.pages == null) {
                this.pages = new ArrayList<>();
            }
            this.pages.add(page);
            return this;
        }
        
        public UiConfig setPages(List<String> pages) {
            this.pages = pages;
            return this;
        }
        
        public List<String> getPages() {
            return this.pages;
        }
        
        public RestdocsextConfig parent() {
            return this.parent;
        }

        @Override
        public String toString() {
            return "UiConfig{"
                    + "theme=" + theme
                    + ", homePage=" + homePage
                    + ", organizationName=" + organizationName
                    + ", organizationLink=" + organizationLink
                    + ", pages=" + pages + '}';
        }
    }
}
