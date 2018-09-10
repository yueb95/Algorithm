import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] item;
    private int size;
    
    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        for(int i=0; i<size; i++){
            temp[i] = item[i];
        }
        item = temp;
    }
    
    private class RandomizedArrayIterator implements Iterator<Item> {
        private int index;
        private Item[] temarray;
        
        public RandomizedArrayIterator() {
            temarray = (Item[]) new Object[size];
            if(!(size == 0)){
                for(int i=0; i<size; i++){
                    temarray[i]=item[i];
                }
                StdRandom.shuffle(temarray,0,size);
                index = 0;
            }else{
                index = -1;
                temarray = null;
            }
        }
        
        public boolean hasNext() {
            return (index <= size-1) && (index >= 0);
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        public Item next() {
            if(!hasNext()) throw new NoSuchElementException();
            index = index+1;
            return temarray[index-1];
        }
    }        
    
    public RandomizedQueue() {               // construct an empty randomized queue
        size = 0;
        item = (Item[]) new Object[2];
    }
        
    public boolean isEmpty() {               // is the queue empty?
        return size == 0;
    }
        
    public int size() {                      // return the number of items on the queue
        return size;
    }
        
    public void enqueue(Item item) {         // add the item
        if(item == null) {
            throw new NullPointerException();
        }
        if(size == this.item.length) resize(2*this.item.length);
        this.item[size] = item;
        size = size+1;
    }
        
    public Item dequeue() {                  // remove and return a random item
        if(isEmpty()) {
            throw new NoSuchElementException();
        }
        int removeIndex = StdRandom.uniform(size);
        Item removeItem = item[removeIndex];
        if(removeIndex != (size-1)){
            item[removeIndex] = item[size-1];
        }
        item[size-1]=null;
        size = size-1;
        if(size > 0 && size == item.length/4) resize(item.length/2);
        return removeItem;
    }
                
    public Item sample() {                   // return (but do not remove) a random item
        if(isEmpty()) {
            throw new NoSuchElementException();
        }
        int sampleIndex = StdRandom.uniform(size);
        return item[sampleIndex];
    }
        
    public Iterator<Item> iterator() {       // return an independent iterator over items in random order
        return new RandomizedArrayIterator();
    }
        
    public static void main(String[] args) { // unit testing (optional)
        
        RandomizedQueue<String> queue1 = new RandomizedQueue<String>();
        queue1.enqueue(new String("3"));
        queue1.enqueue(new String("4"));
        queue1.enqueue(new String("5"));
        queue1.enqueue(new String("6"));
        queue1.enqueue(new String("7"));
        queue1.enqueue(new String("8"));
        Iterator<String> subiterator = queue1.iterator();
        Iterator<String> superiterator = queue1.iterator();
        System.out.println(subiterator.next());
        System.out.println(superiterator.next());
        System.out.println(queue1.size);
        
        int[] tem1 = {0,1,2,3,4,5};
        int[] tem2 = {0,1,2,3,4,5};
        StdRandom.shuffle(tem1,0,6);
        System.out.println(tem1[0]);
        StdRandom.shuffle(tem2,0,6);
        System.out.println(tem2[0]);
    }
}