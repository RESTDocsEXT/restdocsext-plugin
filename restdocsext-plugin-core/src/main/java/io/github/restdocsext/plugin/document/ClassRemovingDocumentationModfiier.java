
package io.github.restdocsext.plugin.document;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Gets rid of all the classes produced by the Asciidocor backend.
 * They are not needed for the UI application, and may conflict with the current CSS.
 *
 * @author Paul Samsotha
 */
class ClassRemovingDocumentModifier implements DocumentModifier {
    
    @Override
    public Integer getOrder() {
        return ModificationOrders.REMOVE_CLASS;
    }

    @Override
    public void modify(Document document) {
        removeClasses(document);
    }
    
    private static void removeClasses(Element element) {
        element.removeAttr("class");
        Elements children = element.children();
        if (children.isEmpty()) {
            return;
        }
        for (Element child: children) {
            removeClasses(child);
        }
    }  
}
