

import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Dialog;
import java.awt.FileDialog;
import java.io.File;

import Component.Fonts;
import Component.MainWindow;
import Solver.GraphAdjacencyMap;
import Component.MainWindow;

class Main{
    public static void main(String[] args){
        String dictionaryPath;

        Fonts.LoadFont();        
        MainWindow window = new MainWindow();

        FileDialog fileDialog = new FileDialog((Dialog)null, "Select Dictionary File", FileDialog.LOAD);
        fileDialog.setFile("*.bin");
        File workingDirectory = new File(System.getProperty("user.dir"));
        String path = workingDirectory.getAbsolutePath() + "\\src\\Asset";
        fileDialog.setDirectory(path);
        fileDialog.setVisible(true);
        
        String dir = fileDialog.getDirectory();
        String file = fileDialog.getFile();
        dictionaryPath = dir + file;

        if(dir == null || file == null){ 
            dictionaryPath = "src/Asset/dictionary.bin";
            window.popUp("Dictionary Path", "Dictionary defaults to " + dictionaryPath);
        } else {
            window.popUp("Dictionary Path", "Dictionary set to "+dictionaryPath);
        }
        
        ArrayList<GraphAdjacencyMap> graphList = GraphAdjacencyMap.createListFromBinaryFile(dictionaryPath);
        window.setGraphList(graphList);
    }
}