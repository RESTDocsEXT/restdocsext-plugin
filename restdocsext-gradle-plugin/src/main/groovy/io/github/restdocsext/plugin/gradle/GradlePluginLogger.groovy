
package io.github.restdocsext.plugin.gradle

import org.gradle.api.logging.Logger
import org.gradle.api.logging.LogLevel

import io.github.restdocsext.plugin.PluginLogger

/**
 * {@link PluginLogger} implementation for the Gradle platform.
 *
 * @author Paul Samsotha
 */
class GradlePluginLogger implements PluginLogger {
    
    final Logger logger
    
    GradlePluginLogger(Logger logger) {
        this.logger = logger
    }
	
    @Override
    void info(String content) {
        logger.info(content)
    }
    
    @Override
    void warn(String content) {
        logger.log(LogLevel.WARN, content)
    }
    
    @Override
    void error(String content) {
        logger.log(LogLevel.ERROR, content)
    }
    
    @Override
    void debug(String content) {
        logger.debug(content)
    }
    
    @Override
    void quiet(String content) {
        logger.quiet(content)
    }
}


