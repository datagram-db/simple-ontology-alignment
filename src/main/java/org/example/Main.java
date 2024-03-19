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