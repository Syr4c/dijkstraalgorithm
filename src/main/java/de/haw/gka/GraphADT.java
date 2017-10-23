package de.haw.gka;

import org.javatuples.Triplet;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.List;

public class GraphADT {
    private static SimpleWeightedGraph<String, DefaultWeightedEdge> swg = new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);

    public static SimpleWeightedGraph<String, DefaultWeightedEdge> init(List<String> vertices, List<Triplet<String, String, Double>> edges){
        addAllVertices(vertices);
        addAllEdges(edges);

        return swg;
    }

    private static void addAllEdges(List<Triplet<String, String, Double>> edges){
        for(Triplet currentTriplet : edges){
            DefaultWeightedEdge currentAddedEdge = swg.addEdge((String) currentTriplet.getValue0(), (String) currentTriplet.getValue1());
            swg.setEdgeWeight(currentAddedEdge, (Double) currentTriplet.getValue2());
        }
    }

    private static void addAllVertices(List<String> vertices){
        for(String currentVertex : vertices){
            swg.addVertex(currentVertex);
        }
    }
}
