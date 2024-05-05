package Solver;

public class SolutionException extends Exception{
    double duration;
    public SolutionException(double duration){
        super("No solution found.");
        this.duration = duration;
    }

    public double getDuration(){
        return duration;
    }
}
