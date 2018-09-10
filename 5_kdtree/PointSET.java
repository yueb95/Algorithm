import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.LinkedList;

public class PointSET {
    private SET<Point2D> set;
    public PointSET() {                              // construct an empty set of points 
        set = new SET<Point2D>();
    }
    public boolean isEmpty() {                     // is the set empty? 
        return set.isEmpty();
    }
    public int size() {                         // number of points in the set
        return set.size();
    }
    public void insert(Point2D p) {             // add the point to the set (if it is not already in the set)
        if(p == null) throw new IllegalArgumentException();
        if(!set.contains(p)) set.add(p);
    }
    public boolean contains(Point2D p) {           // does the set contain point p? 
        if(p == null) throw new IllegalArgumentException();
        return set.contains(p);
    }
    public void draw() {                        // draw all points to standard draw 
        for(Point2D p : set) {
            StdDraw.point(p.x(),p.y());
        }
    }
    public Iterable<Point2D> range(RectHV rect) {            // all points that are inside the rectangle (or on the boundary) 
        if(rect==null) throw new IllegalArgumentException();
        LinkedList<Point2D> list = new LinkedList<Point2D>();
        for(Point2D p : set) {
            if(rect.contains(p)) {
                list.add(p);
            }
        }
        return list;
    }
    public Point2D nearest(Point2D p) {            // a nearest neighbor in the set to point p; null if the set is empty 
        if(p == null) throw new IllegalArgumentException();
        if(set.isEmpty()) return null;
        Point2D currp = null; // should initialize it
        double min = 2.0;
        for(Point2D q : set) {
            double cur = q.distanceTo(p);
            if(cur < min) {
                min = cur;
                currp = q;
            }
        }
        return currp;
    }
    public static void main(String[] args) {                 // unit testing of the methods (optional) 
    }
}