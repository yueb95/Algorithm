import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
//import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class PercolationStats {
    private double temtrials;
    private double[] threshold; 
    public PercolationStats(int n, int trials){    // perform trials independent experiments on an n-by-n grid
        if((n<=0) || (trials<=0)){
            throw new IllegalArgumentException("n and trials must be positve");
        }
        threshold = new double[trials]; 
        for(int i=1; i<=trials; i++){
            Percolation tem = new Percolation(n);
            while(!tem.percolates()){
               tem.open(StdRandom.uniform(n)+1,StdRandom.uniform(n)+1);               
            }
            threshold[i-1]=((double)tem.numberOfOpenSites()/(double)(n*n)); 
        }
        temtrials = (double) trials;
    }
    public double mean(){                          // sample mean of percolation threshold
       return StdStats.mean(this.threshold); 
    }
    public double stddev(){                        // sample standard deviation of percolation threshold
       return StdStats.stddev(this.threshold); 
    }
    public double confidenceLo(){                  // low  endpoint of 95% confidence interval
       return this.mean()-1.96*(this.stddev()/Math.sqrt(this.temtrials)); 
    }
    public double confidenceHi(){                  // high endpoint of 95% confidence interval
       return this.mean()+1.96*(this.stddev()/Math.sqrt(this.temtrials)); 
    }
    public static void main(String[] args){        // test client (described below)
       PercolationStats tem = new PercolationStats(2,1000);
       System.out.println("mean" + tem.mean());
    }
}