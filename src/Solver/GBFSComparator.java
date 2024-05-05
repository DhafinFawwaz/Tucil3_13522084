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

    // Basically the h(n), the heuristic function that calculated according to current to destination
    @Override
    public int compare(GraphNode a, GraphNode b){
        int hA = wordDifference(a.word, destination);
        int hB = wordDifference(b.word, destination);
        return hA - hB; // smaller difference is better
    }
}