import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    
    private int gridsize;
    private boolean[] isopen;
    private WeightedQuickUnionUF wqu1;   //for percolate()
    private WeightedQuickUnionUF wqu2;   //for isFull()
    private int numopen;
    
    private void validateGridsize(int n){
        if(n<=0){
            throw new IllegalArgumentException("the grid size is less then one.");
        }
    }
    
    private void validateIndex(int row, int col){
        if(row<=0 || col<=0 || row>gridsize || col>gridsize){
            throw new IndexOutOfBoundsException("the index is out of (1,n).");
        }
    }
    
    private int xyTo1D(int row, int col){
        validateIndex(row,col);
        return row*(gridsize+2)+col+1;
    }
    
    private int neighborUp(int row, int col){
        return (row-1)*(gridsize+2)+col+1;
    }
    
    private int neighborDown(int row, int col){
        return (row+1)*(gridsize+2)+col+1;
    }
    
    public Percolation(int n){               // create n-by-n grid, with all sites blocked
        validateGridsize(n);
        gridsize = n;
        isopen = new boolean[(n+2)*(n+2)+2];
        wqu1 = new WeightedQuickUnionUF((n+2)*(n+2)+2);
        wqu2 = new WeightedQuickUnionUF((n+2)*(n+2)+1);
        for(int i=0; i<(n+2)*(n+2)+2; i++){
            isopen[i]=false;
        }
    }
    
    public    void open(int row, int col){   // open site (row, col) if it is not open already
        int index = xyTo1D(row,col);
        if(!isOpen(row,col)){
            numopen = numopen+1;
        }
        isopen[index] = true;
        int[] indexNeighbor = new int[4];
        indexNeighbor[0] = neighborUp(row,col);
        indexNeighbor[1] = neighborDown(row,col);
        indexNeighbor[2] = index-1;
        indexNeighbor[3] = index+1;
        for(int i=0; i<4; i++){
            if(isopen[indexNeighbor[i]]){
                if(!wqu1.connected(index,indexNeighbor[i])){
                    wqu1.union(index,indexNeighbor[i]);
                }
                if(!wqu2.connected(index,indexNeighbor[i])){
                    wqu2.union(index,indexNeighbor[i]);
                }
            }
        }
        if(row==1){
            wqu1.union(0,index);
            wqu2.union(0,index);
        }
        if(row==gridsize){
            wqu1.union((gridsize+2)*(gridsize+2)+1,index);
        }
    }
        
    public boolean isOpen(int row, int col){ // is site (row, col) open?
        int index = xyTo1D(row,col);
        return isopen[index];
    }
    
    public boolean isFull(int row, int col){ // is site (row, col) full?
        int index = xyTo1D(row,col);
        return wqu2.connected(index,0);
    }
                               
    public     int numberOfOpenSites(){      // number of open sites
        return numopen;
    }
    
    public boolean percolates(){             // does the system percolate?
        return wqu1.connected(0,(gridsize+2)*(gridsize+2)+1);
    }

    public static void main(String[] args){  // test client (optional)
        Percolation p1 = new Percolation(2);
        p1.open(1,1);
        p1.open(1,2);
        System.out.println(p1.percolates());
        p1.open(2,1);
        System.out.println(p1.percolates());
        System.out.println(p1.isOpen(1,1));
    }

}