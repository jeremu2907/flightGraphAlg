public class NodeCityList extends LinkedList<String>{
    String city;        //Origin City
    NodeCityList next;  //Points to the next city

    public NodeCityList(String city){
        this.city = city;
        this.next = null;
    }

    public String toString(){
        return this.city.concat(" -> ".concat(super.listToString()));
    }

    //Add to list of flights
    public int addHead(String s, int c, int m){
        Node<String> e = new Node<>(s,c,m);
        e.next = this.head;
        this.head = e;
        return 0;
    }
}