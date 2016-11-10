
package io.github.restdocsext.plugin.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 *
 * @author PaulSamsotha
 */
class RestdocsextPlugin implements Plugin<Project> {
	
    static final String RESTDOCSEXT = 'restdocsext'

    void apply(Project project) {
        project.extensions.create(RESTDOCSEXT, RestdocsextExtension, project)

        addRestdocsextTask(project)
    }

    def addRestdocsextTask(Project project) {
        project.afterEvaluate {
            project.tasks.withType(RestdocsextUiTask).whenTaskAdded { task ->
                def extension = project.extensions.findByName(RESTDOCSEXT)
                task.conventionMapping.with {
                    restdocsext = { extension }
                }
            }
            project.task(RESTDOCSEXT, 
                type: RestdocsextUiTask, 
                group: 'RESTDocsEXT',
                description: 'Creates UI from Spring REST Docs snippets')
        }
    }
}

