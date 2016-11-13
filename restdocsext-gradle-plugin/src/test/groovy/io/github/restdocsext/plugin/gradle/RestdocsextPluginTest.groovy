package io.github.restdocsext.plugin.gradle

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

/**
 * Tests for {@code RestdocsextPlugin}.
 *
 * @author Paul Samsotha
 */
class RestdocsextPluginTest extends Specification {

    Project project
    RestdocsextPlugin plugin

    def setup() {
        project = ProjectBuilder.builder().build()
    }

    def 'plugin adds the restdocsext extension'() {
        given:
            plugin = new RestdocsextPlugin()

        when:
            plugin.apply(project)

        then:
            def extension = project.extensions.getByName(RestdocsextPlugin.RESTDOCSEXT)
            extension != null
            extension instanceof RestdocsextExtension
    }

    def 'plugin adds the UI task'() {
        given:
            plugin = new RestdocsextPlugin()

        when:
            plugin.configurePlugin(project)

        then:
            def uiTask = project.tasks.getByName(RestdocsextPlugin.RESTDOCSEXT)
            uiTask instanceof RestdocsextUiTask
    }

    def 'plugin adds conventions to the UI task'() {
        given:
            project.apply plugin: RestdocsextPlugin

        when:
            project.evaluate()
            def uiTask = project.tasks.getByName(RestdocsextPlugin.RESTDOCSEXT)
            def convention = uiTask.getRestdocsext()

        then:
            convention != null
    }

    def 'plugin adds custom conventions to the UI task'() {
        given:
            project.apply plugin: RestdocsextPlugin
            def extension = project.extensions.findByType(RestdocsextExtension)
            extension.uiOutputDir new File('output')
            extension.snippetsDir new File('snippets')
            extension.baseUri 'http://localhost:8080'
            def asciidoctor = new AsciidoctorConfig()
            asciidoctor.outputDir new File('output')
            asciidoctor.sourceDir new File('source')
            asciidoctor.attributes 'snippets': 'snippetsDir'
            extension.setAsciidoctor asciidoctor

        when:
            project.evaluate()
            def uiTask = project.tasks.getByName(RestdocsextPlugin.RESTDOCSEXT)
            RestdocsextExtension convention = uiTask.getRestdocsext()

        then:
            convention.baseUri == 'http://localhost:8080'
            convention.uiOutputDir.path == 'output'
            convention.snippetsDir.path == 'snippets'
            convention.asciidoctor.outputDir.path == 'output'
            convention.asciidoctor.sourceDir.path == 'source'
            convention.asciidoctor.attributes.containsKey('snippets')
    }
}
