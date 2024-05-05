package Solver;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class UCSSolver extends WordLadderSolver {
    public UCSSolver(String source, String destination, ArrayList<GraphAdjacencyMap> graphList) {
        super(source, destination, graphList);
    }

    @Override
    void populateSolutionData(GraphAdjacencyMap graph, SolutionData solutionData){
        PriorityQueue<GraphNode> queue = new PriorityQueue<GraphNode>();
        startSearch(graph, solutionData, queue);
    }
}
