package it.giacomobergami.simpleschema;

import uk.ncl.giacomobergami.utils.structures.ImmutablePair;

import java.util.*;

public class ConceptProperty extends ImmutablePair<String, NativeTypes> implements IConcept {

    public ConceptProperty(IConcept  ancestor, String key, NativeTypes value) {
        super(key, value);
        this.ancestor = ancestor;
    }

    public IConcept ancestor;

    @Override
    public IConcept getAncestor() {
        return ancestor;
    }

    @Override
    public String getSemanticName() {
        return getKey();
    }

    @Override
    public NativeTypes getType() {
        return getValue();
    }

    @Override
    public boolean isBasic() {
        return true;
    }

    @Override
    public Optional<Concept> asConcept() {
        return Optional.empty();
    }


    @Override
    public int countConceptNodes(Set<String> visited) {
        return 1;
    }

    @Override
    public boolean isList() {
        return getValue().asList() == getValue();
    }

    @Override
    public Set<Map.Entry<String, IConcept>> components() {
        return Collections.emptySet();
    }

    @Override
    public Collection<? extends IConcept> components2() {
        return Collections.emptyList();
    }


}
