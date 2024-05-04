package Solver;

import java.util.ArrayList;


public abstract class WordLadderSolver {
    String source;
    String destination;
    ArrayList<GraphNode> graphList;
    public WordLadderSolver(String source, String destination, ArrayList<GraphNode> graphList) {
        if(source.length() != destination.length()){
            throw new IllegalArgumentException("Source and Destination must have the same length");
        }
        this.source = source;
        this.destination = destination;
        this.graphList = graphList;
    }

    public SolutionData Solve() throws Exception {
        if (source.isEmpty() || destination.isEmpty()) {
            throw new IllegalArgumentException("Please fill in both fields");
        }
        if(source.length() != destination.length()) {
            throw new IllegalArgumentException("Source and destination must have the same length");
        }
        return new SolutionData().startTimer();
    }
}
