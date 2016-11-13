package io.github.restdocsext.plugin.task;

import io.github.restdocsext.plugin.JulPluginLogger;
import io.github.restdocsext.plugin.RestdocsextPluginContext;
import io.github.restdocsext.plugin.RestdocsextPluginException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by PaulSamsotha on 11/13/2016.
 */
public class ResolveRestdocsextUiTaskTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    private File uiOutputDir;
    private ResolveRestdocsextUiTask task;
    private RestdocsextPluginContext.Builder builder;

    @Before
    public void setUp() throws Exception {
        uiOutputDir = tempFolder.newFolder("ResolveRestdocsextUiTaskTest");
        task = new ResolveRestdocsextUiTask();
        builder = RestdocsextPluginContext.builder()
                .baseUri("http://localhost:8080")
                .snippetsDir(new File("snippets"))
                .asciidoctorOutputDir(new File("asciiOutput"))
                .asciidoctorSourceDir(new File("asciiSource"))
                .logger(new JulPluginLogger())
                .uiSourceUrl(Thread.currentThread().getContextClassLoader()
                        .getResource("testing.zip").toURI().toASCIIString());
    }

    @Test
    public void should_download_and_extract_zip_from_url() {
        RestdocsextPluginContext context = builder
                .uiOutputDir(uiOutputDir)
                .build();

        task.handle(context);
        assertThat(new File(uiOutputDir, "testing.txt").exists(), is(true));
        assertThat(new File(uiOutputDir, "folder/inside.txt").exists(), is(true));
    }

    @Test(expected = RestdocsextPluginException.class)
    public void should_throw_exception_when_output_location_is_file() throws Exception {
        File temp = tempFolder.newFile("badfile.txt");
        RestdocsextPluginContext context = builder
                .uiOutputDir(temp)
                .build();

        task.handle(context);
    }
}
