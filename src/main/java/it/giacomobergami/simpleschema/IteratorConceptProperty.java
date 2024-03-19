package it.giacomobergami.simpleschema;

import java.util.*;

public class IteratorConceptProperty implements IConcept {
    private final IConcept PARENT;
    final Map.Entry<String, IConcept> curr;
    public IteratorConceptProperty(IConcept PARENT, Map.Entry<String, IConcept> curr) {
        this.PARENT = PARENT;
        this.curr = curr;
    }

    @Override
    public int hashCode() {
        return curr.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IteratorConceptProperty that = (IteratorConceptProperty) o;
        return Objects.equals(curr, that.curr);
    }

    @Override
    public IConcept getAncestor() {
        return PARENT;
    }

    @Override
    public String getSemanticName() {
        return curr.getKey();
    }

    @Override
    public NativeTypes getType() {
        return curr.getValue().isBasic() ? curr.getValue().getType() : NativeTypes.CONCEPT;
    }

    @Override
    public boolean isBasic() {
        return curr.getValue().isBasic();
    }

    @Override
    public Optional<Concept> asConcept() {
        return curr.getValue().asConcept();
    }

    @Override
    public int countConceptNodes(Set<String> visited) {
        return curr.getValue().isBasic() ? 0 : curr.getValue().countConceptNodes(visited);
    }

    @Override
    public boolean isList() {
        return curr.getValue().isList();
    }

    @Override
    public Set<Map.Entry<String, IConcept>> components() {
        return curr.getValue().components();
    }

    @Override
    public Collection<? extends IConcept> components2() {
        return curr.getValue().components2();
    }
}
