package it.giacomobergami.simpleschema.alignments;

import com.google.common.collect.HashMultimap;
import it.giacomobergami.simpleschema.Ontology;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Alignment {
    public final Ontology src;
    public final Ontology dst;
    private final HashMultimap<AlignmentToken, Integer> concept_correspondences;
    private final List<AlignmentToken> field_correspondences;

    public Alignment(Ontology src, Ontology dst) {
        this.src = src;
        this.dst = dst;
        concept_correspondences = HashMultimap.create();
        field_correspondences = new ArrayList<>();
    }

    public AlignmentToken generateConceptCorrespondence(ConceptPath conceptPath, ConceptPath conceptPath1, double cost) {
        return new AlignmentToken(conceptPath, conceptPath1, cost);
    }

    public void addFieldCorrespondence(ConceptPath conceptPath, ConceptPath conceptPath1, double cost, AlignmentToken corr) {
        var tok = new AlignmentToken(conceptPath, conceptPath1, cost);
        field_correspondences.add(tok);
        concept_correspondences.put(corr, field_correspondences.size()-1);
    }

    public Set<AlignmentToken> getConceptCorrespondences() {
        return concept_correspondences.keySet();
    }

    public Set<AlignmentToken> getFieldCorrespondences(AlignmentToken conceptCorrespondence) {
        return concept_correspondences.get(conceptCorrespondence).stream().map(field_correspondences::get).collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (var cp : getConceptCorrespondences()) {
            sb.append("Alignment: ").append(cp.getSrc()).append(" --> ").append(cp.getDst()).append(System.lineSeparator());
            sb.append("* Cost: ").append(cp.getScore()).append(System.lineSeparator());
            sb.append("* Field Correspondence: ").append(System.lineSeparator());
            for (var fields : getFieldCorrespondences(cp)) {
                sb.append(fields.getSrc()).append("-->").append(fields.getDst()).append(" with cost: ").append(fields.getScore()).append(System.lineSeparator());
            }
            sb.append(System.lineSeparator());
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}
