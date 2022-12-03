import java.util.ArrayList;
import java.util.Arrays;
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
        ArrayList<ArrayList<Boolean>> visitedNode = new ArrayList<ArrayList<Boolean>>(); //Row is parent, columns are visted child nodes
        ArrayList<ArrayList<String>> listParent = new ArrayList<ArrayList<String>>();   //Row is node, collumn list are parents of node
        for(int i = 0; i < listNode.size(); i++){
            visitedNode.add(new ArrayList<Boolean>(Collections.nCopies(listNode.size(),false)));
            listParent.add(new ArrayList<String>());
        }
        ArrayList<Integer[]> weight = new ArrayList<>();            //<cost,time>
        int cost = 0;
        int time = 0;
        stack.push(this.findOrigin(o));

        while(!stack.empty()){
            NodeCityList t = stack.pop();   //Hold value of stack for operation purposes
            if(!path.empty()){              //Set the parent child's node to visited
                visitedNode.get(listNode.indexOf(path.top())).set(listNode.indexOf(t.city), true);
                Node<String> tempOrigin = findOrigin(path.top()).find(t.city,0);
                cost += tempOrigin.cost;
                time += tempOrigin.minutes;
            }
            path.push(t.city);

            //if path top is the destination
            if(path.top().equals(d)){
                ArrayList<String> pathArray = path.toArrayList();
                if(pathList.indexOf(pathArray) == -1){
                    pathList.add(pathArray);
                    weight.add(new Integer[]{cost,time});
                }
                String s = path.pop();                 //Destination is poped the moment the path is processed
                if(!path.empty()){
                    Node<String> tempOrigin = findOrigin(path.top()).find(s,0);
                    cost -= tempOrigin.cost;
                    time -= tempOrigin.minutes;
                }
            } else {
                Node<String> r = findOrigin(path.top()).head;
                while(r != null){
                    //Only consider unvisted child nodes
                    if(!visitedNode.get(listNode.indexOf(path.top())).get(listNode.indexOf(r.value)) && 
                    listParent.get(listNode.indexOf(path.top())).indexOf(r.value) == -1){
                        stack.push(findOrigin(r.value));
                        //Path top is the parent node of the current r node
                        listParent.get(listNode.indexOf(r.value)).add(path.top());
                    }
                    r = r.next;
                }
            }

            //this part back tracks until a node has unvisted non-parent adjacent nodes
            while(!path.empty()){
                boolean breakloop = false;
                if(findOrigin(path.top()).head == null){    //No adjacent nodes
                    path.pop();
                    continue;
                }

                else{
                    Node<String> r = findOrigin(path.top()).head;
                    while(r != null){
                        if(listParent.get(listNode.indexOf(path.top())).indexOf(r.value) == -1){
                            if(!visitedNode.get(listNode.indexOf(path.top())).get(listNode.indexOf(r.value))){
                                breakloop = true;
                                break;
                            }
                        }
                        r = r.next;
                    }
                    if(breakloop){
                        break;
                    }
                    else{                                   //All nonparent adjacent nodes visited
                        r = findOrigin(path.top()).head;
                        while(r != null){
                            visitedNode.get(listNode.indexOf(path.top())).set(listNode.indexOf(r.value), false);
                            r = r.next;
                        }
                        String s = path.pop();
                        if(!path.empty()){
                            listParent.get(listNode.indexOf(s)).remove(listParent.get(listNode.indexOf(s)).indexOf(path.top()));
                            Node<String> tempOrigin = findOrigin(path.top()).find(s,0);
                            cost -= tempOrigin.cost;
                            time -= tempOrigin.minutes;
                        }
                    }
                }
            }
        }

        topThreePaths(pathList, weight, option);
    }

    private void topThreePaths(ArrayList<ArrayList<String>> paths, ArrayList<Integer[]> weight, char option){
        if(paths.size() == 0){
            System.out.println("No flight available");
            return;
        }

        int thres = 0;
        int op = (option == 'C')? 0 : 1;
        int iter = (paths.size() >= 3)? 3 : paths.size();
        for(int i = 0; i < iter; i++){
            int idx = 0;
            int min = Integer.MAX_VALUE;
            int j = 0;
            for(;j < weight.size(); j++){
                if(weight.get(j)[op] < min && weight.get(j)[op] > thres){
                    min = weight.get(j)[op];
                    idx = j;
                } 
            }
            thres = min;
            System.out.print("Path " + i + ": " + toPath(paths.get(idx)) + "   Time: " + weight.get(idx)[1] + "  Cost: " + weight.get(idx)[0] + "\n");
        }
    }

    private String toPath(ArrayList<String> path){
        String r = "";
        for(String i : path){
            if(!r.equals("")){
                r += "->";
            }
            r += " " + i + " ";
        }
        return r;
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
