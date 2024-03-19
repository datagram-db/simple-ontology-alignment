/*
 * Main.java
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

 package org.example;

import it.giacomobergami.similarity.EmbeddingCases;
import it.giacomobergami.similarity.EmbeddingFactory;
import it.giacomobergami.simpleschema.Ontology;
import it.giacomobergami.simpleschema.OntologyAlignment;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {

        // Loading an ontology specification
        var facebook = Ontology.ontologyFromFile(new File("Facebook.txt").toPath());
        var twitter = Ontology.ontologyFromFile(new File("Twitter.txt").toPath());

        // Choosing the word embedding to determing the field-base sentence similarity. For this example, ConceptNetWithMatch is better than BertWord
        try (var embedding = EmbeddingFactory.getSimilarityScore(EmbeddingCases.ConceptNetWithMatch)) {
            var Aligner = new OntologyAlignment(facebook, twitter, embedding); // Initialising the alignment function
            var A = Aligner.run(false, false); // Running the alignment
            Aligner.printAlignment(A); // A printing aid, showing what the alignment should represent, the correspondence across fields within the class
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
