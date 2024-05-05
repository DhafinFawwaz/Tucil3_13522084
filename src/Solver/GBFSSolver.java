package Solver;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Comparator;


public class GBFSSolver extends WordLadderSolver {
    Comparator<GraphNode> comparator;

    public GBFSSolver(String source, String destination, ArrayList<GraphAdjacencyMap> graphList) {
        super(source, destination, graphList);
        comparator = new GBFSComparator(destination);
    }
    
    @Override
    void populateSolutionData(GraphAdjacencyMap graph, SolutionData solutionData){
        PriorityQueue<GraphNode> queue = new PriorityQueue<GraphNode>(comparator);
        startSearch(graph, solutionData, queue);
    }
}
