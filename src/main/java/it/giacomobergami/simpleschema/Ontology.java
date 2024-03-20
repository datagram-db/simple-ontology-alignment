/*
 * Ontology.java
 * This file is part of simple-ontology-alignment
 *
 * Copyright (C) 2024 - Giacomo Bergami
 *
 * simple-ontology-alignment is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * simple-ontology-alignment is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with simple-ontology-alignment. If not, see <http://www.gnu.org/licenses/>.
 */

 package it.giacomobergami.simpleschema;

import it.giacomobergami.linalg.LabelledMatrix;
import it.giacomobergami.simpleschema.barbara.barbaraLexer;
import it.giacomobergami.simpleschema.barbara.barbaraParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.jgrapht.alg.cycle.CycleDetector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedPseudograph;
import uk.ncl.giacomobergami.utils.structures.ImmutablePair;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class Ontology extends ImmutablePair<String, ArrayList<Concept>> implements IConcept {

    public Ontology(String key, ArrayList<Concept> value) {
        super(key, value);
        for (var x : value) {
            x.ancestor = this;
        }
    }

    public String getName() {
        return getKey();
    }
    public int countConceptNodes() {
        int count = 0;
        Set<String> visited = new HashSet<>();
        for (Concept x : getValue())
            count += x.countConceptNodes(visited);
        return count;
    }

    public static String unescape(String x) {
        return x
                .replaceAll("^['\"]*", "")
                .replaceAll("['\"]*$", "");
    }

    public static final class UndefinedConceptException extends Exception {

        private final String ontology;

        public UndefinedConceptException(String Ontology, String message) {
            super(message);
            ontology = Ontology;
        }

        @Override
        public String getMessage() {
            return "ERROR:  ontology '"+ontology+"' has a field referring to an undeclared concept, "+super.getMessage();
        }
    }

    public LabelledMatrix.LabelledMatrixBuilder buildConceptToFieldCorrespondenceMatrix() {
        LabelledMatrix.LabelledMatrixBuilder builder = new LabelledMatrix.LabelledMatrixBuilder();
        for (var concept : getValue()) {
            for (var fieldOrConcept : concept.mapping.entrySet()) {
                if (!(fieldOrConcept.getValue() instanceof Concept)) {
                    builder.put(concept.getSemanticName(), fieldOrConcept.getKey(), 1);
                }
            }
        }
        return builder;
    }

    private static Ontology ontologyFromCharStream(CharStream stream) throws UndefinedConceptException{
        barbaraLexer lexer = new barbaraLexer(stream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        barbaraParser parser = new barbaraParser(tokens);
        var ctx = parser.ontology();
        Map<String, Concept> ontologyConcepts = new HashMap<>();
        String ontologyName = unescape(ctx.name.getText());
        for (var concept : ctx.concept()) {
            String conceptName = unescape(concept.name.getText());
            if (!ontologyConcepts.containsKey(conceptName)) {
                Concept c = new Concept(null, conceptName);
                for (var property : concept.properties()) {
                    var key = unescape(property.EscapedString().getText());
                    var value = property.native_type();
                    boolean isList = property.LIST() != null;
                    if (value.getText().startsWith("CONCEPT")) {
                        var conceptType = NativeTypes.CONCEPT;
                        if (isList) {
                            conceptType = conceptType.asList();
                            c.addListProperty(key);
                        }
                        c.addProperty(key, new ConceptProperty(c, unescape(value.EscapedString().toString()), conceptType));
                    } else {
                        var conceptType = NativeTypes.valueOf(value.getText().trim());
                        if (isList) {
                            c.addListProperty(key);
                            conceptType = conceptType.asList();
                        }
                        c.addProperty(key, new ConceptProperty(c, key, conceptType));
                    }
                }
                ontologyConcepts.put(conceptName, c);
            }
        }
        for (var values : ontologyConcepts.values()) {
            var ls = values
                    .components()
                    .stream()
                    .filter(x -> x.getValue().getType() == NativeTypes.CONCEPT || x.getValue().getType() == NativeTypes.LIST_CONCEPT)
                    .map(x -> new ImmutablePair<String, String>(x.getKey(), x.getValue().getSemanticName()))
                    .toList();
            for (var cp : ls) {
                var x = ontologyConcepts.get(cp.getValue());
                if (x == null) {
                    throw new UndefinedConceptException(ontologyName, cp.getValue());
                }
                values.addProperty(cp.getKey(), x);
            }
        }
        return new Ontology(ontologyName, new ArrayList<>(ontologyConcepts.values()));
    }

    public static Ontology ontologyFromFile(Path f) throws Exception {
        return ontologyFromCharStream(CharStreams.fromPath(f));
    }

    public static Ontology ontologyFromString(String str) throws Exception {
        return ontologyFromCharStream(CharStreams.fromString(str));
    }

    @Override
    public IConcept getAncestor() {
        return this;
    }

    @Override
    public String getSemanticName() {
        return getName();
    }

    @Override
    public NativeTypes getType() {
        return NativeTypes.LIST_CONCEPT;
    }

    @Override
    public boolean isBasic() {
        return false;
    }

    @Override
    public Optional<Concept> asConcept() {
        return Optional.empty();
    }

    @Override
    public int countConceptNodes(Set<String> visited) {
        return 0;
    }

    @Override
    public boolean isList() {
        return true;
    }

    public boolean checkIfOK() {
        DirectedPseudograph<String, DefaultEdge> graph = new DirectedPseudograph<>(DefaultEdge.class);
        for (var x : getValue()) {
            graph.addVertex(x.getSemanticName());
        }
        for (var x : getValue()) {
            for (var y : x.mapping.values()) {
                if (y instanceof Concept) {
                    if (!Objects.equals(x.getSemanticName(), y.getSemanticName()))
                        graph.addEdge(x.getSemanticName(), y.getSemanticName());
                }
            }
        }
        return !new CycleDetector<>(graph).detectCycles();
    }

    @Override
    public Set<Map.Entry<String, IConcept>> components() {
        return null;
    }

    @Override
    public Collection<? extends IConcept> components2() {
        return null;
    }
}
