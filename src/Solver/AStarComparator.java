package Solver;

public class AStarComparator extends GBFSComparator {
    public AStarComparator(String source, String destination){
        super(source, destination);
    }

    @Override
    public int compare(GraphNode a, GraphNode b){
        int diff = super.compare(a, b);
        return diff + a.compareTo(b) ; // smaller difference is better
    }
}