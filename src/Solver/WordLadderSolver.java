package Solver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Set;


public abstract class WordLadderSolver {
    String source;
    String destination;
    ArrayList<GraphAdjacencyMap> graphList;
    public WordLadderSolver(String source, String destination, ArrayList<GraphAdjacencyMap> graphList) {
        if(source.length() != destination.length()){
            throw new IllegalArgumentException("Source and Destination must have the same length");
        }
        this.source = source;
        this.destination = destination;
        this.graphList = graphList;
    }

    abstract void populateSolutionResultPath(GraphAdjacencyMap graph, LinkedList<String> resultPath);

    public SolutionData Solve() throws Exception {
        if (source.isEmpty() || destination.isEmpty()) {
            throw new IllegalArgumentException("Please fill in both fields");
        }
        if(source.length() != destination.length()) {
            throw new IllegalArgumentException("Source and destination must have the same length");
        }

        GraphAdjacencyMap graph = graphList.get(source.length());
        if(!graph.containsKey(source)) {
            throw new IllegalArgumentException("Source word not found in dictionary");
        }
        if(!graph.containsKey(destination)) {
            throw new IllegalArgumentException("Destination word not found in dictionary");
        }

        SolutionData solution = new SolutionData().startTimer();
        LinkedList<String> resultPath = new LinkedList<String>();

        populateSolutionResultPath(graph, resultPath);

        return solution.setSolution(resultPath).endTimer();
    }

    void startSearch(GraphAdjacencyMap graph, LinkedList<String> resultPath, PriorityQueue<GraphNode> queue){
        Set<String> visited = new HashSet<String>();

        queue.add(new GraphNode(source, null));
        while(queue.size() > 0){
            GraphNode currentNode = queue.poll();
            if(currentNode.word.equals(destination)){
                // Trace back to the source
                GraphNode currentTracedNode = currentNode;
                while(currentTracedNode.prevNode != null){
                    resultPath.add(0, currentTracedNode.word);
                    currentTracedNode = currentTracedNode.prevNode;
                }
                resultPath.add(0, source);
                break;
            }

            ArrayList<GraphNode> neighborList = graph.get(currentNode.word);

            for(GraphNode neighbor : neighborList){
                if(!visited.contains(neighbor.word)){
                    visited.add(neighbor.word);
                    neighbor.prevNode = currentNode;
                    queue.add(neighbor);
                }
            }
            visited.add(currentNode.word);
        }
    }
}
