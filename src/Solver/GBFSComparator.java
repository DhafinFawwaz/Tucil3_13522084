package Solver;

import java.util.Comparator;

public class GBFSComparator implements Comparator<GraphNode> {
    String destination;
    public GBFSComparator(String destination){
        this.destination = destination;
    }

    int wordDifference(String a, String b){
        int diff = 0;
        for(int i = 0; i < a.length(); i++){
            if(a.charAt(i) != b.charAt(i)){
                diff++;
            }
        }
        return diff;
    }

    // Basically the f(n), the heuristic function that calculated according to current to destination
    @Override
    public int compare(GraphNode a, GraphNode b){
        int fA = wordDifference(a.word, destination);
        int fB = wordDifference(b.word, destination);
        return fA - fB; // smaller difference is better
    }
}