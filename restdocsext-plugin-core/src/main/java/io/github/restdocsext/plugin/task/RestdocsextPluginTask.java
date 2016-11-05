
package io.github.restdocsext.plugin.task;

import io.github.restdocsext.plugin.RestdocsextPluginContext;
import io.github.restdocsext.plugin.RestdocsextPluginException;

/**
 * Represents a task that should be handled by a plugin
 *
 * @author Paul Samsotha
 */
public interface RestdocsextPluginTask {
    
    /**
     * Handles a plugin task, given the plugin context
     * 
     * @param context the context of the plugin operations
     * @throws RestdocsextPluginException
     */
    void handle(RestdocsextPluginContext context) throws RestdocsextPluginException;
}
