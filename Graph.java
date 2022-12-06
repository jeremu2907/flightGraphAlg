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

    public void dfs(String o, String d, char option, int n){
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
            if(t != null)
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
                    listParent.get(listNode.indexOf(r.value)).addAll(listParent.get(listNode.indexOf(path.top())));
                    //Only consider unvisted child nodes
                    if(!visitedNode.get(listNode.indexOf(path.top())).get(listNode.indexOf(r.value)) && 
                    listParent.get(listNode.indexOf(path.top())).indexOf(r.value) == -1){
                        stack.push(findOrigin(r.value));
                        //Path top is the parent node of the current r node
                        listParent.get(listNode.indexOf(r.value)).add(path.top());
                        // System.out.println(path.top() + " to " + r.value + " is added to stack");
                    }
                    // else{
                    //     if(listParent.get(listNode.indexOf(path.top())).indexOf(r.value) != -1)
                    //         System.out.println(r.value + " is parent of " + path.top());
                    //     if(visitedNode.get(listNode.indexOf(path.top())).get(listNode.indexOf(r.value)))
                    //         System.out.println(path.top() + " to " + r.value + " is visisted");
                    // }
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
                            while(listParent.get(listNode.indexOf(r.value)).remove(path.top()));
                            // System.out.println(listParent.get(listNode.indexOf(r.value)));
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

        topThreePaths(pathList, weight, option, n);
    }

    private void topThreePaths(ArrayList<ArrayList<String>> paths, ArrayList<Integer[]> weight, char option, int n){
        if(paths.size() == 0){
            System.out.println("No flight available");
            return;
        }

        int op = (option == 'C')? 0 : 1;

        // for(int i = 0; i < paths.size() ; i++){
        //     System.out.println(paths.get(i) + "   " + weight.get(i)[op]);
        // }

        int iter = (paths.size() >= n)? n : paths.size();
        for(int i = 0; i < iter; i++){
            int idx = 0;
            int min = Integer.MAX_VALUE;
            int j = 0;
            for(;j < weight.size(); j++){
                if(weight.get(j)[op] < min){
                    min = weight.get(j)[op];
                    idx = j;
                } 
            }
            System.out.print("Path " + (i+1) + ": " + toPath(paths.get(idx)) + "   Time: " + weight.get(idx)[1] + "  Cost: " + weight.get(idx)[0] + "\n");
            paths.remove(idx);
            weight.remove(idx);
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
