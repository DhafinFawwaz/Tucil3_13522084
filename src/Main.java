

import java.util.ArrayList;

import Component.Fonts;
import Component.MainWindow;
import Solver.GraphAdjacencyMap;

class Main{
    public static void main(String[] args){
        ArrayList<GraphAdjacencyMap> graphList = GraphAdjacencyMap.createListFromBinaryFile("src/Asset/dictionary.bin");
        Fonts.LoadFont();        
        MainWindow window = new MainWindow(graphList);
    }
}