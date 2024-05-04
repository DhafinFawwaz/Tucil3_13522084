

import java.util.ArrayList;

import Component.Fonts;
import Component.MainWindow;
import Solver.GraphNode;

class Main{
    public static void main(String[] args){
        ArrayList<GraphNode> graphList = GraphNode.createListFromBinaryFile("src/Asset/dictionary.bin");
        Fonts.LoadFont();        
        MainWindow window = new MainWindow(graphList);
    }
}