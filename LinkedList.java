class Node<E> {
    E value;
    Node<E> next;

    Node(E e){
        value = e;
        next = null;
    }

    public String toString(){
        return value.toString();
    }
}


public class LinkedList<E>{
    public Node<E> head;

    public LinkedList() {
        this.head = null;
    }

    public LinkedList(E e){
        this.head = new Node<>(e);
        this.head.next = null;
    }

    //Add to head of the path
    public int addHead(E s){
        Node<E> e = new Node<>(s);
        e.next = this.head;
        this.head = e;
        return 0;
    }

    //Find if a city is in the list
    //Option: 0 for the Node<E>, 1 for the Node<E> that points to it
    public Node<E> find(String e,int option){
        Node<E> t = this.head;
        if(t == null)
            return null;

        while(true){
            if(t.value == e && option == 0)
                return t;   //Found when request for the Node<E> itself
            
            if(t.next != null){
                if(t.next.value == e && option == 1)
                    return t;   //Found when require Node<E> before the target Node<E>
                t = t.next;
            }
            else
                break;
        }

        return null;    //Not found
    }

    public String listToString(){
        String t = "";
        Node<E> tempNode = this.head;
        while(true){
            if(tempNode != null){
                t = t.concat(tempNode.toString());
                t = t.concat(" | ");
            } else {
                break;
            }
            tempNode = tempNode.next;
        }

        return t;
    }

    public int remove(String e){
        Node<E> t = this.find(e,1);
        if(t == null)
            return -1;  //Not found no removal
        else{
            t.next = t.next.next;
        }

        return 0;
    }

    public String toString(){
        String t = "";
        Node<E> tempNode = this.head;
        while(true){
            if(tempNode != null){
                t = t.concat(tempNode.toString());
                t = t.concat("\n");
            } else {
                break;
            }
            tempNode = tempNode.next;
        }

        return t;
    }
}
