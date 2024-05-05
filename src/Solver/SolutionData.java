package Solver;

import java.util.ArrayList;
import java.util.LinkedList;

public class SolutionData {
    long startTime = 0;
    long duration = 0;
    LinkedList<String> solution;

    public SolutionData startTimer(){
        startTime = System.nanoTime();
        return this;
    }

    public SolutionData endTimer(){
        long endTime = System.nanoTime();
        duration = (endTime - startTime);
        return this;
    }

    public SolutionData setSolution(LinkedList<String> solution){
        this.solution = solution;
        return this;
    }

    public LinkedList<String> getSolution(){
        return solution;
    }
    public double getDuration(){
        double temp = (double)duration/1000000.0;
        if(temp < 0.0001) return 0; // because the formating is hard to read for very small numbers
        return temp;
    }

    public void Print(){
        System.out.println("Time taken: " + duration /1000000 + " ms");
    }
}
