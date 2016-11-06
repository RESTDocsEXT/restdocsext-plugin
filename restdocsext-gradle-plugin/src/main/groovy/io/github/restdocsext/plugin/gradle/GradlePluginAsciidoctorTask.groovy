
package io.github.restdocsext.plugin.gradle

import org.asciidoctor.gradle.AsciidoctorTask
import org.gradle.api.Project

import io.github.restdocsext.plugin.RestdocsextPluginContext
import io.github.restdocsext.plugin.task.AsciidoctorPluginTask

/**
 * Wrapper around the Asciidoctor plugin task.
 *
 * @author Paul Samsotha
 */
class GradlePluginAsciidoctorTask implements AsciidoctorPluginTask {
    
    static final String ASCIIDOCTOR = 'asciidoctor'
    
    final Project project
    
    GradlePluginAsciidoctorTask(Project project) {
        this.project = project
    }
	
    @Override
    void handle(RestdocsextPluginContext context) {
        context.logger.info 'Starting to run Asciidoctor Gradle plugin.'
        project.configurations.maybeCreate(ASCIIDOCTOR)
        project.task(ASCIIDOCTOR, type: AsciidoctorTask) {
            
            sourceDir context.asciidoctorSourceDir
            outputDir context.asciidoctorOutputDir
            attributes context.asciidoctorAttrs
            
        }.execute()
        context.logger.info 'Finished running Asciidoctor Gradle plugin.'
    }
}

