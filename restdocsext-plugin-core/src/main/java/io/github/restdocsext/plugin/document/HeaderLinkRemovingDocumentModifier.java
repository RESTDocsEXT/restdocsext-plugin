
package io.github.restdocsext.plugin.document;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Removes links in all the headings.
 *
 * @author Paul Samsotha
 */
class HeaderLinkRemovingDocumentModifier implements DocumentModifier {

    static final String[] H_TAGS = {"h1", "h2", "h3", "h4", "h5", "h6"};
    
    @Override
    public Integer getOrder() {
        return ModificationOrders.REMOVE_HEADER_LINK;
    }

    @Override
    public void modify(Document document) {
        Element body = document.body();
        for (String headingTag: H_TAGS) {
            removeLink(body, headingTag);
        }
    }

    private static void removeLink(Element node, String headingTag) {
        Elements headings = node.getElementsByTag(headingTag);
        for (Element h : headings) {
            Elements aChildren = h.select("a[href]");
            if (!aChildren.isEmpty()) {
                Element a = aChildren.get(0);
                a.remove();
                h.text(a.text());
            }
        }
    }
}
