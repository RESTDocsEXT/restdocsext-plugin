package io.github.restdocsext.plugin.gradle

import io.github.restdocsext.plugin.RestdocsextPluginContext
import io.github.restdocsext.plugin.RestdocsextPluginProcessor
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import spock.lang.Specification

import static org.mockito.Mockito.any
import static org.mockito.Mockito.verify

/**
 * Created by PaulSamsotha on 11/12/2016.
 */
class RestdocsextUiTaskTest extends Specification {

    @Mock
    RestdocsextPluginProcessor processor

    @Captor
    ArgumentCaptor<RestdocsextPluginContext> contextCaptor

    Project project

    def setup() {
        MockitoAnnotations.initMocks(this)
        project = ProjectBuilder.builder().build()
    }

    def 'UI task converts extension properties correctly'() {
        given:
            def extension = new RestdocsextExtension()
            extension.baseUri 'http://localhost:8080'
            extension.snippetsDir new File('snippets')
            extension.uiOutputDir new File('uiOutput')
            extension.generalPages = ['introduction']
        def asciidoctor = new AsciidoctorConfig()
            asciidoctor.outputDir new File('output')
            asciidoctor.sourceDir new File('source')
            asciidoctor.attributes 'snippets': 'snippets.dir'
            extension.setAsciidoctor asciidoctor

        when:
            def uiTask = project.tasks.create('restdocsext', RestdocsextUiTask)
            uiTask.setRestdocsext(extension)
            uiTask.setProcessor(processor)
            uiTask.process()

        then:
            verify(processor).process(contextCaptor.capture(), any(GradlePluginAsciidoctorTask))
            def context = contextCaptor.value
            context.baseUri == 'http://localhost:8080'
            context.asciidoctorAttrs.containsKey('snippets')
            context.asciidoctorAttrs.containsValue('snippets.dir')
            context.uiOutputDir.path == 'uiOutput'
            context.snippetsDir.path == 'snippets'
            context.generalPages.contains('introduction')
            context.asciidoctorOutputDir.path == 'output'
            context.asciidoctorSourceDir.path == 'source'
    }
}
