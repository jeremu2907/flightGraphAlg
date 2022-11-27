

public class Main {
    public static void main(String[] args){
        LinkedList<NodeCityList> graph = new LinkedList<>();
        NodeCityList firstCity = new NodeCityList("Dallas");

        firstCity.addHead("Garland");
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
