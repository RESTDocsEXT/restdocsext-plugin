
package io.github.restdocsext.plugin.gradle

import org.asciidoctor.gradle.AsciidoctorTask
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Nested
import org.gradle.api.tasks.TaskAction

import io.github.restdocsext.plugin.RestdocsextPluginContext
import io.github.restdocsext.plugin.RestdocsextPluginProcessor
import io.github.restdocsext.plugin.task.AsciidoctorPluginTask

/**
 *
 * @author PaulSamsotha
 */
class RestdocsextUiTask extends DefaultTask {
	
    static final String ASCIIDOCTOR = 'asciidoctor'

    @Nested
    RestdocsextExtension restdocsext

    @TaskAction
    void process() {
        def playground = getPlayground()
        def asciidoctor = restdocsext.getAsciidoctor()
        def pluginContext = RestdocsextPluginContext.builder()
                .logger(new GradlePluginLogger(project.logger))
                .baseUri(restdocsext.getBaseUri())
                .uiOutputDir(restdocsext.getUiOutputDir())
                .sippetsDir(restdocsext.getUiSnippetsDir())
                .asciidoctorSourcesDir(asciidoctor.getSourcesDir())
                .asciidoctorOutputDir(asciidoctor.getOutputDir())
                .asciidoctorAttrs(asciidoctor.getAttributes())
                .build()
        def asciidoctorTask = new GradlePluginAsciidoctorTask(project)        
        
        new RestdocsextPluginProcessor(pluginContext, asciidoctorTask).process()
    }
}
