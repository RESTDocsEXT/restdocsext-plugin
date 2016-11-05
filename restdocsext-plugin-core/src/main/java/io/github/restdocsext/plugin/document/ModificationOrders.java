
package io.github.restdocsext.plugin.document;

/**
 * The orders in which {@link DocumentModifier}s should be executed. The lowest will
 * be executed first.
 * 
 * @author Paul Samsotha
 */
public class ModificationOrders {

    private ModificationOrders() {
 
    }

    /**
     * Order for removing all the class attributes.
     */
    public static final int REMOVE_CLASS = 1000;

    /**
     * Order for removing all the links in the headings
     */
    public static final int REMOVE_HEADER_LINK = 2000;

    /**
     * Order for modifying the table of contents links
     */
    public static final int TOC_LINK = 3000;

    /**
     * Order for wrapping the table elements.
     */
    public static final int WRAP_TABLE = 4000;
}
