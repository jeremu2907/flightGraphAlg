import java.util.ArrayList;
import java.util.Collections;

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

    public void dfs(String o, String d, char option){
        StackG<NodeCityList> stack = new StackG<>();
        StackG<String> path = new StackG<>();
        ArrayList<String> listNode = new ArrayList<>();
        ArrayList<ArrayList<String>> pathList = new ArrayList<>();

        NodeCityList temp = this.head;
        while(temp != null){
            listNode.add(temp.city);
            temp = temp.next;
        }
        ArrayList<ArrayList<Boolean>> visitedNode = new ArrayList<ArrayList<Boolean>>();
        ArrayList<ArrayList<String>> listParent = new ArrayList<ArrayList<String>>();
        for(int i = 0; i < listNode.size(); i++){
            visitedNode.add(new ArrayList<Boolean>(Collections.nCopies(listNode.size(),false)));
            listParent.add(new ArrayList<String>());
        }
        // ArrayList<ArrayList<String>> listParent = new ArrayList<ArrayList<String>>(Collections.nCopies(listNode.size(), new ArrayList<String>()));
        // System.out.println(visitedNode);
        stack.push(this.findOrigin(o));

        while(!stack.empty()){
            //t = stack pop
            NodeCityList t = stack.pop();
            
            // System.out.println(listNode);
            // System.out.println("Frisco: "+ visitedNode.get(listNode.indexOf("Frisco")));
            if(!path.empty()){
                //Go to matrix row of the path top then mark the upcoming path top as visited
                // System.out.println("Mark " + t.city + " in " + path.top() + " visited index in list is: " + listNode.indexOf(path.top()));
                visitedNode.get(listNode.indexOf(path.top())).set(listNode.indexOf(t.city), true);
                // System.out.println("Frisco: "+ visitedNode.get(listNode.indexOf("Frisco")));
                // System.out.println(path.top() + visitedNode.get(listNode.indexOf(path.top())));
            }
            // System.out.println(visitedNode);
            path.push(t.city);
            //t visited
            // System.out.println("PATH   " + path);

            //if path top is d
            if(path.top().equals(d)){
                //if path not explored
                ArrayList<String> pathArray = path.toArrayList();
                if(pathList.indexOf(pathArray) == -1){
                    //save path
                    pathList.add(pathArray);
                    // System.out.println("New Path    " + pathArray);
                }
                //path pop
                path.pop();
            } else {
                Node<String> r = findOrigin(path.top()).head;
                while(r != null){
                    if(!visitedNode.get(listNode.indexOf(path.top())).get(listNode.indexOf(r.value)) && listParent.get(listNode.indexOf(path.top())).indexOf(r.value) == -1){
                        stack.push(findOrigin(r.value));
                        //Set set the parent node of the node added to stack
                        listParent.get(listNode.indexOf(r.value)).add(path.top());
                    }
                    // System.out.println(r);
                    r = r.next;
                }
            }
            // System.out.println("STACK  " + stack);

            //pop path
            while(!path.empty()){
                boolean breakloop = false;
                if(findOrigin(path.top()).head == null){
                    path.pop();
                    // System.out.println("No nodes pop");
                    continue;
                }

                else{
                    Node<String> r = findOrigin(path.top()).head;
                    while(r != null){
                        if(listParent.get(listNode.indexOf(path.top())).indexOf(r.value) == -1){
                            if(!visitedNode.get(listNode.indexOf(path.top())).get(listNode.indexOf(r.value))){
                                breakloop = true;
                                // System.out.println(r.value + " not visited, not poping " + path.top());
                                break;
                            }
                        }
                        r = r.next;
                    }
                    if(breakloop){
                        break;
                    }
                    else{
                        r = findOrigin(path.top()).head;
                        while(r != null){
                            // if(listParent.get(listNode.indexOf(path.top())).indexOf(r.value) == -1){
                                visitedNode.get(listNode.indexOf(path.top())).set(listNode.indexOf(r.value), false);
                            // }
                            r = r.next;
                        }
                        // System.out.println("All nodes visited, popping " + path.top());
                        String s = path.pop();
                        if(!path.empty())
                        listParent.get(listNode.indexOf(s)).remove(listParent.get(listNode.indexOf(s)).indexOf(path.top()));
                    }
                }
            }
            //while path not empty
                //if path top has no node pop then consider new path top

                //if all non parent nodes visited
                    //traverse node list and consider non parent nodes
                        //if node has one parent set to unvisted
                        //else do nothing

                        //d to unvisited
                    //path top set to not have visited d
                    //(path pop).remove parent(path.top)
        }

        for(ArrayList<String> i : pathList){
            System.out.println(i);
        }
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
        ArrayList<Integer> timeToNode = new ArrayList<>(Collections.nCopies(this.size(), Integer.MAX_VALUE));
        ArrayList<String> parentNode = new ArrayList<>(Collections.nCopies(this.size(), null));
        NodeCityList temp = this.head;

        //Filling in listNode cities name used for searching later
        //Filling in corresponding initial state of data depending on listNode
        while(temp != null){
            listNode.add(temp.city);
            temp = temp.next;
        }
        costToNode.set(listNode.indexOf(o),0);
        timeToNode.set(listNode.indexOf(o),0);
        parentNode.set(listNode.indexOf(o), null);
        ArrayList<Boolean> visitedNode = new ArrayList<>(Collections.nCopies(listNode.size(),false));

        for(int i = 0; i <= this.size(); i++){
            //Find the closest node to the explored trees
            int leastIDX = (option == 'C')? this.minCost(costToNode,visitedNode) : this.minCost(timeToNode,visitedNode);
            processedNodes.push(listNode.get(leastIDX));
            //Set closest node to visited
            visitedNode.set(leastIDX, true);
            //Get the adjacency list of the closest node and update the least amount of cost to get there
            Node<String> explore = this.findOrigin(listNode.get(leastIDX)).head;
            while(explore != null){
                if(option == 'C'){
                    if(costToNode.get(listNode.indexOf(explore.value)) > explore.cost + costToNode.get(leastIDX)){
                        costToNode.set(listNode.indexOf(explore.value), explore.cost + costToNode.get(leastIDX));
                        timeToNode.set(listNode.indexOf(explore.value), explore.minutes + timeToNode.get(leastIDX));
                        parentNode.set(listNode.indexOf(explore.value), listNode.get(leastIDX));
                    }
                }
        
                else{
                    if(timeToNode.get(listNode.indexOf(explore.value)) > explore.minutes + timeToNode.get(leastIDX)){
                        costToNode.set(listNode.indexOf(explore.value), explore.cost + costToNode.get(leastIDX));
                        timeToNode.set(listNode.indexOf(explore.value), explore.minutes + timeToNode.get(leastIDX));
                        parentNode.set(listNode.indexOf(explore.value), listNode.get(leastIDX));
                    }
                }
                explore = explore.next;
            }
        }

        System.out.println(listNode);
        System.out.println(costToNode);
        System.out.println(timeToNode);
        System.out.println(parentNode);
        // System.out.println("Stack: " + processedNodes);

        StackG<String> printStack = new StackG<>();
        String s = d;
        while(s != null){
            printStack.push(s);
            s = parentNode.get(listNode.indexOf(s));
        }
        while(!printStack.empty()){
            System.out.print(printStack.pop());
            if(!printStack.empty())
                System.out.print(" -> ");
        }
        System.out.printf(". Time: %d Cost: %d\n",timeToNode.get(listNode.indexOf(d)), costToNode.get(listNode.indexOf(d)));
        
        //Finding kth shortest path
        int k = 2;
        int nextshortestl = Integer.MAX_VALUE;

        s = d;  //Start from destination
        for(int i = 0; i < k; i++){
            while(!s.equals(o)){
                Node<String> detour = this.findOrigin(s).head;
                while(detour != null){
                    
                    nextshortestl = (option == 'C')? Integer.min(nextshortestl, detour.cost + costToNode.get(listNode.indexOf(detour.value))):
                                    Integer.min(nextshortestl, detour.minutes + timeToNode.get(listNode.indexOf(detour.value)));
                    System.out.println(s + " " + detour.value + " " + nextshortestl);
               
                    // do{
                        detour = detour.next;
                    // } while (detour != null && parentNode.get(listNode.indexOf(s)).equals(detour.value));
                }

                s = parentNode.get(listNode.indexOf(s));
                // System.out.println(s.equals(o));
                
            }
            if(option == 'C'){
                System.out.println("Next lowest: " + (nextshortestl));
            } else {
                System.out.println("Next lowest: " + (nextshortestl));
            }
        }
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
