
package io.github.restdocsext.plugin;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import io.github.restdocsext.plugin.task.AsciidoctorPluginTask;
import io.github.restdocsext.plugin.task.DocumentModifyingTask;
import io.github.restdocsext.plugin.task.GenerateRestdocsextConfigTask;
import io.github.restdocsext.plugin.task.ResolveRestdocsextUiTask;
import io.github.restdocsext.plugin.task.RestdocsextPluginTask;

/**
 * The main processor for handling the plugin tasks.
 *
 * @author Paul Samsotha
 */
public class RestdocsextPluginProcessor {
    
    /**
     * Process all the plugin tasks.
     */
    public void process(RestdocsextPluginContext context, AsciidoctorPluginTask asciidoctorTask) {
        Objects.requireNonNull(context, "Playground context must not be null");
        Objects.requireNonNull(asciidoctorTask, "Asciidoctor task must not be null");
        List<RestdocsextPluginTask> tasks = Arrays.asList(
                asciidoctorTask,
                new ResolveRestdocsextUiTask(),
                new DocumentModifyingTask(), 
                new GenerateRestdocsextConfigTask());
        for (RestdocsextPluginTask task: tasks) {
            task.handle(context);
        }
    }
}
