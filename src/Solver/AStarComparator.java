package Solver;

public class AStarComparator extends GBFSComparator {
    public AStarComparator(String source, String destination){
        super(destination);
    }

    // Basically the g(n) + h(n)
    // g(n) is the cost from source to current
    // h(n) is the heuristic function that calculated according to current to destination
    @Override
    public int compare(GraphNode a, GraphNode b){
        int gA = a.getDepth();
        int hA = wordDifference(a.word, destination);
        int fA = gA + hA;

        int gB = b.getDepth();
        int hB = wordDifference(b.word, destination);
        int fB = gB + hB;

        return fA - fB; // smaller difference is better
    }
}