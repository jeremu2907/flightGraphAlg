//Name: Jeremy Nguyen
//CS 3345
//Project Flight Path Graph
//Find the three shortest paths from origin to destination

//Implemented using modified dfs with iterative back tracking
//See Graph.java for more

//Self written LinkedList and Stack, see each file for more detail

//Implement OOP principles: inheritance, polymorphism, abstraction, and encapsulation

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.Scanner; // Import the Scanner class to read text files

//For String matching when reading file
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors


public class Main {
    public static void main(String[] args){
        //If no param, default
        if(args.length == 0){
            args = new String[2];
            args[0] = "route.txt";
            args[1] = "request.txt";
        }

        try {
            FileWriter myWriter = new FileWriter("output.txt");
            Graph graph = new Graph();

            //Reading flight paths from file
            try {
                File myObj = new File(args[0]);
                Scanner myReader = new Scanner(myObj);
                Pattern r = Pattern.compile("([A-Z][a-z]*)\\|([A-Z][a-z]*)\\|(\\d+)\\|(\\d+.*\\d*\\d*)");

                int numEntries = Integer.valueOf(myReader.nextLine()); //Reads the top number

                for(int i = 1; i <= numEntries; i++) {
                    String data = myReader.nextLine();
                    Matcher m = r.matcher(data);
                    if(m.find()){
                        String origin = m.group(1);
                        String destination = m.group(2);
                        // float cost = float.valueOf(m.group(3));
                        Float cost = Float.parseFloat(m.group(3));
                        int minutes = Integer.valueOf(m.group(4));

                        // If origin exists then add new destination to head
                        // Else create a new origin and then add new destination to head
                        NodeCityList graphNode = graph.findOrigin(origin);
                        if(graphNode == null){
                            NodeCityList tempOrigin = new NodeCityList(origin);
                            tempOrigin.addHead(destination, cost, minutes);
                            graph.addOrigin(tempOrigin);
                        } else {
                            graphNode.addHead(destination, cost, minutes);
                        }

                        graphNode = graph.findOrigin(destination);
                        if(graphNode == null){
                            NodeCityList tempOrigin = new NodeCityList(destination);
                            tempOrigin.addHead(origin, cost, minutes);
                            graph.addOrigin(tempOrigin);
                        } else {
                            graphNode.addHead(origin, cost, minutes);
                        }
                    } else {
                        System.out.println("File format is wrong");
                        return;
                    }
                }
                myReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

            // Reading requests from file
            try {
                File myObj = new File(args[1]);
                Scanner myReader = new Scanner(myObj);
                Pattern r = Pattern.compile("([A-Z][a-z]*)\\|([A-Z][a-z]*)\\|(T|C)");

                int numEntries = Integer.valueOf(myReader.nextLine()); //Reads the top number

                for(int i = 1; i <= numEntries; i++) {
                    String data = myReader.nextLine();
                    Matcher m = r.matcher(data);
                    if(m.find()){
                        // System.out.printf("Flight %d: %s, %s (%s)\n",i, m.group(1), m.group(2), (m.group(3).charAt(0) == 'C')? "Cost":"Time");
                        String flightName = (m.group(3).charAt(0) == 'C') ? "Flight " + i + ": " + m.group(1) + ", " + m.group(2) + " (" + "Cost" + ")\n" : 
                                                                                        "Flight " + i + ": " + m.group(1) + ", " + m.group(2) + " (" + "Time" + ")\n";
                        myWriter.write(flightName);
                        ArrayList<String> write = graph.dfs(m.group(1),m.group(2),m.group(3).charAt(0),3);
                        for(String rw : write){
                            myWriter.write(rw);
                        }
                        myWriter.write("\n");
                    } else {
                        System.out.println("File format is wrong");
                        return;
                    }
                }
                myReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        System.out.println("Successfully wrote to the file. Program done running");
        return;
    }
}
