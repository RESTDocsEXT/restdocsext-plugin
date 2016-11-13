
package io.github.restdocsext.plugin.task;

import io.github.restdocsext.plugin.RestdocsextPluginContext;
import io.github.restdocsext.plugin.RestdocsextPluginException;
import io.github.restdocsext.plugin.io.UiResourceResolvers;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.apache.commons.io.FileUtils.deleteQuietly;

/**
 *
 * @author PaulS amsotha
 */
public class ResolveRestdocsextUiTask implements RestdocsextPluginTask {

    private static final String DOWNLOAD_URL
            = "https://raw.githubusercontent.com/RESTDocsEXT/restdocsext-ui/master/dist/restdocsext-ui.zip";

    @Override
    public void handle(RestdocsextPluginContext context) throws RestdocsextPluginException {
        context.getLogger().info("Starting task to extract the RESTDocsEXT UI");
        try {
            File uiOutputDir = context.getUiOutputDir();
            if (!uiOutputDir.exists()) {
                uiOutputDir.mkdirs();
            } else if (uiOutputDir.exists() && !uiOutputDir.isDirectory()) {
                throw new RestdocsextPluginException(uiOutputDir + " is not a directory");
            }
            UiResourceResolvers.github().resolve(context.getUiSourceUrl(),
                    context.getUiOutputDir().getAbsolutePath());
            context.getLogger().info("Finished extracting RESTDocsEXT UI to " + uiOutputDir);
        } catch (Exception ex) {
            context.getLogger().error(ex.getMessage());
            throw new RestdocsextPluginException("Failed to extract restdocsext-ui.zip", ex);
        }
    }
}
