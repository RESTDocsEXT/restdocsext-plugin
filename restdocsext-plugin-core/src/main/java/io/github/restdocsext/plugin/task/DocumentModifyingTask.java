
package io.github.restdocsext.plugin.task;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.SortedSet;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import io.github.restdocsext.plugin.RestdocsextPluginContext;
import io.github.restdocsext.plugin.RestdocsextPluginException;
import io.github.restdocsext.plugin.document.DocumentModifier;
import io.github.restdocsext.plugin.document.DocumentModifiers;

/**
 * Task to modify the JSoup {@link Document}s.
 *
 * @author Paul Samsotha
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

        if (asciidoctorOutputDir.listFiles().length == 0) {
            context.getLogger().warn("The directory " + asciidoctorOutputDir + " is empty");
            return;
        }
        for (File file : FileUtils.listFiles(asciidoctorOutputDir, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE)) {
            processFile(file, modifiers, context);
        }
        context.getLogger().info("Finished modifying Asciidoc document(s)");
    }

    /*
     * Modified a document and saves it to the assets/docs directory.
     */
    private void processFile(File file, Set<DocumentModifier> modifiers, RestdocsextPluginContext context) {
        try {
            Document document = Jsoup.parse(file, "utf-8");
            for (DocumentModifier modifier : modifiers) {
                modifier.modify(document);
            }
            String pathWithoutBase = getPathWithoutBase(context.getAsciidoctorOutputDir(), file);
            File docFile = new File(context.getAssetsDocsDir(), pathWithoutBase);
            FileUtils.writeStringToFile(docFile, document.body().html(), "utf-8");
        } catch (IOException ex) {
            context.getLogger().error(ex.getMessage());
            throw new RestdocsextPluginException("failed to modify asciidoctor generated documents.", ex);
        }
    }

    /*
     * Takes the absolute path of a file and strips the base asciidoctor output path.
     */
    private static String getPathWithoutBase(File asciidoctorOutputDir, File fileToSave) {
        String basePath = asciidoctorOutputDir.getAbsolutePath();
        // take care of asciidoctor creating html5 directory
        basePath = basePath.endsWith("/") ? basePath + "html5" : basePath + "/html5";
        String fullPath = fileToSave.getAbsolutePath();
        String baseRemoved = fullPath.substring(basePath.length());
        return baseRemoved.startsWith("/") ? baseRemoved.substring(1) : baseRemoved;
    }
}
