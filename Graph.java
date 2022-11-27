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
                if(temp.city == s)
                    return temp;
            } else {
                return null;
            }

            temp = temp.next;
        }
    }
}
