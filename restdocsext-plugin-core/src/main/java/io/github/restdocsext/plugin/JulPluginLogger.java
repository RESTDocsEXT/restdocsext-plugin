
package io.github.restdocsext.plugin;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Java Util Logging plugin logger implementation. This class is not recommended for use. 
 * It is only here as a fallback. Maven and Gradle plugin infrastructures have their
 * own logging mechanism, and those loggers should be made available through a
 * {@link PluginLogger} implementation.
 * 
 * @author Paul Samsotha
 */
public class JulPluginLogger implements PluginLogger {
    
    private static final Logger LOGGER = Logger.getLogger(JulPluginLogger.class.getName());

    @Override
    public void info(String content) {
        LOGGER.log(Level.INFO, content);
    }

    @Override
    public void warn(String content) {
        LOGGER.log(Level.WARNING, content);
    }

    @Override
    public void error(String content) {
        LOGGER.log(Level.SEVERE, content);
    }

    @Override
    public void debug(String content) {
        LOGGER.log(Level.FINER, content);
    }

    @Override
    public void quiet(String content) {
        LOGGER.log(Level.FINEST, content);
    }  
}
