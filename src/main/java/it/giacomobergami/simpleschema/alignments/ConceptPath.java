package it.giacomobergami.simpleschema.alignments;

import java.util.Objects;

public class ConceptPath {
    private String ontology;
    private String concept;
    private String field;

    public ConceptPath(String ontology, String concept, String field) {
        this.ontology = ontology;
        this.concept = concept;
        this.field = field;
    }

    public ConceptPath(String ontology, String concept) {
        this(ontology, concept, null);
    }

    public ConceptPath(String ontology) {
        this(ontology, null, null);
    }

    @Override
    public String toString() {
        if ((concept == null) && (field == null))
            return ontology;
        else if (field == null)
            return ontology+"."+concept;
        else
            return ontology+"."+concept+"."+field;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConceptPath that = (ConceptPath) o;
        return Objects.equals(ontology, that.ontology) && Objects.equals(concept, that.concept) && Objects.equals(field, that.field);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ontology, concept, field);
    }
}
