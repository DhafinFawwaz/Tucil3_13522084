package Solver;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class AStarSolver extends WordLadderSolver {
    AStarComparator comparator;

    public AStarSolver(String source, String destination, ArrayList<GraphAdjacencyMap> graphList) {
        super(source, destination, graphList);
        comparator = new AStarComparator(source, destination);
    }

    @Override
    void populateSolutionData(GraphAdjacencyMap graph, SolutionData solutionData){
        PriorityQueue<GraphNode> queue = new PriorityQueue<GraphNode>(comparator);
        startSearch(graph, solutionData, queue);
    }
}
