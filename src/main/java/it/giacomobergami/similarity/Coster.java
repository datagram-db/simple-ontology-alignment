package it.giacomobergami.similarity;

import it.giacomobergami.simpleschema.ConceptProperty;
import it.giacomobergami.simpleschema.NativeTypes;

public class Coster {

    private IEmbedding weightFunction;

    public Coster(IEmbedding weightFunction) {
        this.weightFunction = weightFunction;
    }

    public double getDoubleWeight(String src, String dst) {
        return  weightFunction.normalised_distance(src, dst);
    }

    public double getDoubleWeight(String src, NativeTypes srcType, String dst, NativeTypes dstType) {
        double semanticCost = getDoubleWeight(src, dst);
        double issueFactor = NativeTypes.isSubsetOf(srcType, dstType);
        return semanticCost * issueFactor;
    }

    public int getInt100Weight(ConceptProperty src, ConceptProperty dst) {
        double semanticCost = weightFunction.normalised_distance(src.getSemanticName(), dst.getSemanticName());
        double issueFactor = NativeTypes.isSubsetOf(src.getType(), dst.getType());
        return ((int)(semanticCost * issueFactor*100.0));
    }
}
