import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files


public class Main {
    public static void main(String[] args){
        LinkedList<NodeCityList> graph = new LinkedList<>();
        NodeCityList firstCity = new NodeCityList("Dallas");

        firstCity.addHead("Garland",23,13);
        firstCity.addHead("Fort Worth");
        firstCity.addHead("Arlington");

        graph.addHead(firstCity);

        NodeCityList secondCity = new NodeCityList("Houston");
        secondCity.addHead("Katy");
        secondCity.addHead("Dallas");

        graph.addHead(secondCity);

        System.out.println(graph);
    }
}
