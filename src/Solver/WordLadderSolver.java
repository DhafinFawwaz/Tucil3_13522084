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

    abstract void populateSolutionData(GraphAdjacencyMap graph, SolutionData solutionData);

    public SolutionData Solve() throws Exception {
        if (source.isEmpty() || destination.isEmpty()) {
            throw new IllegalArgumentException("Please fill in both fields");
        }
        if(source.length() != destination.length()) {
            throw new IllegalArgumentException("Source and destination must have the same length");
        }
        
        source = source.toLowerCase();
        destination = destination.toLowerCase();

        int wordLength = source.length();
        if(graphList.size() <= wordLength) {
            throw new IllegalArgumentException("Word with length "+wordLength+" not found in dictionary");
        }
        GraphAdjacencyMap graph = graphList.get(wordLength);

        if(!graph.containsKey(source)) {
            throw new IllegalArgumentException("Source word not found in dictionary");
        }
        if(!graph.containsKey(destination)) {
            throw new IllegalArgumentException("Destination word not found in dictionary");
        }


        SolutionData solution = new SolutionData().startTimer();
        solution.setSolution(new LinkedList<String>());

        // make sure all prev nodes are null and cost is reset
        var values = graph.values();
        values.parallelStream().forEach(list -> {
            list.forEach(node -> {
                node.reset();
            });
        });

        populateSolutionData(graph, solution);

        if(solution.getSolutionSize() == 0){
            solution.endTimer();
            throw new SolutionException(solution.getDuration());
        }

        return solution.endTimer();
    }

    void startSearch(GraphAdjacencyMap graph, SolutionData solutionData, PriorityQueue<GraphNode> queue){
        LinkedList<String> resultPath = solutionData.getSolutionPath();
        Set<String> visited = new HashSet<String>();

        queue.add(new GraphNode(source, null));

        while(!queue.isEmpty()){
            GraphNode currentNode = queue.poll();

            if (visited.contains(currentNode.word)) continue;
			visited.add(currentNode.word);

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
                neighbor.prevNode = currentNode;
                queue.add(neighbor);
            }
        }

        solutionData.setNodeVisited(visited.size());
    }
}
