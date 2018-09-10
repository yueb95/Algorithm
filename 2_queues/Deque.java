import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private int size;
    private Node head;
    
    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }
    
    private class ListIterator implements Iterator<Item> {
        private Node current = head.next;
        public boolean hasNext() {return current.item != null;}
        public void remove() {throw new UnsupportedOperationException();}
        
        public Item next(){
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    
    public Deque() {                         // construct an empty deque
        size = 0;
        head = new Node();
        head.next = head;
        head.prev = head;
    }
        
    public boolean isEmpty() {               // is the deque empty?
        return size==0;
    }
    
    public int size() {                      // return the number of items on the deque
        return size;
    }
        
    public void addFirst(Item item) {        // add the item to the front
        if(item == null){
            throw new NullPointerException();
        }
        Node temNode = new Node();
        temNode.item = item;
        temNode.next = head.next;
        temNode.prev = head;
        head.next.prev = temNode;
        head.next = temNode;
        size = size+1;
    }        
        
    public void addLast(Item item) {         // add the item to the end
        if(item == null){
            throw new NullPointerException();
        }
        Node temNode = new Node();
        temNode.item = item;
        temNode.next = head;
        temNode.prev = head.prev;
        head.prev.next = temNode;
        head.prev = temNode;
        size = size+1;
    }
        
    public Item removeFirst() {              // remove and return the item from the front
        if(size == 0){
            throw new NoSuchElementException();
        }
        Node temNode = head.next;
        head.next = head.next.next;
        head.next.prev = head;
        size = size-1;
        return temNode.item;
    }
        
    public Item removeLast() {               // remove and return the item from the end
        if(size == 0){
            throw new NoSuchElementException();
        }
        Node temNode = head.prev;
        head.prev = head.prev.prev;
        head.prev.next = head;
        size = size-1;
        return temNode.item;
    }
        
    public Iterator<Item> iterator() {       // return an iterator over items in order from front to end
        return new ListIterator();
    }
        
    public static void main(String[] args) { // unit testing (optional)
        Deque<Integer> deque1 = new Deque<Integer>();
        deque1.addFirst(new Integer(3));
        deque1.addFirst(new Integer(2));
 
        System.out.println(deque1.removeFirst());
        System.out.println(deque1.removeFirst());
    }
        
}