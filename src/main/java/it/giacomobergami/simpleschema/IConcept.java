package it.giacomobergami.simpleschema;

import javax.swing.text.html.Option;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface IConcept {

    IConcept getAncestor();
    String getSemanticName();
    NativeTypes getType();
    boolean isBasic();

    Optional<Concept> asConcept();

    int countConceptNodes(Set<String> visited);

    boolean isList();

    Set<Map.Entry<String, IConcept>> components();
    Collection<? extends IConcept> components2();
}
