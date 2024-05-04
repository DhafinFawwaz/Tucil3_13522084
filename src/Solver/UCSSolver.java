package Solver;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class UCSSolver extends WordLadderSolver {
    public UCSSolver(String source, String destination, ArrayList<GraphNode> graphList) {
        super(source, destination, graphList);
    }

    void populateSolutionResultPath(GraphNode graph, ArrayList<String> resultPath){
        resultPath.add(source);

        PriorityQueue<String> queue = new PriorityQueue<String>();
        queue.add(source);
        while(queue.size() > 0){
            String currentWord = queue.poll();
            if(currentWord.equals(destination)){
                break;
            }
            ArrayList<String> neighbors = graph.get(currentWord);
            for(String neighbor : neighbors){
                if(neighbor == destination) break; // no need to use .equals because the when inserting to the graph, the String is the same object

                if(!resultPath.contains(neighbor)){
                    resultPath.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        resultPath.add(destination);
    }

    @Override
    public SolutionData Solve() throws Exception {
        SolutionData solution = super.Solve();
        ArrayList<String> resultPath = new ArrayList<String>();

        // Algorithm starts here
        GraphNode graph = graphList.get(source.length()); assert source.length() == destination.length(): "Source and destination should be the same length here because the exception part is already done before this";
        populateSolutionResultPath(graph, resultPath);

        return solution.setSolution(resultPath).endTimer();
    }
}
