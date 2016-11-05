
package io.github.restdocsext.plugin.document;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

/**
 * Adds {@code table table-bordered tabled-striped} Bootstrap classes. For this reason,
 * this modifier should occur after the class removing modification.
 *
 * TODO: Maybe move the class adding to a different modifier.
 * TODO: No longer using ADMIN-LTE. Remove the box wrapper. Shouldn't be a problem right now.
 *
 * @author Paul Samsotha
 */
class TableWrappingDocumentModifier implements DocumentModifier {
    
    /* package for testing */
    static final String CLASSES_TO_ADD = "table table-bordered table-striped";
    
    @Override
    public Integer getOrder() {
        return ModificationOrders.WRAP_TABLE;
    }

    @Override
    public void modify(Document document) {
        Elements elements = document.getElementsByTag("table");
        for (Element element : elements) {
            // Element tableClone = element.clone();
            element.attr("class", CLASSES_TO_ADD);
            // Node previousSibling = element.previousSibling();
            // element.remove();
            // Element boxDiv = createBoxDiv(tableClone);
            // previousSibling.after(boxDiv);
        }
    }

    private static Element createBoxDiv(Element table) {
        Element boxDiv = new Element(Tag.valueOf("div"), "")
                .addClass("box");
        Element boxBodyDiv = new Element(Tag.valueOf("div"), "")
                .addClass("box-body");
        boxDiv.appendChild(boxBodyDiv);
        boxBodyDiv.appendChild(table);
        return boxDiv;
    }
}


