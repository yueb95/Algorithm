import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.util.Comparator;
import java.util.Arrays;
import java.util.LinkedList;
//import java.util.List;

public class FastCollinearPoints {
    private int numOfSegments;
    private LineSegment[] lineSegment; 
    public FastCollinearPoints(Point[] points) {   // finds all line segments containing 4 or more points
        //Point[] temPoints = points.clone(); // immutable
        if(points == null) {
            throw new IllegalArgumentException();
        }
        //int numOfPoints = temPoints.length;
        for(int i=0; i<points.length; i++) {
            if(points[i] == null) { throw new IllegalArgumentException(); }
        }
        for(int i=0; i<points.length; i++) {
            for(int j=i+1; j<points.length; j++) {
                //if(points[i] == points[j]) { throw new IllegalArgumentException(); }
                if(points[i].compareTo(points[j]) == 0) { throw new IllegalArgumentException(); }
            }
        }
        Point[] temPoints = points.clone(); // immutable
        int numOfPoints = temPoints.length;
        Arrays.sort((Object[]) temPoints);
        Point[] temarr = new Point[numOfPoints];
        for(int i=0; i<numOfPoints; i++) {
            temarr[i] = temPoints[i];
        }
        Point refpoint = new Point(0,0);
        if(numOfPoints >= 4) {
            //LineSegment[] temlineSegment = new LineSegment[numOfPoints*numOfPoints*numOfPoints*numOfPoints]; //may use linked list
            LinkedList<LineSegment> linelist = new LinkedList<LineSegment>(); //try
            numOfSegments = 0;
            for(int i=0; i<numOfPoints; i++) {
                for(int j=0; j<numOfPoints; j++) {
                    temPoints[j] = temarr[j];
                }
                refpoint = temPoints[i];
                Comparator<Point> pointComparator = refpoint.slopeOrder();
                Arrays.sort(temPoints,pointComparator);
                int index=1;            
                double curSlope = refpoint.slopeTo(temPoints[index]);
                double nextSlope = refpoint.slopeTo(temPoints[index+1]); 
                int numOfSame = 0;                
                while(index < numOfPoints-1) {
                    while(curSlope == nextSlope) {
                        //if(refpoint.compareTo(temPoints[index]) == -1) {
                        if(refpoint.compareTo(temPoints[index]) < 0) {    
                            index = index+1;
                            curSlope = nextSlope;
                            numOfSame = numOfSame+1;
                            if(index < numOfPoints-1) {
                                nextSlope = refpoint.slopeTo(temPoints[index+1]);
                            }else{
                                break;
                            }
                        }else{
                            double temslope = refpoint.slopeTo(temPoints[index]);
                            while(refpoint.slopeTo(temPoints[index+1]) == temslope ) {
                                if(index < numOfPoints-2) {
                                    index = index+1;
                                }else{
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    if(numOfSame >= 2) {
                        numOfSegments = numOfSegments+1;                        
                        LineSegment temline = new LineSegment(refpoint,temPoints[index]);                                                
                        //temlineSegment[numOfSegments-1] = temline;
                        linelist.addLast(temline);
                    }
                    numOfSame = 0;
                    if(index < numOfPoints-1) {
                        index = index+1;
                        curSlope = refpoint.slopeTo(temPoints[index]);
                    }else{
                        break;
                    }
                    if(index < numOfPoints-1) {
                        nextSlope = refpoint.slopeTo(temPoints[index+1]);
                    }else{
                        break;
                    }
                }
            }
            lineSegment = new LineSegment[numOfSegments];
            //for(int i=0; i<numOfSegments; i++) {
                //lineSegment[i] = temlineSegment[i];
            //}
            lineSegment = linelist.toArray(new LineSegment[0]);
        }else{
            //lineSegment = null;
            lineSegment = new LineSegment[0];
            numOfSegments = 0;
        }
    }    
    public           int numberOfSegments() {      // the number of line segments
        return numOfSegments;
    }
    public LineSegment[] segments() {              // the line segments
        return lineSegment.clone(); // immutable
    }   
    public static void main(String[] args) {

    // read the n points from a file
    In in = new In(args[0]);
    int n = in.readInt();
    Point[] points = new Point[n];
    for (int i = 0; i < n; i++) {
        int x = in.readInt();
        int y = in.readInt();
        points[i] = new Point(x, y);
    }

    // draw the points
    StdDraw.enableDoubleBuffering();
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    for (Point p : points) {
        p.draw();
    }
    StdDraw.show();

    // print and draw the line segments
    FastCollinearPoints collinear = new FastCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
    }
    StdDraw.show();
    }

}