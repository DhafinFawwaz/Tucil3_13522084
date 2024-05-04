package Solver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Comparator;


public class GBFSSolver extends WordLadderSolver {
    Comparator<GraphNode> comparator = new GBFSComparator(source, destination);

    public GBFSSolver(String source, String destination, ArrayList<GraphAdjacencyMap> graphList) {
        super(source, destination, graphList);
    }
    
    @Override
    void populateSolutionResultPath(GraphAdjacencyMap graph, LinkedList<String> resultPath){
        PriorityQueue<GraphNode> queue = new PriorityQueue<GraphNode>(comparator);
        startSearch(graph, resultPath, queue);
    }
}
