import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.util.stream.IntStream;

import Solver.GraphNode;

import java.util.ArrayList;
import java.util.Map;


public class PreProcess {

    // True if a and b have only one different character
    public static boolean isDifferenceOne(String a, String b){
        int diff = 0;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                diff++;
                if(diff > 1) return false;
            }
        }
        return diff == 1;
    }


    // Human readable version, serialize as bytes for faster reading
    // Write preProcessData to file as bytes, each in different file. 1.txt, 2.txt, 3.txt, ...
    public static void writeMultipleHumanReadable(ArrayList<GraphNode> preProcessData, String preProcessResultPath){
        for(int i = 0; i < preProcessData.size(); i++){
            System.out.println("Processing length " + i);
            if(i > 5) break; // remove this later

            try {
                File file = new File(preProcessResultPath + "/" + i + ".txt");
                file.createNewFile();
                BufferedWriter scanner = new BufferedWriter(new FileWriter(file));
                for(Map.Entry<String, ArrayList<String>> entry : preProcessData.get(i).entrySet()){
                    String word = entry.getKey();
                    ArrayList<String> connectedWords = entry.getValue();
                    String connectedWordsString = "";
                    for(String connectedWord : connectedWords){
                        connectedWordsString += connectedWord + " ";
                    }
                    connectedWordsString = connectedWordsString.trim();
                    scanner.write(word + ": " + connectedWordsString + "\n");
                }
                scanner.close();
            } catch (Exception e) {
                System.out.println("File not found");
                e.printStackTrace();
            }
        }
    }


    // Non human readable version, serialize as bytes for faster reading
    // Write preProcessData to file as bytes, each in different file. 1.bin, 2.bin, 3.bin, ...
    public static void writeMultipleInBytes(ArrayList<GraphNode> preProcessData, String preProcessResultPath){
        for(int i = 0; i < preProcessData.size(); i++){
            System.out.println("Processing length " + i);

            try {
                File file = new File(preProcessResultPath + "/" + i + ".bin");
                file.createNewFile();

                GraphNode currentMap = preProcessData.get(i);

                // Write currentMap to file as bytes with serialization
                FileOutputStream fileOutputStream = new FileOutputStream(file, false);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(currentMap);
                objectOutputStream.close();
            } catch (Exception e) {
                System.out.println("File not found");
                e.printStackTrace();
            }
        }
    }


    // Non human readable version, serialize as bytes for faster reading
    // Write preProcessData to file as bytes, in 1 big file
    public static void writeInBytes(ArrayList<GraphNode> preProcessData, String preProcessResultPath){
        try {
            File file = new File(preProcessResultPath);
            file.createNewFile();

            // Write preProcessData to file as bytes with serialization
            FileOutputStream fileOutputStream = new FileOutputStream(file, false);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream);
            objectOutputStream.writeObject(preProcessData);
            objectOutputStream.close();
        } catch (Exception e) {
            System.out.println("Writing file failed.");
            e.printStackTrace();
        }
    }


    // Print content of preProcessData by length and number of words with that length
    public static void printContent(ArrayList<GraphNode> preProcessData){
        System.out.println("Dictionary Content:");
        for(int i = 0; i < preProcessData.size(); i++){
            System.out.println("Length " + i + " :" + preProcessData.get(i).size());
        }
    }


    // Read dictionary file
    // It populates the keys of preProcessData
    public static void populateKeysFromFile(ArrayList<GraphNode> preProcessData, String dictionaryPath){
        try {
            File file = new File(dictionaryPath);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                int length = data.length();

                // Resize the arraylist when found bigger word
                if (length >= preProcessData.size()) {
                    for (int i = preProcessData.size(); i <= length; i++) {
                        preProcessData.add(new GraphNode());
                    }
                }

                // Add word to the list of words with the same length
                preProcessData.get(length).put(data.toLowerCase(), null);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }
    
    // For each word, connect to all words with length difference 1
    // It populates the values of preProcessData
    public static void populateGraph(ArrayList<GraphNode> preProcessData){
        long startTime = System.currentTimeMillis();
        // For each length
        for(int i = 0; i < preProcessData.size(); i++){
            System.out.println("Processing length " + i);

            // For each key in certain length
            var entrySet = preProcessData.get(i).entrySet();
            for(Map.Entry<String, ArrayList<String>> entry : entrySet){
                String word = entry.getKey();
                ArrayList<String> connectedWords = new ArrayList<String>();

                // Loop through current map's keys
                for(Map.Entry<String, ArrayList<String>> currentEntry : entrySet){
                    String currentWord = currentEntry.getKey();
                    if(isDifferenceOne(word, currentWord)){
                        connectedWords.add(currentWord);
                    }
                }

                entry.setValue(connectedWords);
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Time elapsed: " + (endTime - startTime) + " ms");
    }

    // For each word, connect to all words with length difference 1
    // It populates the values of preProcessData with multithreading
    public static void populateGraphMultithreded(ArrayList<GraphNode> preProcessData){
        long startTime = System.currentTimeMillis();
        System.out.println("Please wait, this is a multithreaded process...");
        IntStream.range(0, preProcessData.size()).parallel().forEach(i -> {
            System.out.println("Processing length " + i);

            // For each key in certain length
            var entrySet = preProcessData.get(i).entrySet();
            for(Map.Entry<String, ArrayList<String>> entry : entrySet){
                String word = entry.getKey();
                ArrayList<String> connectedWords = new ArrayList<String>();

                // Loop through current map's keys
                for(Map.Entry<String, ArrayList<String>> currentEntry : entrySet){
                    String currentWord = currentEntry.getKey();
                    if(isDifferenceOne(word, currentWord)){
                        connectedWords.add(currentWord);
                    }
                }

                entry.setValue(connectedWords);
            }
        });
        long endTime = System.currentTimeMillis();
        System.out.println("Time elapsed: " + (endTime - startTime) + " ms");
    }
    
    public static void PreprocessDictionary(String dictionaryPath, String preProcessResultPath){
        // index represents length of word
        ArrayList<GraphNode> preProcessData = new ArrayList<GraphNode>();
        populateKeysFromFile(preProcessData, dictionaryPath);
        printContent(preProcessData);
        System.out.println("Done reading dictionary. Creating graph...");

        // Create graph
        populateGraphMultithreded(preProcessData);
        System.out.println("Done creating graph. Writing to file...");

        // Write to file
        writeInBytes(preProcessData, preProcessResultPath);
        System.out.println("Done writing to file.");
    }

    // read 1.bin, 2.bin, 3.bin, ... and print the content
    public static void CheckPrerocessMultiple(String preProcessResultPath){
        long startTime = System.currentTimeMillis();
        // Read the content inside preprocess
        int i = 0;
        while(true){
            try {
                FileInputStream fileInputStream = new FileInputStream(preProcessResultPath + "/" + i + ".bin");
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                GraphNode currentMap = (GraphNode) objectInputStream.readObject();
                
                System.out.println("Length " + i + " :" + currentMap.size());
                fileInputStream.close();
            } catch (FileNotFoundException e) {
                long endTime = System.currentTimeMillis();
                System.out.println("File " + i + ".bin not found. Finish checking preprocess result.");
                System.out.println("Time elapsed: " + (endTime - startTime) + " ms");
                return;
            } catch (IOException e) {
                System.out.println("Format file error");
                e.printStackTrace();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }

            i++;
        }
    }
    
    public static void CheckPreprocess(String preProcessResultPath){
        System.out.println("Checking preprocess result...");
        long startTime = System.currentTimeMillis();
        ArrayList<GraphNode> currentMap = GraphNode.createListFromBinaryFile(preProcessResultPath);
        printContent(currentMap);
        long endTime = System.currentTimeMillis();
        System.out.println("Time elapsed: " + (endTime - startTime) + " ms");
    }
    
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java PreProcess <flag> <dictionaryPath> <preProcessResultPath>");
            return;
        }
        if(args[0].equals("-p")){
            PreprocessDictionary(args[1], args[2]);
            return;
        }
        if(args[0].equals("-c")){
            CheckPreprocess(args[1]);
            return;
        }
    }
}
