
package io.github.restdocsext.plugin.io;

import java.io.File;
import java.net.URL;
import java.util.UUID;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 *
 * @author Paul Samsotha
 */
public class GitHubUiResourceResolverTest {
    
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
    
    @Test
    public void should_resolve_resource_and_copy_to_destination() throws Exception {
        URL zipUrl = Thread.currentThread().getContextClassLoader().getResource("testing.zip");
        String source = new File(zipUrl.toURI()).toURI().toASCIIString();
        File destDir = this.tempFolder.newFolder(UUID.randomUUID().toString());
        String dest = destDir.getAbsolutePath();
        
        new GitHubUiResourceResolver().resolve(source, dest);
        
        assertThat(new File(destDir, "testing.txt").exists(), is(true));
        assertThat(new File(destDir, "folder/inside.txt").exists(), is(true));
    }
}
