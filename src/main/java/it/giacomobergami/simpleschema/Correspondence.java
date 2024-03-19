package it.giacomobergami.simpleschema;

import it.giacomobergami.similarity.Coster;
import it.giacomobergami.similarity.IEmbedding;
import uk.ncl.giacomobergami.utils.structures.ImmutablePair;

import java.util.*;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

public class Correspondence extends ImmutablePair<Concept, Concept> {

    private double overallCost;

    public Correspondence(Concept key, Concept value) {
        super(key, value);
        this.overallCost = 0.0;
    }

    public double getOverallCost() {
        return overallCost;
    }

    public void setOverallCost(double overallCost) {
        this.overallCost = overallCost;
    }

    public HashMap<String, HashMap<String, Double>> map = new HashMap<>();

    public void putCorrespondence(String src, String dst, double cost, double correction) {
        if (!map.containsKey(src))
            map.put(src, new HashMap<>());
        map.get(src).put(dst, cost/correction);
    }

    public double computeMapCost() {
        return map.values().stream().flatMap(x -> x.values().stream()).mapToDouble(y -> y).sum();
    }

    public Correspondence updateCorrespondenceOverFields(Coster conceptSimilarity) {
        double concept_name_distance = conceptSimilarity.getDoubleWeight(getLeft().getKey(), getRight().getKey());
        Set<String> allLeftFields = new HashSet<>(getLeft().mapping.keySet()),
                    allRightFields = new HashSet<>(getRight().mapping.keySet());
        allLeftFields.removeAll(map.keySet());
        map.values().stream().flatMap(x->x.keySet().stream()).forEach(allRightFields::remove);
        var updatedCorrespondence = new Correspondence(getLeft(), getRight());
        updatedCorrespondence.map = new HashMap<>(map);
        if ((!allLeftFields.isEmpty()) && (!allRightFields.isEmpty())) {
            Map<Boolean, List<String>> leftConceptVsAnyField = allLeftFields.stream().collect(Collectors.partitioningBy(x -> getLeft().mapping.get(x) instanceof Concept));
            Map<Boolean, List<String>> rightConceptVsAnyField = allRightFields.stream().collect(Collectors.partitioningBy(x -> getLeft().mapping.get(x) instanceof Concept));
            for (String x : leftConceptVsAnyField.getOrDefault(false, Collections.emptyList())) {
                var xType = getLeft().mapping.get(x).getType();
                rightConceptVsAnyField.getOrDefault(false, Collections.emptyList()).stream()
                        .map(o1 -> {
                            var o1Type = Correspondence.this.getRight().mapping.get(o1).getType();
                            return new ImmutablePair<>(conceptSimilarity.getDoubleWeight(x, xType, o1, o1Type), o1);
                        })
                        .min(Comparator.comparingDouble(ImmutablePair::getLeft)).ifPresent(y -> updatedCorrespondence.putCorrespondence(x, y.getRight(), y.getLeft(), 1.0));
            }
        }
        double alignmentCost = updatedCorrespondence.computeMapCost();
        double finalFieldCost = alignmentCost + concept_name_distance;
        updatedCorrespondence.setOverallCost(finalFieldCost);
        return updatedCorrespondence;
    }

    @Override
    public String toString() {
        return map.toString();
    }

    public void selfUpdateWithConceptAlignment(HashMap<ImmutablePair<String, String>, Correspondence> corr2) {
        Set<String> allLeftFields = new HashSet<>(getLeft().mapping.keySet()),
                allRightFields = new HashSet<>(getRight().mapping.keySet());
        allLeftFields.removeAll(map.keySet());
        map.values().stream().flatMap(x->x.keySet().stream()).forEach(allRightFields::remove);
        if ((!allLeftFields.isEmpty()) && (!allRightFields.isEmpty())) {
            Map<Boolean, List<String>> leftConceptVsAnyField = allLeftFields.stream().collect(Collectors.partitioningBy(x -> getLeft().mapping.get(x) instanceof Concept));
            Map<Boolean, List<String>> rightConceptVsAnyField = allRightFields.stream().collect(Collectors.partitioningBy(x -> getLeft().mapping.get(x) instanceof Concept));
            for (String x : leftConceptVsAnyField.getOrDefault(true, Collections.emptyList())) {
                var varX = ((Concept)getLeft().mapping.get(x)).getSemanticName();
                double minVal = Double.MAX_VALUE;
                String yCand = null;
                for (var y :rightConceptVsAnyField.getOrDefault(true, Collections.emptyList())) {
                    var varY = ((Concept)getRight().mapping.get(y)).getSemanticName();
                    var cpI = new ImmutablePair<>(varX, varY);
                    if (corr2.containsKey(cpI)) {
                        var z = corr2.get(cpI);
                        var cp = new ImmutablePair<>(y, z);
                        if (z.getOverallCost()<minVal) {
                            minVal = z.getOverallCost();
                            yCand = y;
                        }
                    }
                }
                if (yCand != null) {
                    putCorrespondence(x, yCand, minVal, 1.0);
                }
            }
        }
    }

    public void finalizeAlignmentCost(Coster weightFunction) {
        double concept_name_distance = weightFunction.getDoubleWeight(getLeft().getKey(), getRight().getKey());

        double count = map.values().stream().mapToDouble(x->x.values().size()).sum();
        double overallAlignments = map.values().stream().flatMap(x->x.values().stream()).mapToDouble(x->x).sum();

        Set<String> allLeftFields = new HashSet<>(getLeft().mapping.keySet()),
                allRightFields = new HashSet<>(getRight().mapping.keySet());
        allLeftFields.removeAll(map.keySet());
        map.values().stream().flatMap(x->x.keySet().stream()).forEach(allRightFields::remove);

        overallCost = concept_name_distance + (overallAlignments/count) + (allLeftFields.size()+allRightFields.size());
    }

    public void recomputeFieldCostByNormalization(Coster weightFunction) {
        Set<String> allLeftFields = new HashSet<>(getLeft().mapping.keySet()),
                allRightFields = new HashSet<>(getRight().mapping.keySet());
        allLeftFields.retainAll(map.keySet());
        allRightFields.retainAll(map.values().stream().flatMap(x->x.keySet().stream()).collect(Collectors.toList()));
        for (var x : allLeftFields) {
            if (map.containsKey(x)) {
                var val = map.get(x);
                for (var y : allRightFields) {
                    if (val.containsKey(y)) {
                        double newValue = weightFunction.getDoubleWeight(x, getLeft().mapping.get(x).getType(), y, getRight().mapping.get(y).getType());
                        val.put(y, newValue);
                    }
                }
            }
        }
    }
}
