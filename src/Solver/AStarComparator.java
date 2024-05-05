package Solver;

public class AStarComparator extends GBFSComparator {
    public AStarComparator(String source, String destination){
        super(destination);
    }

    // Basically the g(n) + f(n)
    // g(n) is the cost from source to current
    // f(n) is the heuristic function that calculated according to current to destination
    @Override
    public int compare(GraphNode a, GraphNode b){
        int fA = a.getDepth();
        int gA = wordDifference(a.word, destination);
        int hA = fA + gA;

        int fB = b.getDepth();
        int gB = wordDifference(b.word, destination);
        int hB = fB + gB;

        return hA - hB; // smaller difference is better
    }
}