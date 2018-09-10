import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import java.util.LinkedList;
public class Solver {
    private SearchNode last;
    private boolean isSolve;
    //private MinPQ<SearchNode> mpq;
    public Solver(Board initial) {          // find a solution to the initial board (using the A* algorithm)
        if(initial==null) {
            throw new IllegalArgumentException();
        }
        last=null;
        MinPQ<SearchNode> mpq=new MinPQ<SearchNode>();
        mpq.insert(new SearchNode(initial,null,0));
        while(true) {
            SearchNode currNode = mpq.delMin();
            Board currBoard = currNode.getBoard();
            if(currBoard.isGoal()) {
                last = currNode;
                isSolve=true;
                break;
            }
            if(currBoard.twin().isGoal()) {
                isSolve=false;
                break;
            }
            int moves = currNode.getMoves();
            Board prevBoard=currNode.getMoves()>0? currNode.getPrev().getBoard():null;
            for(Board neighbor : currBoard.neighbors()) {
                if(prevBoard!=null && neighbor.equals(prevBoard)) {
                    continue;
                }
                mpq.insert(new SearchNode(neighbor,currNode,moves+1));
            }
        }
    }
    public boolean isSolvable() {           // is the initial board solvable?
        return isSolve;
    }
    public int moves() {                    // min number of moves to solve initial board; -1 if unsolvable
        return isSolve?last.getMoves():-1;
    }
    public Iterable<Board> solution() {     // sequence of boards in a shortest solution; null if unsolvable
        if(!isSolve) return null;
        LinkedList<Board> list = new LinkedList<Board>();
        SearchNode node = last;
        while(node!=null) {
            list.addFirst(node.getBoard());
            node=node.getPrev();
        }
        return list;
    }
    private class SearchNode implements Comparable<SearchNode>{
        private final SearchNode prev;
        private final Board board;
        private final int moves;
        private final int manhattan;
        public SearchNode(Board board,SearchNode prev,int moves) {
            this.prev=prev;
            this.board=board;
            this.moves=moves;
            this.manhattan=board.manhattan();
        }
        public int compareTo(SearchNode that) {
            return this.priority()-that.priority();
        }
        /*
        public int manhattan() {
            return this.board.manhattan();
        }
        */
        public int priority() {
            return this.manhattan+this.moves;
        }
        public SearchNode getPrev() {
            return this.prev;
        }
        public int getMoves() {
            return this.moves;
        }
        public Board getBoard() {
            return this.board;
        }
    }
    public static void main(String[] args) {// solve a slider puzzle (given below)
        // create initial board from file
    In in = new In(args[0]);
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
            blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
        StdOut.println("No solution possible");
    else {
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution())
            StdOut.println(board);
    }
    }
}