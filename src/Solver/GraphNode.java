package Solver;

import java.io.Serializable;

public class GraphNode implements Comparable<GraphNode>, Serializable {
    public String word = null;
    public GraphNode prevNode = null;
    int cost = -1; // if -1, it means its not calculated yet

    public GraphNode(String word, GraphNode prevNode){
        this.word = word;
        this.prevNode = prevNode;
    }

    public int getCost(){
        if(cost != -1) return cost;

        if(prevNode == null){
            cost = 1;
        } else {
            cost = prevNode.getCost() + 1;
        }
        return cost;
    }

    @Override
    public int compareTo(GraphNode o) {
        return getCost() - o.getCost(); // smaller cost is better
    }
}