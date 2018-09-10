import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.LinearProbingHashST;
import edu.princeton.cs.algs4.LinkedBag;
import edu.princeton.cs.algs4.DirectedCycle;
public class WordNet {
    
    private Digraph G;
    private LinearProbingHashST<String,LinkedBag<Integer>> synToid; // one word may in different synsets, so it has several id
    private LinearProbingHashST<Integer,String> idTosynset;
    private SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if(synsets==null || hypernyms==null) throw new IllegalArgumentException("argument is null");
        synToid = new LinearProbingHashST<>();
        idTosynset = new LinearProbingHashST<>();
        In synFile = new In(synsets);
        In hypFile = new In(hypernyms);
        String strsyn;
        String strhyp;
        String[] fieldsyn;
        String[] syns;
        String[] fieldhyp;
        while(synFile.hasNextLine()) {
            strsyn = synFile.readLine();
            fieldsyn = strsyn.split(",");
            idTosynset.put(Integer.parseInt(fieldsyn[0]),fieldsyn[1]);
            syns = fieldsyn[1].split(" ");
            for(String syn:syns) {
                if(synToid.contains(syn)) synToid.get(syn).add(Integer.parseInt(fieldsyn[0]));
                else {
                    LinkedBag<Integer> list = new LinkedBag<>();
                    list.add(Integer.parseInt(fieldsyn[0]));
                    synToid.put(syn,list);
                }
            }
        }
        G = new Digraph(idTosynset.size());
        while(hypFile.hasNextLine()) {
            strhyp = hypFile.readLine();
            fieldhyp = strhyp.split(",");
            for(int i=1; i<fieldhyp.length; i++) {
                G.addEdge(Integer.parseInt(fieldhyp[0]),Integer.parseInt(fieldhyp[i]));
            }
        }
        //The constructor should throw a java.lang.IllegalArgumentException if the input does not correspond to a rooted DAG.
        int numRoot=0;
        for(int i=0; i<G.V(); i++) {
            if(G.outdegree(i)==0) numRoot++;
        }
        DirectedCycle dc = new DirectedCycle(G);
        if(numRoot!=1 || dc.hasCycle()) throw new IllegalArgumentException("can not form a rooted DAG");
        sap = new SAP(G);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return synToid.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if(word==null) throw new IllegalArgumentException("argument is null");
        return synToid.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if(!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException("argument is null");
        return sap.length(synToid.get(nounA),synToid.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if(!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException("argument is null");
        return idTosynset.get(sap.ancestor(synToid.get(nounA),synToid.get(nounB)));
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wn = new WordNet(args[0], args[1]);
        StdOut.printf("Synset nouns:\n");
        for (String s : wn.nouns())
            StdOut.printf(s);
        StdOut.printf("\n");
        String nounA, nounB;
        StdOut.printf("Enter two wordnet nouns: ");
        while (!StdIn.isEmpty()) {
            nounA = StdIn.readString();
            nounB = StdIn.readString();
            StdOut.printf("Shortest ancestral path length(%s, %s) = %d\n", nounA, nounB, wn.distance(nounA, nounB));
            StdOut.printf("Common ancestor in SAP(%s, %s) = %s\n", nounA, nounB, wn.sap(nounA, nounB));
            StdOut.printf("Enter two wordnet nouns: ");
        }
    }
}