
package io.github.restdocsext.plugin.task;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.SortedSet;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import io.github.restdocsext.plugin.RestdocsextPluginContext;
import io.github.restdocsext.plugin.RestdocsextPluginException;
import io.github.restdocsext.plugin.document.DocumentModifier;
import io.github.restdocsext.plugin.document.DocumentModifiers;

/**
 * Task to modify the JSoup {@link Document}s.
 *
 * @author PaulSamsotha
 */
public class DocumentModifyingTask implements RestdocsextPluginTask {

    @Override
    public void handle(RestdocsextPluginContext context) throws RestdocsextPluginException {
        context.getLogger().info("Starting to modify Asciidoc document(s).");
        SortedSet<DocumentModifier> modifiers = DocumentModifiers.defaultModifiers();
        File asciidoctorOutputDir = context.getAsciidoctorOutputDir();
        if (!asciidoctorOutputDir.exists() || !asciidoctorOutputDir.isDirectory()) {
            throw new RestdocsextPluginException(asciidoctorOutputDir 
                    + " does not exist or is not a directory");
        }
        File[] files = asciidoctorOutputDir.listFiles();

        if (files.length == 0) {
            context.getLogger().warn("The directory " + asciidoctorOutputDir + " is empty");
            return;
        }
        for (File file : files) {
            processFile(file, modifiers, context);
        }
        context.getLogger().info("Finished modifying Asciidoc document(s)");
    }

    private void processFile(File file, Set<DocumentModifier> modifiers, RestdocsextPluginContext context) {
        try {
            Document document = Jsoup.parse(file, "utf-8");
            for (DocumentModifier modifier : modifiers) {
                modifier.modify(document);
            }
            File docFile = new File(context.getAssetsOutputDir(), file.getName());
            FileUtils.writeStringToFile(docFile, document.body().html(), "utf-8");
            // add doc page to list of pages. These pages will be put into the PlaygroundConfig
            // from the JSON generator task.
            context.addDocPage(file.getName());
        } catch (IOException ex) {
            context.getLogger().error(ex.getMessage());
            throw new RestdocsextPluginException("failed to modify asciidoctor generated documents.", ex);
        }
    }
}
