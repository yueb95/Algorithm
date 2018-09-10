import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
public class SAP {

    private Digraph G;
    private static final int INFINITY = Integer.MAX_VALUE;
    
    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if(G==null) throw new IllegalArgumentException("argument is null");
        this.G = new Digraph(G);
    }
    
    // private method to validate vertex
    private void validateVertex(int v) {
        if(v<0 || v>=G.V()) throw new IllegalArgumentException("vertex is not valid");
    }
    
    //private method to validate vertices
    private void validateVertices(Iterable<Integer> vertices) {
        if(vertices==null) throw new IllegalArgumentException("argument is null");
        for(int v:vertices) {
            validateVertex(v);
        }
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        BreadthFirstDirectedPaths vPath = new BreadthFirstDirectedPaths(G,v);
        BreadthFirstDirectedPaths wPath = new BreadthFirstDirectedPaths(G,w);
        int ans=INFINITY;
        for(int i=0; i<G.V(); i++) {
            if(vPath.hasPathTo(i) && wPath.hasPathTo(i)) {
                ans=Math.min(ans,vPath.distTo(i)+wPath.distTo(i));
            }
        }
        return ans==INFINITY?-1:ans;
    }
 
    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        BreadthFirstDirectedPaths vPath = new BreadthFirstDirectedPaths(G,v);
        BreadthFirstDirectedPaths wPath = new BreadthFirstDirectedPaths(G,w);
        int len=INFINITY;
        int ans=-1;
        for(int i=0; i<G.V(); i++) {
            if(vPath.hasPathTo(i) && wPath.hasPathTo(i)) {
                if(len>vPath.distTo(i)+wPath.distTo(i)) {
                    ans=i;
                    len=vPath.distTo(i)+wPath.distTo(i);
                }
            }
        }
        return ans;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        validateVertices(v);
        validateVertices(w);
        BreadthFirstDirectedPaths vPath = new BreadthFirstDirectedPaths(G,v);
        BreadthFirstDirectedPaths wPath = new BreadthFirstDirectedPaths(G,w);
        int ans=INFINITY;
        for(int i=0; i<G.V(); i++) {
            if(vPath.hasPathTo(i) && wPath.hasPathTo(i)) {
                ans=Math.min(ans,vPath.distTo(i)+wPath.distTo(i));
            }
        }
        return ans==INFINITY?-1:ans;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        validateVertices(v);
        validateVertices(w);
        BreadthFirstDirectedPaths vPath = new BreadthFirstDirectedPaths(G,v);
        BreadthFirstDirectedPaths wPath = new BreadthFirstDirectedPaths(G,w);
        int len=INFINITY;
        int ans=-1;
        for(int i=0; i<G.V(); i++) {
            if(vPath.hasPathTo(i) && wPath.hasPathTo(i)) {
                if(len>vPath.distTo(i)+wPath.distTo(i)) {
                    ans=i;
                    len=vPath.distTo(i)+wPath.distTo(i);
                }
            }
        }
        return ans;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}