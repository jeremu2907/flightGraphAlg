import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files


public class Main {
    public static void main(String[] args){
        Graph graph = new Graph();
        NodeCityList firstCity = new NodeCityList("Dallas");

        firstCity.addHead("Garland",23,13);
        firstCity.addHead("Fort Worth",44,23);
        firstCity.addHead("Arlington",44,77);
        firstCity.addHead("Houston",44,77);

        graph.addOrigin(firstCity);

        NodeCityList secondCity = new NodeCityList("Houston");
        secondCity.addHead("Katy",77,99);
        secondCity.addHead("Dallas",15,99);
        secondCity.addHead("New Orleans", 123,55543);

        graph.addOrigin(secondCity);
        
        System.out.println(graph.findOrigin("Houston").find("Katy",0));
    }

    // public static void readFile(){
    //     try {
    //         File myObj = new File("route.txt");
    //         Scanner myReader = new Scanner(myObj);
    //         while (myReader.hasNextLine()) {
    //           String data = myReader.nextLine();
    //           System.out.println(data);
    //         }
    //         myReader.close();
    //       } catch (FileNotFoundException e) {
    //         System.out.println("An error occurred.");
    //         e.printStackTrace();
    //       }
    // }
}
