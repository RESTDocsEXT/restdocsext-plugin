
package io.github.restdocsext.plugin.gradle

import org.gradle.api.Project
import org.gradle.api.tasks.Input 
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.Nested
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
}

