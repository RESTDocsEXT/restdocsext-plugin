
package io.github.restdocsext.plugin;

/**
 * Logger wrapper contract for plugin. Each plugin implementation should implement 
 * this interface and provide the implementation to the {@link REstdocsextPluginContext}
 *
 * @author Paul Samsotha
 */
public interface PluginLogger {
    
    void info(String content);
    
    void warn(String content);
    
    void error(String content);
    
    void debug(String content);
    
    void quiet(String content);
}
