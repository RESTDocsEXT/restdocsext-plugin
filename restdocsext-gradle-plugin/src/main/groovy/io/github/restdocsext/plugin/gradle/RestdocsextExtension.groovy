
package io.github.restdocsext.plugin.gradle

import org.gradle.api.Project
import org.gradle.api.tasks.Input 
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.Nested
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputDirectory

/**
 *
 * @author PaulSamsotha
 */
class RestdocsextExtension {

    @Nested
    AsciidoctorConfig asciidoctor

    @OutputDirectory
    File uiOutputDir

    @InputDirectory
    File snippetsDir

    @Input 
    String baseUri

    @Input
    @Optional
    String homePage

    @Input
    @Optional
    String organizationName

    @Input
    @Optional
    String organizationLink

    @Input
    @Optional
    String uiSourceUrl

    @Input 
    boolean noDefaultRepositories = false
	
    final Project project

    RestdocsextExtension(Project project) {
        this.project = project
    }

    def asciidoctor(Closure closure) {
        this.asciidoctor = project.configure(new AsciidoctorConfig(), closure)
    }

    def setAsciidoctor(AsciidoctorConfig asciidoctor) {
        this.asciidoctor = asciidoctor
    }

    def getAsciidoctor() {
        this.asciidoctor
    }

    def uiOutputDir(File uiOutputDir) {
        this.uiOutputDir = uiOutputDir
    }

    def setUiOutputDir(File uiOutputDir) {
        this.uiOutputDir = uiOutputDir
    }

    def getUiOutputDir() {
        this.uiOutputDir
    }

    def snippetsDir(File snippetsDir) {
        this.snippetsDir = snippetsDir
    }

    def setSnippetsDir(File snippetsDir) {
        this.snippetsDir = snippetsDir
    }

    def getSnippetsDir() {
        this.snippetsDir
    }

    def getBaseUri() {
        this.baseUri
    }

    def baseUri(String baseUri) {
        this.baseUri = baseUri
    }

    def setBaseUri(String baseUri) {
        this.baseUri = baseUri
    }

    def getHomePage() {
        return this.homePage
    }

    def homePage(String homePage) {
        this.homePage = homePage;
    }

    def setHomePage(String homePage) {
        this.homePage = homePage;
    }

    def getOrganizationName() {
        return this.organizationName;
    }

    def organizationNmae(String organizationName) {
        this.organizationName = organizationName;
    }

    def setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    def getOrganizationLink() {
        return this.organizationLink
    }

    def organizationLink(String organizationLink) {
        this.organizationLink = organizationLink;
    }

    def setOrganizationLink(String organizationLink) {
        this.organizationLink = organizationLink;
    }

    def getUiSourceUrl() {
        return this.uiSourceUrl;
    }

    def uiSourceUrl(String uiSourceUrl) {
        this.uiSourceUrl = uiSourceUrl;
    }

    def setUiSourceUrl(String uiSourceUrl) {
        this.uiSourceUrl = uiSourceUrl;
    }
}

