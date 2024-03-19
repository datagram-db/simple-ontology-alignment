package it.giacomobergami.simpleschema;

import com.google.common.collect.Lists;
import it.giacomobergami.linalg.LabelledMatrix;
import it.giacomobergami.similarity.Coster;
import it.giacomobergami.similarity.IEmbedding;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.AttributeType;
import org.jgrapht.nio.dot.DOTExporter;
import org.jgrapht.traverse.TopologicalOrderIterator;
import uk.ncl.giacomobergami.traffic_orchestrator.solver.MinCostMaxFlow;
import uk.ncl.giacomobergami.utils.structures.ImmutablePair;

import java.io.File;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class OntologyAlignment {

    private final Ontology srcOntology;
    private final Ontology dstOntology;
    private final Coster weightFunction;

    enum CasesRedundant {
        LeftRedundant,
        RightRedundant,
        None
    }

    public static class CostWeightEdge {
        public int src;
        public int dst;
        public int capacity;
        public int cost;

//        public CostWeightEdge() {
//            src = -1;
//            dst = -1;
//            capacity = MinCostMaxFlow.INF;
//            cost = 0;
//        }

//        public static int binlog( int bits ) // returns 0 for bits=0
//        {
//            int log = 0;
//            if( ( bits & 0xffff0000 ) != 0 ) { bits >>>= 16; log = 16; }
//            if( bits >= 256 ) { bits >>>= 8; log += 8; }
//            if( bits >= 16  ) { bits >>>= 4; log += 4; }
//            if( bits >= 4   ) { bits >>>= 2; log += 2; }
//            return log + ( bits >>> 1 );
//        }

        public CostWeightEdge(int src, int dst, int capacity, int cost) {
            this.src = src;
            this.dst = dst;
            this.capacity = capacity;
            this.cost = cost;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CostWeightEdge that = (CostWeightEdge) o;
            return capacity == that.capacity && Double.compare(that.cost, cost) == 0 && Objects.equals(src, that.src) && Objects.equals(dst, that.dst);
        }

        @Override
        public int hashCode() {
            return Objects.hash(src, dst, capacity, cost);
        }
    }

//    List<MinCostMaxFlow.Edge>[] flow_graph;
//    String LeftOntology;
//    String RightOntology;
    SimpleDirectedWeightedGraph<Integer, CostWeightEdge> graph;
    int global_N;

//    private Set<String> leftPrivates, rightPrivates;

    public String fromIdx(int x) {
        for (int j = 0; j<visitedOntologyInv.size(); j++) {
            {
                var y = visitedOntologyInv.get(j);
                if (y.containsKey(x))
                    return (ontology_name[j]+"_"+y.get(x)).replace("\s", "_");
            }

            {
                var y = visitedOntologyFieldInv.get(j);
                if (y.containsKey(x))
                    return (ontology_name[j]+"_"+y.get(x).getKey()+"_"+y.get(x).getValue()).replace("\s", "_");
            }
        }
        return "none"+x;
    }

    public void exportGraph(File f){
        var graphML = new DOTExporter<Integer, CostWeightEdge>();
        graphML.setVertexIdProvider(this::fromIdx);
        graphML.setEdgeAttributeProvider(costWeightEdge -> {
            Map<String, Attribute> result = new HashMap<>();
            result.put("label", new Attribute() {
                @Override
                public String getValue() {
                    return "cost="+ costWeightEdge.cost +" cap="+ costWeightEdge.capacity;
                }

                @Override
                public AttributeType getType() {
                    return AttributeType.DOUBLE;
                }
            });
            return result;
        });
        graphML.exportGraph(graph, f);
    }

    List<HashMap<String, Integer>> visitedOntology;
    List<HashMap<Integer, String>> visitedOntologyInv;
    List<HashMap<ImmutablePair<String, String>, Integer>> visitedOntologyField;
    List<HashMap<Integer, ImmutablePair<String, String>>> visitedOntologyFieldInv;
    LabelledMatrix.LabelledMatrixBuilder fieldCorrespondenceMatrix;
    AtomicInteger ai;
    String[] ontology_name;
    int[] ontology_id;
    HashMap<Integer, Integer> getBogusForId;

    int surfOntology(Concept src, int distinguisherId, int maxConceptOpposite) {
        var currentSet = visitedOntology.get(distinguisherId);
        var currentSetInv = visitedOntologyInv.get(distinguisherId);
        var currentSetField = visitedOntologyField.get(distinguisherId);
        var currentSetFieldInv = visitedOntologyFieldInv.get(distinguisherId);
        var name = src.getSemanticName();
        if (currentSet.containsKey(name)) {
            return currentSet.get(name);
        }
        int val = ai.getAndIncrement();
        graph.addVertex(val);
        currentSet.put(name, val);
        currentSetInv.put(val, name);
        src.mapping.forEach((fieldname, concept) -> {
            int id;
            if (concept instanceof Concept) {
                id = surfOntology((Concept) concept, distinguisherId, maxConceptOpposite);
            } else {
                id = ai.getAndIncrement();
            }
            if (id != val) {
                var cp = new ImmutablePair<>(name, fieldname);
                currentSetField.put(cp, id);
                currentSetFieldInv.put(id, cp);
                graph.addVertex(id);
                if (distinguisherId == 1) {
                    graph.addEdge(id, val, new CostWeightEdge(id, val, maxConceptOpposite, 0));
                } else if (distinguisherId == 0) {
                    graph.addEdge(val, id, new CostWeightEdge(val, id, maxConceptOpposite, 0));
                }
            }
        });

        int bogus = ai.getAndIncrement();
        getBogusForId.put(val, bogus);
        graph.addVertex(bogus);
        if (distinguisherId == 1) {
            graph.addEdge(bogus, val, new CostWeightEdge(bogus, ontology_id[1], global_N, 1000));
        } else if (distinguisherId == 0) {
            graph.addEdge(val, bogus, new CostWeightEdge(ontology_id[0], bogus, global_N, 1000));
        }
        return val;
    }

    int surfOntology(Ontology src, int distinguisherId, int maxConceptOpposite) {
        ontology_name[distinguisherId] = src.getName();
        ontology_id[distinguisherId] = ai.getAndIncrement();
        graph.addVertex(ontology_id[distinguisherId]);
        for (var concept : src.getValue()) {
            int dst = surfOntology(concept, distinguisherId, maxConceptOpposite);
            if (distinguisherId == 1) {
                graph.addEdge(dst, ontology_id[1], new CostWeightEdge(dst, ontology_id[1], maxConceptOpposite, 0));
            } else if (distinguisherId == 0) {
                graph.addEdge(ontology_id[0], dst, new CostWeightEdge(ontology_id[0], dst, maxConceptOpposite, 0));
            }
        }
        int bogus = ai.getAndIncrement();
        graph.addVertex(bogus);
        getBogusForId.put(ontology_id[distinguisherId], bogus);
        if (distinguisherId == 1) {
            graph.addEdge(bogus, ontology_id[1], new CostWeightEdge(bogus, ontology_id[1], global_N, 1000));
        } else if (distinguisherId == 0) {
            graph.addEdge(ontology_id[0], bogus, new CostWeightEdge(ontology_id[0], bogus, global_N, 1000));
        }
        return ontology_id[distinguisherId];
    }


    HashSet<String> leftSet, rightSet;
    HashMap<ImmutablePair<String, String>, Correspondence> simpleFieldAlignment;
    HashMap<ImmutablePair<String, String>, Double> simpleFieldAlignmentOverCost;


    public boolean establishEdge(ConceptProperty src, ConceptProperty dst) {
        var idSrc = visitedOntologyField.get(0).get(new ImmutablePair<>(src.getAncestor().getSemanticName(), src.getSemanticName()));
        var idDst = visitedOntologyField.get(1).get(new ImmutablePair<>(dst.getAncestor().getSemanticName(), dst.getSemanticName()));
        if (!graph.containsEdge(idSrc, idDst)) {
            graph.addEdge(idSrc, idDst, new CostWeightEdge(idSrc, idDst, 1, weightFunction.getInt100Weight(src, dst)));
            return true;
        }
        return false;
    }

    HashSet<ImmutablePair<IConcept, IConcept>> s;

    public boolean establishEdge(IConcept src, IConcept dst) {
        var cp = new ImmutablePair<>(src, dst);
        if (!s.add(cp)) {
            return false;
        }
        if (!(src instanceof Concept)) {
            if (!(dst instanceof Concept)) {
                return establishEdge((ConceptProperty)src, (ConceptProperty)dst);
            } else {
                boolean atLeastOne = false;
                for (var y : ((Concept)dst).mapping.values()) {
                    if (y.equals(dst))
                        atLeastOne = true;
                    else if (establishEdge(src, y))
                        atLeastOne = true;
                }
                return atLeastOne;
            }
        } else {
            if (dst instanceof Concept) {
                boolean atLeastOne = false;
                for (var x : ((Concept)src).mapping.values()) {
                    if (x.equals(src)) {
                        atLeastOne = true;
                    } else {
                        for (var y : ((Concept)dst).mapping.values()) {
                            if (y.equals(dst))
                                atLeastOne  =true;
                            else if (establishEdge(x, y))
                                atLeastOne = true;
                        }
                    }

                }
                if (atLeastOne) {
                    var bogusSrc = getBogusForId.get(visitedOntology.get(0).get(src.getSemanticName()));
                    var bogusDst = getBogusForId.get(visitedOntology.get(1).get(dst.getSemanticName()));
                    graph.addEdge(bogusSrc, bogusDst,
                            new CostWeightEdge(bogusSrc, bogusDst, global_N,  2000));
                    return true;
                }
                return false;
            } else {
                boolean atLeastOne = false;
                for (var y : ((Concept)src).mapping.values()) {
                    if (y.equals(src)) {
                        atLeastOne = true;
                    } else if (establishEdge(y, dst))
                        atLeastOne = true;
                }
                return atLeastOne;
            }
        }
    }

    public  HashMap<ImmutablePair<String, String>, Correspondence> returnAlignmentResult() {
        return simpleFieldAlignment;
    }

    public final class UnformattedOntology extends Exception {

        public UnformattedOntology(String message) {
            super(message);
        }

        @Override
        public String getMessage() {
            return "Error: the Ontology is not well-defined (as it contains cycles): "+super.getMessage();
        }
    }


    public OntologyAlignment(Ontology src, Ontology dst, IEmbedding weightFunction) throws UnformattedOntology {
        srcOntology = src;
        dstOntology = dst;
        s = new HashSet<>();
        this.weightFunction = new Coster(weightFunction);
        floodingHasBeenRun = false;
        if (!src.checkIfOK()) {
            throw new UnformattedOntology(src.getName());
        }
        if (!dst.checkIfOK()) {
            throw new UnformattedOntology(dst.getSemanticName());
        }
    }

    public HashMap<ImmutablePair<String, String>, Correspondence> run(boolean doFieldRefine, boolean doConceptCostEvaluateBeforeAssignment) {
        floodingFieldCost();
        return fieldwiseConceptCorrespondence(doFieldRefine, doConceptCostEvaluateBeforeAssignment);
    }

    public void printAlignment(HashMap<ImmutablePair<String, String>, Correspondence> alignment) {
        for (var cp : alignment.entrySet()) {
            System.out.println("Alignment: "+srcOntology.getName()+"."+cp.getKey().getKey()+" --> "+dstOntology.getName()+"."+cp.getKey().getValue());
            System.out.println("* Cost: "+cp.getValue().getOverallCost());
            System.out.println("* Field Correspondence: ");
            for (var fields : cp.getValue().map.entrySet()) {
                for (var vals : fields.getValue().entrySet()) {
                    System.out.println(srcOntology.getName()+"."+cp.getKey().getKey()+"."+fields.getKey()+" --> "+dstOntology.getName()+"."+cp.getKey().getValue()+"."+vals.getKey()+" with score: "+vals.getValue());
                }
            }
            System.out.println(System.lineSeparator());
            System.out.println(System.lineSeparator());
        }
    }

    public HashMap<ImmutablePair<String, String>, Correspondence> fieldwiseConceptCorrespondence(boolean doFieldRefine, boolean doConceptCostEvaluateBeforeAssignment) {
        floodingFieldCost();
        HashMap<String, HashMap<String, Correspondence>> corr = new HashMap<>();
        for (var cp : simpleFieldAlignment.entrySet()) {
            if (!corr.containsKey(cp.getKey().getKey())) {
                corr.put(cp.getKey().getKey(), new HashMap<>());
            }
            corr.get(cp.getKey().getKey()).put(cp.getKey().getValue(), cp.getValue());
        }
        for (var cp: corr.entrySet()) {
            cp.getValue().forEach((s, correspondence) -> correspondence.recomputeFieldCostByNormalization(weightFunction));
        }
        if (doFieldRefine) {
            for (var cp: corr.entrySet()) {
                cp.getValue().replaceAll((s, correspondence) -> correspondence.updateCorrespondenceOverFields(weightFunction));
            }
        }
        if (doConceptCostEvaluateBeforeAssignment) {
            HashMap<ImmutablePair<String, String>, Correspondence> corr2 = new HashMap<>();
            for (var cp : corr.entrySet()) {
                for (var cp2 : cp.getValue().entrySet()) {
                    corr2.put(new ImmutablePair<>(cp.getKey(), cp2.getKey()), cp2.getValue());
                }
            }
            for (var cp: corr.entrySet()) {
                cp.getValue().forEach((s, correspondence) -> correspondence.selfUpdateWithConceptAlignment(corr2));
            }
        }
        HashMap<ImmutablePair<String, String>, Correspondence> corr2 = new HashMap<>();
        for (var cp : corr.entrySet()) {
            cp.getValue().entrySet().stream().min(Comparator.comparingDouble(o -> o.getValue().getOverallCost())).ifPresent(cp2 -> corr2.put(new ImmutablePair<>(cp.getKey(), cp2.getKey()), cp2.getValue()));
        }
        if (!doConceptCostEvaluateBeforeAssignment) {
            corr2.values().forEach(x -> x.selfUpdateWithConceptAlignment(corr2));
        }
        corr2.values().forEach(x -> x.finalizeAlignmentCost(weightFunction));
        return corr2;
    }

    private static List<List<Concept>> layerGraph(SimpleDirectedGraph<String, DefaultEdge> g, HashMap<String, Concept> mappa) {
        int maxTime = 0;
        HashMap<String, Integer> timeLeft =  new HashMap<>();

        List<List<Concept>> ls = new ArrayList<>();
        List<String> reverseTopological;
        {
            reverseTopological = new ArrayList<>();
            var it = new TopologicalOrderIterator<>(g);
            it.forEachRemaining(reverseTopological::add);
            reverseTopological = Lists.reverse(reverseTopological);
        }

        for (var v : reverseTopological) {
            var S = g.outgoingEdgesOf(v);
            if (S.isEmpty()) {
                timeLeft.put(v, 0);
            } else {
                if (!timeLeft.containsKey(v))
                    timeLeft.put(v, 0);
                for (var y : S) {
                    var val = Math.max(timeLeft.get(v), timeLeft.get(g.getEdgeTarget(y))+1);
                    maxTime = Math.max(val, maxTime);
                    timeLeft.put(v, val);
                }
            }
        }
        for (int i = 0; i<maxTime+1; i++)
            ls.add(new ArrayList<>());
        timeLeft.forEach((val, idx) -> ls.get(idx).add(mappa.get(val)));
        return ls;
    }

    SimpleDirectedGraph<String, DefaultEdge> dependencyLeft;
    SimpleDirectedGraph<String, DefaultEdge> dependencyRight;
    boolean floodingHasBeenRun;
    private void floodingFieldCost() {
        if (!floodingHasBeenRun) {
            leftSet = new HashSet<>();
            rightSet = new HashSet<>();
            simpleFieldAlignmentOverCost = new HashMap<>();
            visitedOntology = List.of(new HashMap<>(), new HashMap<>());
            visitedOntologyInv = List.of(new HashMap<>(), new HashMap<>());
            visitedOntologyField = List.of(new HashMap<>(), new HashMap<>());
            visitedOntologyFieldInv = List.of(new HashMap<>(), new HashMap<>());
            graph = new SimpleDirectedWeightedGraph<>(CostWeightEdge.class);
            dependencyLeft = new SimpleDirectedWeightedGraph<>(DefaultEdge.class);
            dependencyRight = new SimpleDirectedWeightedGraph<>(DefaultEdge.class);
            ai = new AtomicInteger(0);
            ontology_name = new String[2];
            ontology_id = new int[2];
            getBogusForId = new HashMap<>();
            global_N = srcOntology.countConceptNodes() + dstOntology.countConceptNodes();
            surfOntology(srcOntology, 0, dstOntology.countConceptNodes());
            surfOntology(dstOntology, 1, srcOntology.countConceptNodes());
            for (var left : srcOntology.getValue()) {
                for (var right : dstOntology.getValue()) {
                    establishEdge((IConcept) left, (IConcept) right);
                }
            }
            graph.addEdge(getBogusForId.get(ontology_id[0]), getBogusForId.get(ontology_id[1]),
                    new CostWeightEdge(getBogusForId.get(ontology_id[0]), getBogusForId.get(ontology_id[1]), global_N,  2000));
            int[][] capacity = new int[graph.vertexSet().size()][graph.vertexSet().size()];
            int[][] cost = new int[graph.vertexSet().size()][graph.vertexSet().size()];
            for (var e : graph.edgeSet()) {
                int e_src = e.src;
                int e_dst = e.dst;
                int e_capacity = e.capacity;
                int e_cost = (int)(e.cost*100.0);
                capacity[e_src][e_dst] = e_capacity;
                cost[e_src][e_dst] = e_cost;
            }
            MinCostMaxFlow algo = new MinCostMaxFlow();
            var result = algo.getMaxFlow(capacity, cost, ontology_id[0], ontology_id[1], dstOntology.countConceptNodes());
            simpleFieldAlignment = new HashMap<>();
            for (var path : result.minedPaths) {
                if (path.getValue().size() == 4) {
                    var srcConcept = visitedOntologyInv.get(0).get(path.getValue().get(0));
                    var dstConcept = visitedOntologyInv.get(1).get(path.getValue().get(path.getValue().size()-1));
                    leftSet.add(srcConcept);
                    rightSet.add(dstConcept);
                    dependencyLeft.addVertex(srcConcept);
                    dependencyRight.addVertex(dstConcept);
                    var ipk = new ImmutablePair<>(srcConcept, dstConcept);
                    if (!simpleFieldAlignment.containsKey(ipk)) {
                        boolean found = false;
                        for (var x : srcOntology.getValue()) {
                            if (x.getSemanticName().equals(srcConcept)) {
                                for (var y : dstOntology.getValue()) {
                                    if (y.getSemanticName().equals(dstConcept)) {
                                        simpleFieldAlignment.put(ipk, new Correspondence(x, y));
                                        found = true;
                                        break;
                                    }
                                }
                            }
                            if (found) break;
                        }
                    }
                    simpleFieldAlignmentOverCost.compute(ipk, (cp, prev) ->  {
                        if (prev == null) return path.getLeft()*1.0; else return prev+path.getLeft();
                    });
                    if ((srcConcept != null) && (dstConcept != null)) {
                        var srcFieldConcept = visitedOntologyFieldInv.get(0).get(path.getValue().get(1));
                        var dstFieldConcept = visitedOntologyFieldInv.get(1).get(path.getValue().get(2));
                        if ((srcFieldConcept != null) && (dstFieldConcept != null)) {
                            simpleFieldAlignment.get(ipk).putCorrespondence(srcFieldConcept.getRight(), dstFieldConcept.getRight(), path.getLeft(), 100);
                        }
                    }
                }
            }
            fieldCorrespondenceMatrix = new LabelledMatrix.LabelledMatrixBuilder();
            for (var x: simpleFieldAlignment.entrySet()) {
                var totalCost = (double)simpleFieldAlignmentOverCost.get(x.getKey());
                for (var field_map : x.getValue().map.entrySet()) {
                    for (var field2_cost : field_map.getValue().entrySet()) {
                        fieldCorrespondenceMatrix.put(field_map.getKey(), field2_cost.getKey(), 1.0-((double)field2_cost.getValue())/totalCost);
                        fieldCorrespondenceMatrix.put(field2_cost.getKey(), field_map.getKey(), 1.0-((double)field2_cost.getValue())/totalCost);
                    }
                }
            }
            double actualTotalCost = simpleFieldAlignmentOverCost.values().stream().mapToDouble(x->x).sum();
            simpleFieldAlignmentOverCost.replaceAll((x, v) -> {
                simpleFieldAlignment.get(x).setOverallCost(simpleFieldAlignmentOverCost.get(x) / actualTotalCost);
                return simpleFieldAlignment.get(x).getOverallCost();
            });
            floodingHasBeenRun= true;
        }

    }
}
