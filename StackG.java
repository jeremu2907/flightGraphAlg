import java.util.ArrayList;

public class StackG<E> implements Comparable<E>{
    private ArrayList<E> stack;
    private int topIdx;

    public StackG(){
        stack = new ArrayList<E>();
        topIdx = -1;
    }

    public ArrayList<E> toArrayList(){
        ArrayList<E> ret = new ArrayList<>();
        for(E i : stack){
            ret.add(i);
        }
        return ret;
    }

    public boolean empty(){
        if(topIdx == -1)
            return true;
        else
            return false;
    }

    public E top(){
        if(topIdx > -1)
            return stack.get(topIdx);
        else
            return null;
    }

    public void push(E e){
        topIdx++;
        stack.add(e);
    }

    public E pop(){
        if(topIdx > -1){
            topIdx--;
            return stack.remove(topIdx + 1);
        }
        else
            return null;
    }

    public String toString(){
        String t = "";
        for(int i = topIdx; i >= 0; i--){
            t = t + stack.get(i) + " -> ";
        }
        return t;
    }

    public void clear(){
        topIdx = -1;
        stack = new ArrayList<>();
    }

    @Override
    public int compareTo(Object o) {
        // TODO Auto-generated method stub
        if(stack.equals(o))
            return 1;
        else
            return 0;
    }

    
}
