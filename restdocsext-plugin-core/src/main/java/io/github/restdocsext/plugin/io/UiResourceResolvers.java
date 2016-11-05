
package io.github.restdocsext.plugin.io;

/**
 *
 * @author Paul Samsotha
 */
public abstract class UiResourceResolvers {
    
    public static UiResourceResolver github() {
        return new GitHubUiResourceResolver();
    }
}
