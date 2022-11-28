public class Graph{
    NodeCityList head;

    public Graph(){
        this.head = null;
    }

    public boolean addOrigin(NodeCityList e){
        e.next = this.head;
        this.head = e;
        return true;
    }

    //Function returns Origin if found
    public NodeCityList findOrigin(String s){
        NodeCityList temp = this.head;
        while(true){
            if(temp != null){
                if(temp.city.equals(s))
                    return temp;
            } else {
                return null;
            }

            temp = temp.next;
        }
    }

    public String toString(){
        String t = "";
        NodeCityList r = this.head;
        while(r != null){
            t = t + r.toString() + "\n";
            r = r.next;
        }
        return t;
    }

    // public static void main(String[] args){
    //     Graph graph = new Graph();
    //     NodeCityList firstCity = new NodeCityList("Dallas");

    //     firstCity.addHead("Garland",23,13);
    //     firstCity.addHead("Fort Worth",44,23);
    //     firstCity.addHead("Arlington",44,77);
    //     firstCity.addHead("Houston",44,77);

    //     graph.addOrigin(firstCity);

    //     NodeCityList secondCity = new NodeCityList("Houston");
    //     secondCity.addHead("Katy",77,99);
    //     secondCity.addHead("Dallas",15,99);
    //     secondCity.addHead("New Orleans", 123,55543);

    //     graph.addOrigin(secondCity);

    //     graph.findOrigin("Dallas").addHead("New York",90,90);
        
    //     System.out.println(graph.findOrigin("Dallas"));
    // }
}
