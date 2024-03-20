package it.giacomobergami.simpleschema.alignments;

import org.apache.commons.lang3.tuple.Triple;

public class AlignmentToken extends Triple<ConceptPath, Double, ConceptPath> {

    private final ConceptPath src;
    private final ConceptPath dst;
    private final double score;

    public AlignmentToken(ConceptPath src, ConceptPath dst, double score) {
        this.src = src;
        this.dst = dst;
        this.score = score;
    }

    public ConceptPath getSrc() {
        return src;
    }

    public ConceptPath getDst() {
        return dst;
    }

    public double getScore() {
        return score;
    }

    @Override
    public ConceptPath getLeft() {
        return src;
    }

    @Override
    public Double getMiddle() {
        return score;
    }

    @Override
    public ConceptPath getRight() {
        return dst;
    }
}
