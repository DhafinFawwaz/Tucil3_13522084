package Solver;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class AStarSolver extends WordLadderSolver {
    Comparator<GraphNode> comparator = new AStarComparator(source, destination);

    public AStarSolver(String source, String destination, ArrayList<GraphAdjacencyMap> graphList) {
        super(source, destination, graphList);
    }

    @Override
    void populateSolutionData(GraphAdjacencyMap graph, SolutionData solutionData){
        PriorityQueue<GraphNode> queue = new PriorityQueue<GraphNode>(comparator);
        startSearch(graph, solutionData, queue);
    }
}
