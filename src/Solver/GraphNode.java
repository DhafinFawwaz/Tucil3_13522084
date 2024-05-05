package Solver;

import java.io.Serializable;
import java.util.Comparator;
import java.util.PriorityQueue;

public class GraphNode implements Comparable<GraphNode>, Serializable {
    public String word = null;
    public GraphNode prevNode = null;
    int cost = -1; // if -1, it means its not calculated yet

    public GraphNode(String word, GraphNode prevNode){
        this.word = word;
        this.prevNode = prevNode;
    }

    public int getDepth(){
        if(cost != -1) return cost;

        if(prevNode == null){
            cost = 1;
        } else {
            cost = prevNode.getDepth() + 1;
        }
        return cost;
    }

    public void reset(){
        cost = -1;
        prevNode = null;
    }

    @Override
    public int compareTo(GraphNode o) {
        return getDepth() - o.getDepth(); // g(n), smaller is better
    }

    public static void main(String[] args) {
        GraphNode a = new GraphNode("arang", null);
        GraphNode b = new GraphNode("brang", null);
        GraphNode c = new GraphNode("arazg", null);
        GraphNode d = new GraphNode("argan", null);
        GraphNode e = new GraphNode("ccang", null);
        c.cost = 9;
        a.cost = 6;
        b.cost = 3;
        d.cost = 4;
        e.cost = 1;
        Comparator<GraphNode> comparator = new GBFSComparator("arang");

        PriorityQueue<GraphNode> queue = new PriorityQueue<GraphNode>(comparator);
        queue.add(a);
        queue.add(b);
        queue.add(c);
        queue.add(d);
        queue.add(e);

        System.out.println("Traversal:");
        for(GraphNode node : queue){
            System.out.println(node.word + ": " + node.getDepth());
        }

        System.out.println("Poll:");
        while(!queue.isEmpty()){
            GraphNode node = queue.poll();
            System.out.println(node.word + ": " + node.getDepth());
        }
    }
}