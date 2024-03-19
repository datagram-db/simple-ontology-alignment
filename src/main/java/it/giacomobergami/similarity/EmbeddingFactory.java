package it.giacomobergami.similarity;

import com.robrua.nlp.bert.Bert;

import java.io.IOException;

public class EmbeddingFactory {

    public static double kernel(float[] a, float[] b) {
        double sum = 0;
        for (int i = 0; i < Math.min(a.length, b.length); i++)
        {
            sum += a[i] * b[i];
        }
        return sum;
    }

    public static double kernel_n(float[] a, float[] b) {
        return kernel(a,b)/(double) Math.sqrt(kernel(a,a)*kernel(b,b));
    }

    public static IEmbedding getSimilarityScore(EmbeddingCases cases) {
        switch (cases) {
            case BertWord -> {
                return new IEmbedding() {
                    Bert bert = Bert.load("com/robrua/nlp/easy-bert/bert-multi-cased-L-12-H-768-A-12");
                    @Override
                    public void close() throws Exception {
                        bert.close();
                    }
                    @Override
                    public double normalised_distance(String left, String right) {
                        float[] embedding1 = bert.embedSequence(left);
                        float[] embedding2 = bert.embedSequence(right);
                        return (double)1.0-kernel_n(embedding1, embedding2);
                    }
                };
            }
            case ConceptNetWithMatch -> {
                try {
                    return new ConceptNet5SingleConceptSimilarity(null, 0.7);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        return null;
    }

}
