import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

//For String matching when reading file
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {
    public static void main(String[] args){
        Graph graph = new Graph();

        //Reading flight paths from file
        try {
            File myObj = new File("route.txt");
            Scanner myReader = new Scanner(myObj);
            Pattern r = Pattern.compile("([A-Z][a-z]*)\\|([A-Z][a-z]*)\\|(\\d+)\\|(\\d+)");

            int numEntries = Integer.valueOf(myReader.nextLine()); //Reads the top number

            for(int i = 1; i <= numEntries; i++) {
                String data = myReader.nextLine();
                Matcher m = r.matcher(data);
                if(m.find()){
                    String origin = m.group(1);
                    String destination = m.group(2);
                    int cost = Integer.valueOf(m.group(3));
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
                    System.out.println("something wrong");
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        System.out.println(graph);
        // graph.dfs("Houston", "Chicago", 'C');


        // Reading requests from file
        try {
            File myObj = new File("request.txt");
            Scanner myReader = new Scanner(myObj);
            Pattern r = Pattern.compile("([A-Z][a-z]+)\\|([A-Z][a-z]+)\\|(T|C)");

            int numEntries = Integer.valueOf(myReader.nextLine()); //Reads the top number

            for(int i = 1; i <= numEntries; i++) {
              String data = myReader.nextLine();
              Matcher m = r.matcher(data);
              if(m.find()){
                System.out.printf("Flight %d: %s, %s (%s)\n",i, m.group(1), m.group(2), (m.group(3).charAt(0) == 'C')? "Cost":"Time");
                // graph.path(m.group(1),m.group(2),m.group(3).charAt(0));
                graph.dfs(m.group(1),m.group(2),m.group(3).charAt(0));
                System.out.println("\n");
              }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
}
