
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
    
    private final RestdocsextPluginContext context;
    private final AsciidoctorPluginTask asciidoctorTask;
    
    /**
     * @param context the plugin context
     * @param task the Asciidoctor plugin task
     */
    public RestdocsextPluginProcessor(RestdocsextPluginContext context, AsciidoctorPluginTask task) {
        Objects.requireNonNull(context, "Playground context must not be null");
        Objects.requireNonNull(task, "Asciidoctor task must not be null");
        this.context = context;
        this.asciidoctorTask = task;
    }
    
    /**
     * Process all the plugin tasks.
     */
    public void process() {
        List<RestdocsextPluginTask> tasks = Arrays.asList(
                this.asciidoctorTask,
                new ResolveRestdocsextUiTask(),
                new DocumentModifyingTask(), 
                new GenerateRestdocsextConfigTask());
        for (RestdocsextPluginTask task: tasks) {
            task.handle(this.context);
        }
    }
}
