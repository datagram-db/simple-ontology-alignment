package it.giacomobergami.similarity;

public interface IEmbedding extends AutoCloseable {

    public double normalised_distance(String left, String right);

}
