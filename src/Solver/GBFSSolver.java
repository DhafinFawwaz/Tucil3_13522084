package Solver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Set;



public class GBFSSolver extends WordLadderSolver {
    GBFSComparator comparator;

    public GBFSSolver(String source, String destination, ArrayList<GraphAdjacencyMap> graphList) {
        super(source, destination, graphList);
        comparator = new GBFSComparator(destination);
    }

    // if return null, it means all nodes in currentNeighborList are visited
    GraphNode findMinimumNodeNotInVisited(GraphNode currentNode, ArrayList<GraphNode> currentNeighborList, Set<String> visited){
        ArrayList<GraphNode> notVisited = new ArrayList<GraphNode>();
        for(GraphNode node : currentNeighborList){
            if(!visited.contains(node.word)){
                notVisited.add(node);
            }
        }


        if(notVisited.isEmpty()){
            return null;
        }

        GraphNode minimumNode = notVisited.get(0);
        for(GraphNode node : notVisited){
            if(comparator.compare(node, minimumNode) < 0){
                minimumNode = node;
            }
        }

        return minimumNode;
    }
    
    @Override
    void populateSolutionData(GraphAdjacencyMap graph, SolutionData solutionData){
        // there are 2 variant. but the specification is not clear which to use. 
        // Either with queue or not. Without queue, result with no solution is possible
        // simply uncomment either the 2 lines or the lines after that. choose one.
        // PriorityQueue<GraphNode> queue = new PriorityQueue<GraphNode>(comparator); 
        // startSearch(graph, solutionData, queue); 

        LinkedList<String> resultPath = solutionData.getSolutionPath();
        Set<String> visited = new HashSet<String>();

        GraphNode currentNode = new GraphNode(source, null);
        if(currentNode.word.equals(destination)){ // if source is the destination
            resultPath.add(currentNode.word);
            solutionData.setSolution(resultPath);
            return;
        }

        ArrayList<GraphNode> currentNeighborList = graph.get(source);
		while (!currentNeighborList.isEmpty()) {
            resultPath.add(currentNode.word);
            visited.add(currentNode.word);

            GraphNode minimumNode = findMinimumNodeNotInVisited(currentNode, graph.get(currentNode.word), visited);
            if(minimumNode == null) break;

            if(minimumNode.word.equals(destination)){
                resultPath.add(minimumNode.word);
                currentNode = minimumNode;
                break;
            }

            currentNode = minimumNode;
            currentNeighborList = graph.get(currentNode.word);
		}

        if(!currentNode.word.equals(destination)){
            solutionData.setSolution(new LinkedList<String>());
        } else {
            solutionData.setSolution(resultPath);
        }

        solutionData.setNodeVisited(visited.size());
    }
}
