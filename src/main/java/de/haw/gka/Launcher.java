package de.haw.gka;

import org.javatuples.Triplet;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.ArrayList;
import java.util.List;

public class Launcher {
    public static void main(String[] args){
        List<String> vertices = new ArrayList<String>();
        List<Triplet<String, String, Double>> edges = new ArrayList<Triplet<String, String, Double>>();

        // Beispiel 2.3, Seite 43
        vertices.add("v1");
        vertices.add("v2");
        vertices.add("v3");
        vertices.add("v4");
        vertices.add("v5");
        vertices.add("v6");

        // Beispiel 2.3, Seite 43
        edges.add(new Triplet<String, String, Double>("v1", "v2", 1.0));
        edges.add(new Triplet<String, String, Double>("v1", "v6", 3.0));
        edges.add(new Triplet<String, String, Double>("v2", "v6", 2.0));
        edges.add(new Triplet<String, String, Double>("v2", "v5", 3.0));
        edges.add(new Triplet<String, String, Double>("v2", "v3", 5.0));
        edges.add(new Triplet<String, String, Double>("v6", "v3", 2.0));
        edges.add(new Triplet<String, String, Double>("v6", "v5", 1.0));
        edges.add(new Triplet<String, String, Double>("v5", "v3", 2.0));
        edges.add(new Triplet<String, String, Double>("v3", "v4", 1.0));
        edges.add(new Triplet<String, String, Double>("v5", "v4", 3.0));

        SimpleWeightedGraph<String, DefaultWeightedEdge> swg = GraphADT.init(vertices, edges);

        Dijkstra dij = new Dijkstra(swg);

        dij.startSearchProcess("v1");
    }
}
