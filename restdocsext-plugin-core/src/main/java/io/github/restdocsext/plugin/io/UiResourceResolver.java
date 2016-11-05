
package io.github.restdocsext.plugin.io;

import io.github.restdocsext.plugin.RestdocsextPluginException;

/**
 * Resolves the UI from some external resource
 *
 * @author PaulSamsotha
 */
public interface UiResourceResolver {

    /**
     * Resolves the UI from the {@code source} and and saves to the {@code destination}.
     *
     * @param source the source location of the UI
     * @param destination the destination the UI should be saved to
     * @throws RestdocsextPluginException 
     */
    void resolve(String source, String destination) throws RestdocsextPluginException;
}
