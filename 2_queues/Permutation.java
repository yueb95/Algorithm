import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rmq = new RandomizedQueue<String>();
        while(!StdIn.isEmpty()){
            String temstr = StdIn.readString();
            rmq.enqueue(temstr);
        }
        Iterator<String> temiterator = rmq.iterator();
        for(int i=1; i<=k; i++){
            StdOut.println(temiterator.next());
        }
    }
}
