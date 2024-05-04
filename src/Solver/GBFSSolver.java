package Solver;

import java.util.ArrayList;
// import java.util.PriorityQueue;

public class GBFSSolver extends WordLadderSolver {
    public GBFSSolver(String source, String destination, ArrayList<GraphNode> graphList) {
        super(source, destination, graphList);
    }

    @Override
    public SolutionData Solve() throws Exception {
        SolutionData solution = super.Solve();
        ArrayList<String> resultPath = new ArrayList<String>();

        // Algorithm starts here
        resultPath.add(source);
        resultPath.add(destination);


        return solution.setSolution(resultPath).endTimer();
    }
}
