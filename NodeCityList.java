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
}