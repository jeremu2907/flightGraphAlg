import java.util.ArrayList;
import java.util.Collections;

import org.w3c.dom.css.ElementCSSInlineStyle;

public class Graph{
    NodeCityList head;
    private Integer size;

    public Graph(){
        this.head = null;
        this.size = 0;
    }

    public boolean addOrigin(NodeCityList e){
        e.next = this.head;
        this.head = e;
        size++;
        return true;
    }

    public int size(){
        return this.size;
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

    private int minCost(ArrayList<Integer> costList, ArrayList<Boolean> visited){
        int min = Integer.MAX_VALUE;
        int idx = 0;

        for(int i = 0; i < costList.size(); i++){
            if(costList.get(i) < min && visited.get(i) == false){
                idx = i;
                min = costList.get(i);
            }
        }
        return idx;
    }


    public void path(String o, String d, char option){
        StackG<String> processedNodes = new StackG<>();
        ArrayList<String> listNode = new ArrayList<>();
        ArrayList<Integer> costToNode = new ArrayList<>(Collections.nCopies(this.size(), Integer.MAX_VALUE));
        ArrayList<String> parentNode = new ArrayList<>(Collections.nCopies(this.size(), null));
        NodeCityList temp = this.head;

        //Filling in listNode cities name used for searching later
        //Filling in corresponding initial state of data depending on listNode
        while(temp != null){
            listNode.add(temp.city);
            temp = temp.next;
        }
        costToNode.set(listNode.indexOf(o),0);
        parentNode.set(listNode.indexOf(o), o);
        ArrayList<Boolean> visitedNode = new ArrayList<>(Collections.nCopies(listNode.size(),false));

        for(int i = 0; i <= this.size(); i++){
            //Find the closest node to the explored trees
            int leastIDX = this.minCost(costToNode,visitedNode);
            processedNodes.push(listNode.get(leastIDX));
            //Set closest node to visited
            visitedNode.set(leastIDX, true);
            //Get the adjacency list of the closest node and update the least amount of cost to get there
            Node<String> explore = this.findOrigin(listNode.get(leastIDX)).head;
            while(explore != null){
                if(option == 'C'){
                    if(costToNode.get(listNode.indexOf(explore.value)) > explore.cost + costToNode.get(leastIDX)){
                        costToNode.set(listNode.indexOf(explore.value), explore.cost + costToNode.get(leastIDX));
                        parentNode.set(listNode.indexOf(explore.value), listNode.get(leastIDX));
                    }
                }
        
                else{
                    if(costToNode.get(listNode.indexOf(explore.value)) > explore.minutes + costToNode.get(leastIDX)){
                        costToNode.set(listNode.indexOf(explore.value), explore.minutes + costToNode.get(leastIDX));
                        parentNode.set(listNode.indexOf(explore.value), listNode.get(leastIDX));
                    }
                }
                explore = explore.next;
            }
        }

        System.out.println(listNode);
        System.out.println(costToNode);
        System.out.println(parentNode);
        System.out.println("Stack: " + processedNodes);
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
