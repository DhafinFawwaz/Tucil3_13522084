package Solver;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class UCSSolver extends WordLadderSolver {
    public UCSSolver(String source, String destination, ArrayList<GraphAdjacencyMap> graphList) {
        super(source, destination, graphList);
    }

    @Override
    void populateSolutionResultPath(GraphAdjacencyMap graph, LinkedList<String> resultPath){
        PriorityQueue<GraphNode> queue = new PriorityQueue<GraphNode>();
        startSearch(graph, resultPath, queue);
    }
}
