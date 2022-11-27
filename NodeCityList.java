//List cities, a linked list for NodePath

// public class NodeCityList {
//     public String city;      //This is the city pointing to heads
//     public NodePath head;    //This is the adjacency list, head is the out node
//     public NodeCityList next;

//     public NodeCityList(String city){
//         this.city = city;
//         this.next = null;
//         this.head = null;
//     }

//     //Add to head of the path
//     public int addHead(NodePath e){
//         e.next = this.head;
//         this.head = e;
//         return 0;
//     }

//     //Find if a city is in the list
//     //Option: 0 for the node, 1 for the node that points to it
//     public NodePath find(String e,int option){
//         NodePath t = this.head;
//         if(t == null)
//             return null;

//         while(true){
//             if(t.city == e && option == 0)
//                 return t;   //Found when request for the node itself
            
//             if(t.next != null){
//                 if(t.next.city == e && option == 1)
//                     return t;   //Found when require node before the target node
//                 t = t.next;
//             }
//             else
//                 break;
//         }

//         return null;    //Not found
//     }

//     public int remove(String e){
//         NodePath t = this.find(e,1);
//         if(t == null)
//             return -1;  //Not found no removal
//         else{
//             t.next = t.next.next;
//         }

//         return 0;
//     }

//     //List in string form
//     public String listToString(){
//         String t = "";
//         NodePath tempNode = this.head;
//         while(true){
//             if(tempNode != null){
//                 t = t.concat(tempNode.toString());
//                 t = t.concat(" | ");
//             } else {
//                 break;
//             }
//             tempNode = tempNode.next;
//         }

//         return t;
//     }

//     public String toString(){
//         return this.city.concat(" -> ".concat(this.listToString()));
//     }
// }

public class NodeCityList extends LinkedList<String>{
    String city;
    // public LinkedList<String> pathList; //This is the adjacency list, head is the out node

    public NodeCityList(String city){
        this.city = city;
        // pathList = new LinkedList<>();
    }

    public String toString(){
        return this.city.concat(" -> ".concat(super.listToString()));
    }
}