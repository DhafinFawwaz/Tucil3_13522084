package Solver;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.TreeMap;

public class GraphNode extends TreeMap<String, ArrayList<String>> {
    public GraphNode(){
        super();
    }

    public static ArrayList<GraphNode> createListFromBinaryFile(String preProcessResultPath){
        try {
            FileInputStream fileInputStream = new FileInputStream(preProcessResultPath);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream);
            
            @SuppressWarnings("unchecked")
            ArrayList<GraphNode> currentMap = (ArrayList<GraphNode>) objectInputStream.readObject();
            fileInputStream.close();
            return currentMap;
        } catch (FileNotFoundException e) {
            System.out.println("./src/Asset/dictionary.bin not found");
            System.out.println("Please run PreProcess first to generate dictionary.bin file.");
            System.out.println("You can copy the following commands to the terminal to run PreProcess:");
            System.out.println(">> ./compilepreprocess.bat");
            System.out.println(">> ./runpreprocess.bat");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("Format file error");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    public static void main(String[] args) {
    }
}
