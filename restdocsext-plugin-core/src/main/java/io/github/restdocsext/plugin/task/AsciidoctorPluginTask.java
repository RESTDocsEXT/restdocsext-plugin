

package io.github.restdocsext.plugin.task;

/**
 * Interface for plugin implementations to implement. Each implementation will have
 * its own way of processing Asciidoctor, so we will need to call this specific
 * implementation during core processing.
 *
 * @author Paul Samsotha
 */
public interface AsciidoctorPluginTask extends RestdocsextPluginTask {

}
