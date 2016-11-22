
package io.github.restdocsext.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Map;

/**
 *
 * @author Paul Samsotha
 */
public class RestdocsextPluginContext {
    
    /**
     * The subdirectory of the UI output directory that has the documentation.
     */
    public static final String ASSETS_DOCSDIR = "assets/docs";

    /**
     * The subdirectory of the UI output directory that has the configuration.
     */
    public static final String ASSETS_CONFIGDIR = "assets/config";

    /**
     * The source URL for the UI disttribution
     */
    private String uiSourceUrl
            = "https://raw.githubusercontent.com/RESTDocsEXT/restdocsext-ui/master/dist/restdocsext-ui.zip";

    /**
     * The name of the organization for the UI
     */
    private String organizationName = "RESTDocsEXT UI";

    /**
     * The link for the organization for the UI
     */
    private String organizationLink = "https://github.com/RESTDocsEXT/restdocsext-ui";

    /**
     * The base URI for the REST API
     */
    private final String baseUri;

    /**
     * The home page for the UI.
     */
    private final String homePage;

    /**
     * Then general pages for the UI.
     */
    private final List<String> generalPages;

    /**
     * The output directory for the UI.
     */
    private final File uiOutputDir;

    /**
     * The output directory for Spring REST Docs snippets.
     */
    private final File snippetsDir;

    /**
     * The source directory for Asciidctor to find asciidoc files.
     */
    private final File asciidoctorSourceDir;

    /**
     * The output directory for the Asciidoc generated HTML.
     */
    private final File asciidoctorOutputDir;

    /**
     * The attributes for the Asciidoctor plugin.
     */
    private final Map<String, Object> asciidoctorAttrs;

    /**
     * The logger the plugin
     */
    private final PluginLogger logger;

    /**
     * Contstruct a {@code RestdocsextPluginContext} from an instance of the {@code Builder}.
     *
     * Items that are required will throw a {@code NullPointerException} if the values have not
     * been set in the builder.
     *
     * @param builder
     */
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
        this.homePage = builder.homePage;
        this.asciidoctorOutputDir = builder.asciidoctorOutputDir;
        this.asciidoctorSourceDir = builder.asciidoctorSourceDir;
        this.asciidoctorAttrs = builder.asciidoctorAttrs != null ? builder.asciidoctorAttrs : new HashMap<String, Object>();
        this.organizationName = builder.organizationName != null ? builder.organizationName : this.organizationName;
        this.organizationLink = builder.organizationLink != null ? builder.organizationLink : this.organizationLink;
        this.uiSourceUrl = builder.uiSourceUrl != null ? builder.uiSourceUrl : uiSourceUrl;
        this.generalPages = builder.generalPages != null ? builder.generalPages : new ArrayList<String>();
    }

    /**
     * Get a builder instance to create a {@code RestdocsextPluginContext}.
     *
     * @return the builder.
     */
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
        private List<String> generalPages;

        private File asciidoctorSourceDir;
        private File asciidoctorOutputDir;
        private Map<String, Object> asciidoctorAttrs;

        private Builder() {

        }

        /**
         * Set the {@link PluginLogger}. This is required.
         *
         * @param logger the logger for the plugin.
         * @return the builder.
         */
        public Builder logger(PluginLogger logger) {
            this.logger = logger;
            return this;
        }

        /**
         * Set the base URI for the REST API. This is required.
         *
         * @param baseUri the base URI.
         * @return the builder.
         */
        public Builder baseUri(String baseUri) {
            this.baseUri = baseUri;
            return this;
        }

        /**
         * Set the home page for the UI.
         *
         * @param homePage the home page
         * @return the builder.
         */
        public Builder homePage(String homePage) {
            this.homePage = homePage;
            return this;
        }

        /**
         * Set the output directory for the UI. This is required.
         *
         * @param uiOutputDir the output directory.
         * @return the builder
         */
        public Builder uiOutputDir(File uiOutputDir) {
            this.uiOutputDir = uiOutputDir;
            return this;
        }

        /**
         * Set the source URL for the UI download. The default is the dist location of the UI project.
         *
         * @param url the URL for the UI download.
         * @return the builder.
         */
        public Builder uiSourceUrl(String url) {
            this.uiSourceUrl = url;
            return this;
        }

        /**
         * Set the general pages for the UI.
         *
         * @param generalPages a list of the general pages.
         * @return the builder.
         */
        public Builder generalPages(List<String> generalPages) {
            this.generalPages = generalPages;
            return this;
        }

        /**
         * Set the directory of the snippets output from Spring REST Docs. This is required.
         *
         * @param snippetsDir the directory of the snippets.
         * @return the builder.
         */
        public Builder snippetsDir(File snippetsDir) {
            this.snippetsDir = snippetsDir;
            return this;
        }

        /**
         * Set the directory of the Asciidoc source files where Asciidoctor should look. This is required.
         *
         * @param asciidoctorSourceDir the source directory for asciidoc files.
         * @return the builder.
         */
        public Builder asciidoctorSourceDir(File asciidoctorSourceDir) {
            this.asciidoctorSourceDir = asciidoctorSourceDir;
            return this;
        }

        /**
         * Set the output directory for Asciidoctor to save the generated HTML files. this is required.
         *
         * @param asciidoctorSourceDir the output directory for Asciidoctor.
         * @return the builder.
         */
        public Builder asciidoctorOutputDir(File asciidoctorSourceDir) {
            this.asciidoctorOutputDir = asciidoctorSourceDir;
            return this;
        }

        /**
         * Set attributes for the Asciidoctor pluing.
         *
         * @param asciidoctorAttrs the map of asstributes.
         * @return the builder.
         */
        public Builder asciidoctorAttrs(Map<String, Object> asciidoctorAttrs) {
            this.asciidoctorAttrs = asciidoctorAttrs;
            return this;
        }

        /**
         * Set the name of the organization for the UI. The default will be "RESTDocsEXT".
         *
         * @param organizationName the name of the organization.
         * @return the builder.
         */
        public Builder organizationName(String organizationName) {
            this.organizationName = organizationName;
            return this;
        }

        /**
         * Set the organization link for the UI. The default will be the link to the RESTDocsEXT project.
         *
         * @param organizationLink the link to the organization.
         * @return the builder.
         */
        public Builder organizationLink(String organizationLink) {
            this.organizationLink = organizationLink;
            return this;
        }

        public RestdocsextPluginContext build() {
            return new RestdocsextPluginContext(this);
        }
    }

    /**
     * Get the logger for the plugin.
     *
     * @return the logger.
     */
    public PluginLogger getLogger() {
        return this.logger;
    }

    /**
     * Get the base URI for the REST API.
     *
     * @return the base URI.
     */
    public String getBaseUri() {
        return this.baseUri;
    }

    /**
     * Get the home page for the UI.
     *
     * @return the home page.
     */
    public String getHomePage() {
        return this.homePage;
    }

    /**
     * Gett the output directory for the UI.
     *
     * @return the UI output directory.
     */
    public File getUiOutputDir() {
        return this.uiOutputDir;
    }

    /**
     * Get the directory of the Spring REST Docs snippets.
     *
     * @return the directory of the Spring REST Docs snippets.
     */
    public File getSnippetsDir() {
        return this.snippetsDir;
    }

    /**
     * Get the URL for the UI download.
     *
     * @return the download URL.
     */
    public String getUiSourceUrl() {
        return this.uiSourceUrl;
    }

    /**
     * Get the general pages for the UI.
     *
     * @return a list of the general pages.
     */
    public List<String> getGeneralPages() {
        return this.generalPages;
    }

    /**
     * Get the name of the organization.
     *
     * @return the name of the organization.
     */
    public String getOrganizationName() {
        return this.organizationName;
    }

    /**
     * Get the link for the organization.
     *
     * @return the link for the organization.
     */
    public String getOrganizationLink() {
        return this.organizationLink;
    }

    /**
     * Get the output directory for Asciidoctor generated HTML files.
     * @return
     */
    public File getAsciidoctorOutputDir() {
        return this.asciidoctorOutputDir;
    }

    /**
     * Get the source directory for Asciidoc files.
     * @return
     */
    public File getAsciidoctorSourceDir() {
        return this.asciidoctorSourceDir;
    }

    /**
     * Get a map of the Asciidoctor attributes.
     *
     * @return the map of Asciidoctor attributes.
     */
    public Map<String, Object> getAsciidoctorAttrs() {
        return this.asciidoctorAttrs;
    }

    /**
     * Get the documenation output directory.
     *
     * @return the documentation output directory.
     */
    public File getAssetsDocsDir() {
        return new File(this.uiOutputDir, ASSETS_DOCSDIR);
    }

    /**
     * Get the UI config file directory.
     *
     * @return the UI config file directory.
     */
    public File getAssetsConfigDir() {
        return new File(this.uiOutputDir, ASSETS_CONFIGDIR);
    }
}
