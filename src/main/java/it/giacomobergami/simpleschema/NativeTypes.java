package it.giacomobergami.simpleschema;

import org.jgrapht.alg.TransitiveClosure;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedPseudograph;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public enum NativeTypes {
    STRING,
    LIST_STRING,
    DOUBLE,
    LIST_DOUBLE,
    INTEGER,
    LIST_INTEGER,
    BOOLEAN,
    LIST_BOOLEAN,
    DATE,
    LIST_DATE,
    DAY,
    LIST_DAY,
    MONTH,
    LIST_MONTH,
    WEEK,
    LIST_WEEK,
    YEAR,
    LIST_YEAR,
    CONCEPT,
    LIST_CONCEPT;


    public static SimpleDirectedGraph<NativeTypes, DefaultEdge> graph = null;

    public static double isSubsetOf(NativeTypes src, NativeTypes dst) {
        if (src == dst)
            return 1.0;
        if (graph == null) {
            graph = new SimpleDirectedGraph<>(DefaultEdge.class);
            for (var x : values()) {
                graph.addVertex(x);
                if (x != x.asList()) {
                    graph.addVertex(x.asList());
                    graph.addEdge(x, x.asList());
                }
                if ((x != CONCEPT) && (x != LIST_CONCEPT) && (x != STRING)) {
                    graph.addEdge(x, STRING);
                }
            }
            graph.addEdge(DAY, DOUBLE);
            graph.addEdge(DAY, DATE);
            graph.addEdge(MONTH, DATE);
            graph.addEdge(MONTH, DOUBLE);
            graph.addEdge(WEEK, DATE);
            graph.addEdge(WEEK, DOUBLE);
            graph.addEdge(YEAR, DATE);
            graph.addEdge(YEAR, DOUBLE);
            graph.addEdge(BOOLEAN, DOUBLE);
            graph.addEdge(BOOLEAN, INTEGER);
            graph.addEdge(INTEGER, DOUBLE);
            TransitiveClosure.INSTANCE.closeSimpleDirectedGraph(graph);
        }
        return graph.containsEdge(src, dst) ? 1.1 : 2;
    }

    public NativeTypes asList() {
        switch (this) {
            case STRING -> {
                return LIST_STRING;
            }
            case DOUBLE -> {
                return LIST_DOUBLE;
            }
            case INTEGER -> {
                return LIST_INTEGER;
            }
            case BOOLEAN -> {
                return LIST_BOOLEAN;
            }
            case DATE -> {
                return LIST_DATE;
            }
            case DAY -> {
                return LIST_DAY;
            }
            case MONTH -> {
                return LIST_MONTH;
            }
            case WEEK -> {
                return WEEK;
            }
            case YEAR -> {
                return LIST_YEAR;
            }
            case CONCEPT -> {
                return LIST_CONCEPT;
            }
            default -> {
                return this;
            }
        }
    }
}
