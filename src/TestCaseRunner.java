import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import Solver.AStarSolver;
import Solver.GBFSSolver;
import Solver.GraphAdjacencyMap;
import Solver.SolutionData;
import Solver.SolutionException;
import Solver.UCSSolver;
import Solver.WordLadderSolver;

public class TestCaseRunner {
    public static void solveAndPrint(WordLadderSolver solver) throws Exception {
        SolutionData data;
        try{
            data = solver.Solve();
        } catch (SolutionException e) {
            System.out.println(e.getMessage());
            System.out.println("Time taken: " + e.getDuration() + " ms");
            return;
        }

        int i = 1;
        for (String word : data.getSolution()) {
            System.out.print(i);
            if(i < 10) System.out.print(".   ");
            else if(i < 100) System.out.print(".  ");
            else System.out.print(". ");
            System.out.println(word);
            i++;
        }
        System.out.println("Nodes visited: " + data.getNodesVisited());
        System.out.println("Time taken: " + data.getDuration() + " ms");
    }

    public static void solve(int currentTestCase, String source, String destination, ArrayList<GraphAdjacencyMap> graphList){
        System.out.println("\n\n============== [ Test case " + currentTestCase + " | " + source + " -> " + destination + " ] ==============");
        try {
            System.out.println("\nAlgorithm: Uniform Cost Search");
            UCSSolver ucs = new UCSSolver(source, destination, graphList);
            solveAndPrint(ucs);

            System.out.println("\nAlgorithm: Greedy Best First Search");
            GBFSSolver bgfs = new GBFSSolver(source, destination, graphList);
            solveAndPrint(bgfs);
            
            System.out.println("\nAlgorithm: A* Search");
            AStarSolver astar = new AStarSolver(source, destination, graphList);
            solveAndPrint(astar);
 
        } catch (Exception e) {
            System.out.println("\u001B[31mError in test case " + currentTestCase);
            System.out.print(e.getMessage());
            System.out.print("\u001B[0m\n");
        }
    }
    public static void main(String[] args) {
        String testCasePath;
        String dictionaryPath;
        if(args.length < 1){ // default test case because the debugger is bugged when passing arguments
            testCasePath = "test/input.txt";
            dictionaryPath = "src/Asset/dictionary.bin";
        } else if(args.length < 2) {
            testCasePath = args[0];
            dictionaryPath = "src/Asset/dictionary.bin";
        } else {
            testCasePath = args[0];
            dictionaryPath = args[1];
        }

        ArrayList<GraphAdjacencyMap> graphList = GraphAdjacencyMap.createListFromBinaryFile(dictionaryPath);
        
        try {
            File file = new File(testCasePath);
            Scanner scanner = new Scanner(file);
            int currentLine = 1;
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                String[] splitted = data.split("\\s+");
                if(splitted.length != 2){
                    System.out.println("\u001B[31m\nInvalid test case format in\nline: " + currentLine + "\ntext: " + data);
                    System.out.println("Test case format should be:\n<source_word> <destination_word>\n<source_word> <destination_word>\n<source_word> <destination_word>\n...\nWith a space between the two words and same length\u001B[0m");
                }
                else solve(currentLine, splitted[0].toLowerCase(), splitted[1].toLowerCase(), graphList);
                currentLine++;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }
}
