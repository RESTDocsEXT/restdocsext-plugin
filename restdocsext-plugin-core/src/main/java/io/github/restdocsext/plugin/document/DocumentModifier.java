
package io.github.restdocsext.plugin.document;

import org.jsoup.nodes.Document;

/**
 * Contract to modify a JSoup {@link Document}. Each modifier will have an order
 * in which it should be called.
 *
 * @author Paul Samsotha
 */
public interface DocumentModifier {

    /**
     * Modifies the JSoup document
     * 
     * @param document the JSoup document
     */
    void modify(Document document);

    /**
     * Gets the order in which this modifier will be called.
     * 
     * @return the order position of this modifier.
     */
    Integer getOrder();
}
