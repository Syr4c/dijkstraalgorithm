package de.haw.gka;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Dijkstra {
    // v -> visited vertices (OK := true), r -> remaining vertices (OK := false)
    // distanceRel -> (current, distance) := 'ENTFi', sourceRel -> (current, source) := 'VORGi'
    private HashMap<String, Double> distanceRel = new HashMap<String, Double>();
    private HashMap<String, String> sourceRel = new HashMap<String, String>();
    private SimpleWeightedGraph<String, DefaultWeightedEdge> swg;
    private HashSet<String> v = new HashSet<String>();
    private HashSet<String> r = new HashSet<String>();
    private Timestamp startTime;
    private Timestamp endTime;

    public Dijkstra(){}

    public Dijkstra(SimpleWeightedGraph<String, DefaultWeightedEdge> swg){
        this.swg = swg;
        r.addAll(swg.vertexSet());
    }

    public void startSearchProcess(String startVertex, String goalVertex){
        //TODO
        startSearchProcess(startVertex);
    }

    public void startSearchProcess(String startVertex){
        if(!swg.containsVertex(startVertex)){
            throw new IllegalArgumentException("given vertex is not existing");
        }
        startTime = new Timestamp(System.currentTimeMillis());

        v.add(startVertex);
        r.remove(startVertex);
        System.out.println("added " + startVertex + " to [v]");
        System.out.println("removed " + startVertex + " from [r]");

        Set<DefaultWeightedEdge> targets = swg.edgesOf(startVertex);
        DefaultWeightedEdge nextTargetEdge = getMinDistanceEdge(targets);

        distanceRel.put(startVertex, 0.0);

        init(swg.getEdgeTarget(nextTargetEdge), startVertex);
    }

    private void init(String currentVertex, String sourceVertex){
        System.out.println("calling init() with : " + currentVertex + " as vertex");

        v.add(currentVertex);
        r.remove(currentVertex);
        System.out.println("[init] added " + currentVertex + " to [v]");
        System.out.println("[init] removed " + currentVertex + " from [r]");

        //FIXME
        if(distanceRel.containsKey(currentVertex)){
            Double currentValue = distanceRel.get(currentVertex);
            Double newValue = distanceRel.get(sourceVertex) + swg.getEdgeWeight(swg.getEdge(sourceVertex, currentVertex));

            if(checkDistancLess(currentValue, newValue)){
                System.out.println("[init] set sourceRel key: " + currentVertex + ", value: " + sourceVertex);
                System.out.println("[init] set distanceRel key: " + currentVertex + ", value: " + newValue + " - [was value: " + currentVertex + "]");
                sourceRel.put(currentVertex, sourceVertex);
                distanceRel.put(sourceVertex, newValue);
            }
        } else {
            System.out.println("[init] set sourceRel key: " + currentVertex + ", value: " + sourceVertex);
            System.out.println("[init] set distanceRel key: " + currentVertex + ", value: " + swg.getEdgeWeight(swg.getEdge(sourceVertex, currentVertex)));
            sourceRel.put(currentVertex, sourceVertex);
            distanceRel.put(currentVertex, swg.getEdgeWeight(swg.getEdge(sourceVertex, currentVertex)));
        }

        iterationCycle(currentVertex);
    }

    private void iterationCycle(String currentVertex){
        Set<DefaultWeightedEdge> edges = swg.edgesOf(currentVertex);
        System.out.println("[iterationCycle] get target edges : " + currentVertex + ", target edges count : " + edges.size());
        DefaultWeightedEdge nextMinEdge = getMinDistanceEdge(edges);

        if(nextMinEdge != null){
            System.out.println("[iterationCycle] current vertex : " + currentVertex + ", next target : " + swg.getEdgeTarget(nextMinEdge));
            init(swg.getEdgeTarget(nextMinEdge), currentVertex);
        } else {
            endTime = new Timestamp(System.currentTimeMillis());
            long difference = endTime.getTime() - startTime.getTime();
            System.out.println("Algorithm finished in " + difference + " ms");
            System.out.println(getResultAsString());
        }
    }

    private DefaultWeightedEdge getMinDistanceEdge(Set<DefaultWeightedEdge> edges){
        DefaultWeightedEdge resultEdge = null;
        Double resultMin = null;
        Double currentMin = null;

        for(DefaultWeightedEdge currentEdge : edges){
            if(resultMin == null && r.contains(swg.getEdgeTarget(currentEdge))){
                resultMin = swg.getEdgeWeight(currentEdge);
                resultEdge = currentEdge;
            } else if (r.contains(swg.getEdgeTarget(currentEdge))){
                currentMin = swg.getEdgeWeight(currentEdge);
                if(currentMin < resultMin){
                    resultMin = currentMin;
                    resultEdge = currentEdge;
                }
            }
        }

        return resultEdge;
    }

    private String getResultAsString(){
        StringBuilder sb = new StringBuilder();
            sb.append("origin graph: ");
            sb.append(swg.toString());
            sb.append("\n\nvertex\t|\tfastes source\t|\ttotal distance");

            for(String currentVertex : swg.vertexSet()){
                sb.append("\n" + currentVertex + "\t|\t" + sourceRel.get(currentVertex) + "\t|\t" + distanceRel.get(currentVertex));
            }

        return sb.toString();
    }

    private boolean checkDistancLess(Double currentValue, Double newValue){
        return currentValue > newValue;
    }

    public SimpleWeightedGraph<String, DefaultWeightedEdge> getSwg() {
        return swg;
    }

    public void setSwg(SimpleWeightedGraph<String, DefaultWeightedEdge> swg) {
        this.swg = swg;
    }
}
