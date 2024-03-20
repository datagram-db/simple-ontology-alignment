/*
 * ConceptNet5SingleConceptSimilarity.java
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

 
package it.giacomobergami.similarity;

import net.intertextueel.conceptnet.Concept;

import javax.annotation.Nullable;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.zip.GZIPInputStream;

import static it.giacomobergami.similarity.EmbeddingFactory.kernel_n;

public class ConceptNet5SingleConceptSimilarity implements IEmbedding {

    private final double maxTheta;
    private HashMap<String, float[]> mapVector;
    private HashMap<String, Concept> mapConcept;

    InputStream retrieveNumberbatch() throws IOException {
        File f = new File("numberbatch-en-19.08.txt.gz");
        if (!f.exists()) {
            URL website = new URL("https://conceptnet.s3.amazonaws.com/downloads/2019/numberbatch/numberbatch-en-19.08.txt.gz");
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            System.out.println("Downloading from: \"https://conceptnet.s3.amazonaws.com/downloads/2019/numberbatch/numberbatch-en-19.08.txt.gz\"");
            try (FileOutputStream fos = new FileOutputStream(f)) {
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("done!");
        }
        return new FileInputStream(f);
    }

    public ConceptNet5SingleConceptSimilarity( @Nullable String lang, double maxTheta) throws IOException {
        this.maxTheta = maxTheta;
        mapVector = new HashMap<>();
        mapConcept = new HashMap<>();
        InputStream fileStream = retrieveNumberbatch();
        InputStream gzipStream = new GZIPInputStream(fileStream);
        Reader decoder = new InputStreamReader(gzipStream, StandardCharsets.UTF_8);
        BufferedReader buffered = new BufferedReader(decoder);
        String content;
        buffered.readLine();
        System.out.println("Loading now, please wait...");
        while ((content = buffered.readLine()) != null) {
            processNumberbatchLine(content, lang);
        }
        System.out.println("Done!");
    }

    public Set<String> filterByMaxVal(String toAnalyse) {
        Set<String> bagToAnalyse = new HashSet<>();
        for (var cp : mapVector.entrySet()) {
            if (LowConfidenceRank.getInstance().sim(cp.getKey(), toAnalyse) >= maxTheta) {
                bagToAnalyse.add(cp.getKey());
            }
        }
        return bagToAnalyse;
    }

    protected void processNumberbatchLine(String line, String lang) {
        /* split space-delimited fields */
        String[] columns = line.split(" ");
        String conceptstring = columns[0];
        Concept concept = new Concept("/c/en/"+conceptstring);
        if ((lang == null) || (Objects.equals(lang, concept.getLanguage()))) {
            float[] coords = new float[columns.length-1];
            for (int i = 1; i<columns.length; i++) {
                coords[i-1] = Float.parseFloat(columns[i]);
            }
            var dis = concept.getWordphrase();
            mapConcept.put(dis, concept);
            mapVector.put(dis, coords);
        }
    }

    @Override
    public double normalised_distance(String left, String right) {
        double similarity = 0;
        var lsY = filterByMaxVal(right);
        for (var x : filterByMaxVal(left)) {
            var vecX = mapVector.get(x);
            for (var y : lsY) {
                var vecY = mapVector.get(y);
                double sim = kernel_n(vecX, vecY);
                if (sim > similarity) {
                    similarity = sim;
                }
            }
        }
        return 1.0-similarity;
    }

    @Override
    public void close() throws Exception {
        mapConcept.clear();
        mapVector.clear();
    }

    public static void main(String args[]) throws IOException {
        var x = new ConceptNet5SingleConceptSimilarity(null, 0.8);
        System.out.println(x.normalised_distance("Twitter Account", "Account"));
        System.out.println(x.normalised_distance("Surname", "Account"));
        System.out.println(x.normalised_distance("Name", "Account"));
    }

}
