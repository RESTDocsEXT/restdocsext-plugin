package io.github.restdocsext.plugin.task;

import io.github.restdocsext.plugin.JulPluginLogger;
import io.github.restdocsext.plugin.RestdocsextPluginContext;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;


/**
 * Created by PaulSamsotha on 11/13/2016.
 */
public class DocumentModifyingTaskTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    private RestdocsextPluginContext context;

    File assetsDocsDir;

    @Before
    public void setUp() throws Exception {
        File uiOutputDir = tempFolder.newFolder("DocumentModifyingTaskTest.uiOutputDir");
        context = RestdocsextPluginContext.builder()
                .baseUri("http://localhost:8080")
                .snippetsDir(new File("snippets"))
                .uiOutputDir(uiOutputDir)
                .asciidoctorSourceDir(new File("asciiSource"))
                .logger(new JulPluginLogger())
                .asciidoctorOutputDir(new File(Thread.currentThread().getContextClassLoader()
                        .getResource("asciidoctorOutput").toURI()))
                .build();
        assetsDocsDir = context.getAssetsDocsDir();
    }

    @Test
    public void should_copy_files_from_outputDir_recursively() {
        new DocumentModifyingTask().handle(context);

        assertThat(new File(assetsDocsDir, "test1.html").exists(), is(true));
        assertThat(new File(assetsDocsDir, "sub/sub.html").exists(), is(true));
        assertThat(new File(assetsDocsDir, "sub1/sub1.html").exists(), is(true));
        assertThat(new File(assetsDocsDir, "sub1/sub1/sub1-sub1.html").exists(), is(true));
    }
}
