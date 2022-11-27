import java.util.ArrayList;

public class Stack<E>{
    private ArrayList<E> stack;
    private int topIdx;

    public Stack(){
        stack = new ArrayList<E>();
        topIdx = 0;
    }

    public E top(){
        return stack.get(topIdx);
    }

    public int push(E e){
        if(stack.add(e)){
            topIdx++;
            return 0;   //Success
        } else {
            return -1;  //Failure
        }
    }

    public E pop(){
        if(topIdx > 0)
            return stack.remove(topIdx);
        else    
            return null;
    }
}
