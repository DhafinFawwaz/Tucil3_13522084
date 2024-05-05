package Solver;

import java.io.Serializable;
import java.util.PriorityQueue;

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

    public void reset(){
        cost = -1;
        prevNode = null;
    }

    @Override
    public int compareTo(GraphNode o) {
        return getCost() - o.getCost(); // smaller cost is better
    }

    public static void main(String[] args) {
        GraphNode a = new GraphNode("a", null);
        GraphNode b = new GraphNode("b", null);
        GraphNode c = new GraphNode("c", null);
        GraphNode d = new GraphNode("d", null);
        GraphNode e = new GraphNode("e", null);
        c.cost = 9;
        a.cost = 6;
        b.cost = 3;
        d.cost = 4;
        e.cost = 1;

        PriorityQueue<GraphNode> queue = new PriorityQueue<GraphNode>();
        queue.add(a);
        queue.add(b);
        queue.add(c);
        queue.add(d);
        queue.add(e);

        System.out.println("Traversal:");
        for(GraphNode node : queue){
            System.out.println(node.word + ": " + node.getCost());
        }

        System.out.println("Poll:");
        while(!queue.isEmpty()){
            GraphNode node = queue.poll();
            System.out.println(node.word + ": " + node.getCost());
        }
    }
}