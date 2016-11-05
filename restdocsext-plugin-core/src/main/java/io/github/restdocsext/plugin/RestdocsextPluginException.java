
package io.github.restdocsext.plugin;

/**
 * Exception to be thrown during execution of a plugin operations.
 *
 * @author Paul Samsotha
 */
public class RestdocsextPluginException extends RuntimeException {
    
    public RestdocsextPluginException(String message) {
        super(message);
    }
    
    public RestdocsextPluginException(String message, Throwable ex) {
        super(message, ex);
    }
}
