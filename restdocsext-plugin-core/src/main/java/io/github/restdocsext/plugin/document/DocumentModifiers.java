
package io.github.restdocsext.plugin.document;

import java.util.Arrays;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author Paul Samsotha
 */
public abstract class DocumentModifiers {
    
    private DocumentModifiers() {
        
    }
    
    public static DocumentModifier classRemover() {
        return new ClassRemovingDocumentModifier();
    }
    
    public static DocumentModifier headerLinkRemover() {
        return new HeaderLinkRemovingDocumentModifier();
    }
    
    public static DocumentModifier tableWrapper() {
        return new TableWrappingDocumentModifier();      
    }

    public static SortedSet<DocumentModifier> defaultModifiers() {
        TreeSet<DocumentModifier> set = new TreeSet<>(new DocumentModifierComparator());
        set.addAll(Arrays.asList(classRemover(), headerLinkRemover(), tableWrapper()));
        return set;
    }
    
    private static final class DocumentModifierComparator implements Comparator<DocumentModifier> {

        @Override
        public int compare(DocumentModifier o1, DocumentModifier o2) {
            return o1.getOrder().compareTo(o2.getOrder());
        }   
    }
}
