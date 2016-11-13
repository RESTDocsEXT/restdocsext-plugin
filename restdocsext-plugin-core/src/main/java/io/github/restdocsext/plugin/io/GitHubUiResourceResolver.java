
package io.github.restdocsext.plugin.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;

import io.github.restdocsext.plugin.RestdocsextPluginException;

/**
 * Resolve the UI from GitHub.
 *
 * @author Paul Samsotha
 */
class GitHubUiResourceResolver implements UiResourceResolver {

    @Override
    public void resolve(String source, String destination) throws RestdocsextPluginException {
        final File tempFile = getFromSource(source);
        checkOrCreateOutputDir(destination);
        extractToDestination(tempFile, destination);
        FileUtils.deleteQuietly(tempFile);
    }

    private static void extractToDestination(File file, String destination) {
        try (InputStream inFile = new FileInputStream(file);
                ZipInputStream zipIn = new ZipInputStream(inFile)) {

            ZipEntry entry;
            while ((entry = zipIn.getNextEntry()) != null) {
                File newFile = new File(destination + File.separator + entry.getName());
                if (!entry.isDirectory()) {
                    new File(newFile.getParent()).mkdirs();
                    try (FileOutputStream fos = new FileOutputStream(newFile)) {
                        int len;
                        byte[] buffer = new byte[4096];
                        while ((len = zipIn.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                    }
                }
                zipIn.closeEntry();
            }
        } catch (IOException ex) {
            throw new RestdocsextPluginException("Could not extract zip file", ex);
        }
    }

    private static File getFromSource(String source) {
        try {
            File tempFile = File.createTempFile("restdocsext-ui", ".tmp");
            FileUtils.copyURLToFile(new URL(source), tempFile, 10_000, 10_000);
            return tempFile;
        } catch (MalformedURLException ex) {
            throw new RestdocsextPluginException("Not a valid URL: " + source, ex);
        } catch (IOException ex) {
            throw new RestdocsextPluginException("Problem downloading file", ex);
        }
    }

    private void checkOrCreateOutputDir(String destination) {
        File file = new File(destination);
        if (!file.exists()) {
            file.mkdir();
        } else if (!file.isDirectory()) {
            throw new IllegalArgumentException("<" + destination + "> must be a directory");
        }
    }
}
