package Solver;

import java.util.Comparator;

public class GBFSComparator implements Comparator<GraphNode> {
    String source;
    String destination;
    public GBFSComparator(String source, String destination){
        this.source = source;
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

    @Override
    public int compare(GraphNode a, GraphNode b){
        int diffA = wordDifference(a.word, destination);
        int diffB = wordDifference(b.word, destination);
        return diffA - diffB; // smaller difference is better
    }
}