package it.giacomobergami.similarity;

import net.intertextueel.conceptnet.Concept;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

import javax.annotation.Nullable;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.IntFunction;
import java.util.zip.GZIPInputStream;

import static it.giacomobergami.similarity.EmbeddingFactory.kernel_n;

public class ConceptNet5SingleConceptSimilarity implements IEmbedding {

    private final double maxTheta;
    private HashMap<String, float[]> mapVector;
    private HashMap<String, Concept> mapConcept;

    public ConceptNet5SingleConceptSimilarity( @Nullable String lang, double maxTheta) throws IOException {
        this.maxTheta = maxTheta;
        mapVector = new HashMap<>();
        mapConcept = new HashMap<>();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream fileStream = classloader.getResourceAsStream("numberbatch-en-19.08.txt.gz");
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
