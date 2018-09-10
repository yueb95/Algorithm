import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.util.LinkedList;
//up should be deleted in the end
//import java.util.Arrays;
public class BruteCollinearPoints { 
    private int numOfSegments;
    private LineSegment[] lineSegment;
    public BruteCollinearPoints(Point[] points) {  // finds all line segments containing 4 points
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
        numOfSegments = 0;
        Point[] tem4points = new Point[4];
        //LineSegment[] temlineSegment = new LineSegment[numOfPoints*(numOfPoints-1)*(numOfPoints-2)*(numOfPoints-3)/4]; //may use linked list
        LinkedList<LineSegment> temlineList = new LinkedList<LineSegment>();
        int index = 0;
        for(int i=0; i<numOfPoints; i++){
            for(int j=i+1; j<numOfPoints; j++){
                for(int m=j+1; m<numOfPoints; m++){
                    if(temPoints[i].slopeTo(temPoints[j]) == temPoints[i].slopeTo(temPoints[m])) {
                        for(int n=m+1; n<numOfPoints; n++){
                            if((temPoints[i].slopeTo(temPoints[j]) == temPoints[i].slopeTo(temPoints[m])) && (temPoints[i].slopeTo(temPoints[j]) == temPoints[i].slopeTo(temPoints[n]))){                            
                                tem4points[0] = temPoints[i];
                                tem4points[1] = temPoints[j];
                                tem4points[2] = temPoints[m];
                                tem4points[3] = temPoints[n];
                                tem4points = this.sorted4points(tem4points);
                                LineSegment temline = new LineSegment(tem4points[0],tem4points[3]);
                                //temlineSegment[index] = temline;
                                temlineList.addLast(temline);
                                index = index+1;
                                numOfSegments = numOfSegments+1;
                            }
                        }
                    }
                }
            }
        }
        lineSegment = new LineSegment[numOfSegments];
        //for(int i=0; i<numOfSegments; i++) {
          //  lineSegment[i] = temlineSegment[i];
        //}
        lineSegment = temlineList.toArray(new LineSegment[0]);
    }    
    private Point[] sorted4points(Point[] tem4points) { //may sort as a whole
        for(int i=1; i<tem4points.length; i++){
            for(int j=i; j>0; j=j-1){
                if(tem4points[j].compareTo(tem4points[j-1]) < 0){
                    Point tempoint = tem4points[j];
                    tem4points[j] = tem4points[j-1];
                    tem4points[j-1] = tempoint;
                }else{
                    break;
                }
            }
        }
        return tem4points;
    }
    public           int numberOfSegments() {      // the number of line segments
        return numOfSegments;
    }
    public LineSegment[] segments() {              // the line segments
        return lineSegment.clone(); // immutable
    }
    //main method
   
    public static void main(String[] args) {
        Point p1 = new Point(10000,0);
        Point p2 = new Point(0,10000);
        Point p3 = new Point(3000,7000);
        Point p4 = new Point(7000,3000);
        Point p5 = new Point(20000,21000);
        Point p6 = new Point(3000,4000);
        Point p7 = new Point(14000,15000);
        Point p8 = new Point(6000,7000);
        Point p9 = new Point(1000,1000);
        Point[] points = new Point[8];
        points[0] = p1;
        points[1] = p2;
        points[2] = p3;
        points[3] = p4;
        points[4] = p5;
        points[5] = p6;
        points[6] = p7;
        points[7] = p8;
        //points[8] = p9;
        //System.out.println(points.length);
        BruteCollinearPoints brute1 = new BruteCollinearPoints(points);
        //System.out.println(p1.slopeTo(p2));
        //System.out.println(p1.slopeTo(p3));
        //System.out.println(p1.slopeTo(p4));
        //System.out.println(p1.slopeTo(p8));
        //System.out.println(p5.slopeTo(p6));
        //System.out.println(p5.slopeTo(p7));
        //System.out.println(p5.slopeTo(p8));
        //System.out.println(p1.slopeTo(p8));        
        System.out.println(brute1.numberOfSegments());
        //System.out.println(brute1.segments().length);
        System.out.println(brute1.lineSegment[0]);
        System.out.println(brute1.lineSegment[1]);
        points[5] = p9;
        System.out.println(brute1.numberOfSegments());
        for (LineSegment segment : brute1.segments()) {
            StdOut.println(segment);
            //segment.draw();
        }
        //System.out.println(brute1.segments());
        //System.out.println(brute1.lineSegment[1]);
    }

/*   
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
    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
    }
    StdDraw.show();
}
*/
}