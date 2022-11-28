import java.util.ArrayList;

public class StackG<E>{
    private ArrayList<E> stack;
    private int topIdx;

    public StackG(){
        stack = new ArrayList<E>();
        topIdx = -1;
    }

    public E top(){
        return stack.get(topIdx);
    }

    public int push(E e){
        if(stack.add(e)){
            topIdx++;
            return topIdx;   //Success
        } else {
            return -1;  //Failure
        }
    }

    public E pop(){
        if(topIdx > 0){
            topIdx--;
            return stack.remove(topIdx);
        }
        else    
            return null;
    }

    public String toString(){
        String t = "";
        for(int i = 0; i < topIdx; i++){
            t = t + stack.get(i) + " -> ";
        }
        return t;
    }
}
