import edu.princeton.cs.algs4.Queue;
import java.util.Comparator;

public class Board {
    private final int dimension;
    private final int[] blocks;
    
    public Board(int[][] blocks) {         // construct a board from an n-by-n array of blocks
        dimension = blocks.length;         // (where blocks[i][j] = block in row i, column j)
        this.blocks = new int[dimension * dimension];
        int index = 0;
        for(int i=0; i<dimension; i++){
            for(int j=0; j<dimension; j++){
                this.blocks[index] = blocks[i][j];
                index++;
            }
        }
    }
    public int dimension() {               // board dimension n
        return dimension;
    }
    public int hamming() {                 // number of blocks out of place
        int location = 0;
        for(int i=0; i<dimension * dimension; i++){
            if((this.blocks[i] != i+1) && (this.blocks[i] != 0)){
                location = location + 1;
            }
        }        
        return location; 
    }
    public int manhattan() {               // sum of Manhattan distances between blocks and goal
        int distance = 0;
        int row;
        int column;
        for(int j=0; j<dimension * dimension; j++){
            if(this.blocks[j] != 0){
                row = Math.abs(j / dimension - (this.blocks[j]-1) / dimension);
                column = Math.abs(j % dimension - (this.blocks[j]-1) % dimension);
                distance = distance + row + column;
            }
        }
        return distance;
    }
    public boolean isGoal() {              // is this board the goal board?
        return (this.manhattan() == 0) && (this.hamming() == 0);
    }
    public Board twin() {                  // a board that is obtained by exchanging any pair of blocks
        int[][] temBlocks = new int[this.dimension][this.dimension];
        int index = 0;
        for(int i=0; i<this.dimension; i++){
            for(int j=0; j<this.dimension; j++){
                temBlocks[i][j] = this.blocks[index];
                index++;
            }
        }
        for(int j=0; j<this.dimension * this.dimension - 1; j++){
            if((this.blocks[j] != 0) && (this.blocks[j+1] != 0)){
                temBlocks[j/this.dimension][j%this.dimension] = this.blocks[j+1];
                temBlocks[(j+1)/this.dimension][(j+1)%this.dimension] = this.blocks[j];
                break;
            }
        }
        Board twin = new Board(temBlocks);
        return twin;
    }
    public boolean equals(Object y) {      // does this board equal y?
        if(y == this) return true;
        if(y == null) return false;
        if(y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if(this.dimension == that.dimension){
            for(int i=0; i<this.dimension*this.dimension; i++){
                if(this.blocks[i] != that.blocks[i]){
                    return false;
                }
            }
            return true;
        }else{
            return false;
        }

        //return (this.dimension == that.dimension) && (this.blocks == that.blocks);
    }
    public Iterable<Board> neighbors() {   // all neighboring boards
        Queue<Board> queueNeighbors = new Queue<Board>();
        int zeroIndex;
        int zeroRow = 0;
        int zeroColumn = 0;
        for(int i=0; i<dimension*dimension; i++){
            if(blocks[i] == 0){
                zeroIndex = i;
                zeroRow = zeroIndex / dimension;
                zeroColumn = zeroIndex % dimension;
                break;
            }
        }
        int[][] neighborBlocks = new int[dimension][dimension];
        int[][] temNeighborBlocks1 = new int[dimension][dimension];
        int[][] temNeighborBlocks2 = new int[dimension][dimension];
        int[][] temNeighborBlocks3 = new int[dimension][dimension];
        int[][] temNeighborBlocks4 = new int[dimension][dimension];
        int index = 0;
        for(int i=0; i<this.dimension; i++){
            for(int j=0; j<this.dimension; j++){
                neighborBlocks[i][j] = this.blocks[index];         //redundant
                index++;
            }
        }
        temNeighborBlocks1 = this.changeUp(neighborBlocks,zeroRow,zeroColumn);
        temNeighborBlocks2 = this.changeDown(neighborBlocks,zeroRow,zeroColumn);
        temNeighborBlocks3 = this.changeLeft(neighborBlocks,zeroRow,zeroColumn);
        temNeighborBlocks4 = this.changeRight(neighborBlocks,zeroRow,zeroColumn);
        if(temNeighborBlocks1 != null){
            queueNeighbors.enqueue(new Board(temNeighborBlocks1));
        }
        if(temNeighborBlocks2 != null){
            queueNeighbors.enqueue(new Board(temNeighborBlocks2));
        }
        if(temNeighborBlocks3 != null){
            queueNeighbors.enqueue(new Board(temNeighborBlocks3));
        }
        if(temNeighborBlocks4 != null){
            queueNeighbors.enqueue(new Board(temNeighborBlocks4));
        }
        return queueNeighbors;
    }
    public String toString() {             // string representation of this board (in the output format specified below)
        int[][] tiles = new int[this.dimension][this.dimension];
        int index = 0;
        for(int i=0; i<this.dimension; i++){
            for(int j=0; j<this.dimension; j++){
                tiles[i][j] = this.blocks[index];
                index++;
            }
        }
        StringBuilder s = new StringBuilder();
        s.append(this.dimension + "\n");
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
        s.append("\n");
        }
        return s.toString();
    }
    private int[][] changeUp(int[][] neighborBlocks,int zeroRow,int zeroColumn){
        if(zeroRow != 0){
            int[][] temNeighborBlocks = new int[neighborBlocks.length][neighborBlocks.length];
            for(int i=0; i<neighborBlocks.length; i++){
                for(int j=0; j<neighborBlocks.length; j++){
                    temNeighborBlocks[i][j] = neighborBlocks[i][j];
                }
            }
            temNeighborBlocks[zeroRow][zeroColumn] = this.blocks[(zeroRow-1)*this.dimension+zeroColumn];
            temNeighborBlocks[zeroRow-1][zeroColumn] = 0;
            return temNeighborBlocks;
        }else{
            return null;
        }
    }
    private int[][] changeDown(int[][] neighborBlocks,int zeroRow,int zeroColumn){
        if(zeroRow != this.dimension-1){
            int[][] temNeighborBlocks = new int[neighborBlocks.length][neighborBlocks.length];
            for(int i=0; i<neighborBlocks.length; i++){
                for(int j=0; j<neighborBlocks.length; j++){
                    temNeighborBlocks[i][j] = neighborBlocks[i][j];
                }
            }
            temNeighborBlocks[zeroRow][zeroColumn] = this.blocks[(zeroRow+1)*this.dimension+zeroColumn];
            temNeighborBlocks[zeroRow+1][zeroColumn] = 0;
            return temNeighborBlocks;
        }else{
            return null;
        }
    }
    private int[][] changeLeft(int[][] neighborBlocks,int zeroRow,int zeroColumn){
        if(zeroColumn != 0){
            int[][] temNeighborBlocks = new int[neighborBlocks.length][neighborBlocks.length];
            for(int i=0; i<neighborBlocks.length; i++){
                for(int j=0; j<neighborBlocks.length; j++){
                    temNeighborBlocks[i][j] = neighborBlocks[i][j];
                }
            }
            temNeighborBlocks[zeroRow][zeroColumn] = this.blocks[zeroRow*this.dimension+zeroColumn-1];
            temNeighborBlocks[zeroRow][zeroColumn-1] = 0;
            return temNeighborBlocks;
        }else{
            return null;
        }
    }
    private int[][] changeRight(int[][] neighborBlocks,int zeroRow,int zeroColumn){
        if(zeroColumn != this.dimension-1){
            int[][] temNeighborBlocks = new int[neighborBlocks.length][neighborBlocks.length];
            for(int i=0; i<neighborBlocks.length; i++){
                for(int j=0; j<neighborBlocks.length; j++){
                    temNeighborBlocks[i][j] = neighborBlocks[i][j];
                }
            }
            temNeighborBlocks[zeroRow][zeroColumn] = this.blocks[zeroRow*this.dimension+zeroColumn+1];
            temNeighborBlocks[zeroRow][zeroColumn+1] = 0;
            return temNeighborBlocks;
        }else{
            return null;
        }
    }
    public static void main(String[] args){// unit tests (not graded)
        /*
        int[][] blocks = {{8,1,3},{4,0,2},{7,6,5}};
        Board newBoard = new Board(blocks);
        System.out.println(newBoard);
        System.out.println("the hamming of newBoard is: " + newBoard.hamming());
        System.out.println("the manhattan of newBoard is: " + newBoard.manhattan());
        Board twinBoard = newBoard.twin();
        System.out.println(twinBoard);
        System.out.println("the hamming of twinBoard is: " + twinBoard.hamming());
        System.out.println("the manhattan of twinBoard is: " + twinBoard.manhattan());
        Queue neighborQueue = (Queue) (newBoard.neighbors());
        System.out.println("the # of neighbors of newBoard is: " + neighborQueue.size());
        Board neighbor1 = (Board)(neighborQueue.dequeue());
        System.out.println("the first neighbor is: ");
        System.out.println(neighbor1);
        System.out.println("the hamming of neighbor1 is: " + neighbor1.hamming());
        System.out.println("the manhattan of neighbor1 is: " + neighbor1.manhattan());
        */
    }
}