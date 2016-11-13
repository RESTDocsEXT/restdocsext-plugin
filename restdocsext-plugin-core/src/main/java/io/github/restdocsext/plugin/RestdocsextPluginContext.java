
package io.github.restdocsext.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author PaulSamsotha
 */
public class RestdocsextPluginContext {
    
    /**
     * The subdirectory of the UI output directory that has the assets
     */
    public static final String ASSETS_SUBDIR = "assets/config";

    /**
     * The source URL for the UI disttribution
     */
    private String uiSourceUrl
            = "https://raw.githubusercontent.com/RESTDocsEXT/restdocsext-ui/master/dist/restdocsext-ui.zip";

    private String organizationName = "RESTDocsEXT UI";
    private String organizationLink = "https://github.com/RESTDocsEXT/restdocsext-ui";

    private final PluginLogger logger;
    private final String baseUri;
    private final String homePage;

    private final File uiOutputDir;
    private final File snippetsDir;


    private final File asciidoctorSourceDir;
    private final File asciidoctorOutputDir;
    private final Map<String, Object> asciidoctorAttrs;

    private final List<String> docPages = new ArrayList<>();

    private RestdocsextPluginContext(Builder builder) {
        Objects.requireNonNull(builder.logger, "logger must not be null");
        Objects.requireNonNull(builder.baseUri, "baseUri must not be null");
        Objects.requireNonNull(builder.snippetsDir, "snippetsDir must not be null");
        Objects.requireNonNull(builder.uiOutputDir, "uiOutputDir must not be null");
        Objects.requireNonNull(builder.asciidoctorOutputDir, "asciidoctorOutputDir must not be null");
        Objects.requireNonNull(builder.asciidoctorSourceDir, "asciidoctorSourceDir must not be null");
        this.logger = builder.logger;
        this.baseUri = builder.baseUri;
        this.snippetsDir = builder.snippetsDir;
        this.uiOutputDir = builder.uiOutputDir;
        this.asciidoctorOutputDir = builder.asciidoctorOutputDir;
        this.asciidoctorSourceDir = builder.asciidoctorSourceDir;
        this.asciidoctorAttrs = builder.asciidoctorAttrs;
        this.organizationName = builder.organizationName;
        this.organizationLink = builder.organizationLink;
        this.homePage = builder.homePage;
        this.uiSourceUrl = builder.uiSourceUrl != null ? builder.uiSourceUrl : uiSourceUrl;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private PluginLogger logger = new JulPluginLogger();
        private String baseUri;
        private String homePage;
        private String organizationName;
        private String organizationLink;
        private File uiOutputDir;
        private File snippetsDir;
        private String uiSourceUrl;

        private File asciidoctorSourceDir;
        private File asciidoctorOutputDir;
        private Map<String, Object> asciidoctorAttrs;

        private Builder() {

        }

        public Builder logger(PluginLogger logger) {
            this.logger = logger;
            return this;
        }

        public Builder baseUri(String baseUri) {
            this.baseUri = baseUri;
            return this;
        }
        
        public Builder homePage(String homePage) {
            this.homePage = homePage;
            return this;
        }

        public Builder uiOutputDir(File uiOutputDir) {
            this.uiOutputDir = uiOutputDir;
            return this;
        }

        public Builder uiSourceUrl(String url) {
            this.uiSourceUrl = url;
            return this;
        }

        public Builder snippetsDir(File snippetsDir) {
            this.snippetsDir = snippetsDir;
            return this;
        }

        public Builder asciidoctorSourceDir(File asciidoctorSourceDir) {
            this.asciidoctorSourceDir = asciidoctorSourceDir;
            return this;
        }

        public Builder asciidoctorOutputDir(File asciidoctorSourceDir) {
            this.asciidoctorOutputDir = asciidoctorSourceDir;
            return this;
        }

        public Builder asciidoctorAttrs(Map<String, Object> asciidoctorAttrs) {
            this.asciidoctorAttrs = asciidoctorAttrs;
            return this;
        }
        
        public Builder organizationName(String organizationName) {
            this.organizationName = organizationName;
            return this;
        }
        
        public Builder organizationLink(String organizationLink) {
            this.organizationLink = organizationLink;
            return this;
        }

        public RestdocsextPluginContext build() {
            return new RestdocsextPluginContext(this);
        }
    }

    public PluginLogger getLogger() {
        return this.logger;
    }

    public String getBaseUri() {
        return this.baseUri;
    }
    
    public String getHomePage() {
        return this.homePage;
    }

    public File getUiOutputDir() {
        return this.uiOutputDir;
    }

    public File getSnippetsDir() {
        return this.snippetsDir;
    }

    public String getUiSourceUrl() {
        return this.uiSourceUrl;
    }

    public File getAsciidoctorOutputDir() {
        return this.asciidoctorOutputDir;
    }

    public File getAsciidoctorSourceDir() {
        return this.asciidoctorSourceDir;
    }

    public File getAssetsOutputDir() {
        return new File(this.uiOutputDir, ASSETS_SUBDIR);
    }

    public Map<String, Object> getAsciidoctorAttrs() {
        return this.asciidoctorAttrs;
    }

    public List<String> getDocPages() {
        return this.docPages;
    }
    
    public String getOrganizationName() {
        return this.organizationName; 
    }
    
    public String getOrganizationLink() {
        return this.organizationLink;
    }

    public void addDocPage(String docPage) {
        this.docPages.add(docPage);
    }
}
