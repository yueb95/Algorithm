import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.LinkedList;
public class KdTree {
    private Node root;
    private int size;
    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        public Node(Point2D p) {
            this.p = p;
        }
    }
    public KdTree() {                              // construct an empty set of points
        root = null;
        size = 0;
    }
    public boolean isEmpty() {                     // is the set empty? 
        return size() == 0;
    }
    public int size() {                        // number of points in the set 
        return size;
    }
    public void insert(Point2D p) {             // add the point to the set (if it is not already in the set)
        if(p == null) throw new IllegalArgumentException();
        if(!contains(p)) {
            size++;
            root = insert(root,p,true);
        } 
    }
    private Node insert(Node node, Point2D p, boolean even) {
        if(node == null) {
            Node x = new Node(p);
            x.rect = new RectHV(0.0,0.0,1.0,1.0);
            return x;
        }
        if(even) {
            double cmp = p.x()-node.p.x();
            if(cmp<0) {
                if(node.lb == null) {
                    Node x = new Node(p);
                    x.rect = new RectHV(node.rect.xmin(),node.rect.ymin(),node.p.x(),node.rect.ymax());
                    node.lb = x;
                    return node;
                }
                node.lb = insert(node.lb, p, false);
                return node;
            }else {
                if(node.rt == null) {
                    Node x = new Node(p);
                    x.rect = new RectHV(node.p.x(),node.rect.ymin(),node.rect.xmax(),node.rect.ymax());
                    node.rt = x;
                    return node;
                }
                node.rt = insert(node.rt, p, false);
                return node;
            }
        }else{
            double cmp = p.y()-node.p.y();
            if(cmp<0) {
                if(node.lb == null) {
                    Node x = new Node(p);
                    x.rect = new RectHV(node.rect.xmin(),node.rect.ymin(),node.rect.xmax(),node.p.y());
                    node.lb = x;
                    return node;
                }
                node.lb = insert(node.lb, p, true);
                return node;
            }else{
                if(node.rt == null) {
                    Node x = new Node(p);
                    x.rect = new RectHV(node.rect.xmin(),node.p.y(),node.rect.xmax(),node.rect.ymax());
                    node.rt = x;
                    return node;
                }
                node.rt = insert(node.rt, p, true);
                return node;
            }
        }
    }
    public boolean contains(Point2D p) {           // does the set contain point p? 
        if(p == null) throw new IllegalArgumentException();
        return contains(root, p, true);
    }
    private boolean contains(Node node, Point2D p, boolean even) {
        if(node == null) return false;
        if(even) {
            double cmp = p.x()-node.p.x();
            if(cmp<0) {
                return contains(node.lb, p, false);
            }else if(cmp == 0) {
                if(node.p.equals(p)) return true;
                return contains(node.rt, p, false);
            }else {
                return contains(node.rt, p, false);
            }
        }else {
            double cmp = p.y()-node.p.y();
            if(cmp<0) {
                return contains(node.lb, p, true);
            }else if(cmp==0) {
                if(node.p.equals(p)) return true;
                return contains(node.rt, p, true);
            }else {
                return contains(node.rt, p, true);
            }
        }
    }
    public void draw() {                        // draw all points to standard draw 
        draw(root, true);
    }
    private void draw(Node node, boolean even) {
        if(node == null) return;
        if(even) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.point(node.p.x(),node.p.y());
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.p.x(),node.rect.ymin(),node.p.x(),node.rect.ymax());
            draw(node.lb, false);
            draw(node.rt, false);
            return;
        }else {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.point(node.p.x(),node.p.y());
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(node.rect.xmin(),node.p.y(),node.rect.xmax(),node.p.y());
            draw(node.lb, true);
            draw(node.rt, true);
            return;
        }
    } 
    public Iterable<Point2D> range(RectHV rect) {            // all points that are inside the rectangle (or on the boundary) 
        if(rect == null) throw new IllegalArgumentException();
        LinkedList<Point2D> list = new LinkedList<Point2D>();
        range(root, rect, list);
        return list;
    }
    private void range(Node node, RectHV rect, LinkedList<Point2D> list) {
        if(node == null) return;
        if(node.rect.intersects(rect)) {
            if(rect.contains(node.p)) list.add(node.p);
            range(node.lb, rect, list);
            range(node.rt, rect, list);
            return;
        }else {
            return;
        }
    }
    public Point2D nearest(Point2D p) {            // a nearest neighbor in the set to point p; null if the set is empty 
        if(p == null) throw new IllegalArgumentException();
        if(isEmpty()) return null;
        Point2D curr = null;
        return nearest(root, p, curr, true);
    }
    private Point2D nearest(Node node, Point2D p, Point2D curr, boolean even) {
        if(node == null) return curr;
        double q = p.distanceTo(node.p);
        double min = curr == null ? 2.0 : p.distanceTo(curr);
        if(min>q) {
            min = q;
            curr = node.p;
        }
        if(even) {
            double temp = node.p.x()-p.x();
            RectHV range = temp<0? new RectHV(node.rect.xmin(),node.rect.ymin(),node.p.x(),node.rect.ymax()):new RectHV(node.p.x(),node.rect.ymin(),node.rect.xmax(),node.rect.ymax());
            if(min<=range.distanceTo(p)) {
                if(temp<0) {
                    curr = nearest(node.rt, p, curr, false);
                    return curr;
                }else{
                    curr = nearest(node.lb, p, curr, false);
                    return curr;
                }                
            }else {
                if(temp<=0) {
                    curr = nearest(node.rt, p, curr, false);
                    // some optimization
                    min = p.distanceTo(curr);
                    if(min<=range.distanceTo(p)) return curr;
                    curr = nearest(node.lb, p, curr, false);
                    return curr;
                }else {
                    curr = nearest(node.lb, p, curr, false);
                    min = p.distanceTo(curr);
                    if(min<=range.distanceTo(p)) return curr;
                    curr = nearest(node.rt, p, curr, false);
                    return curr;
                }
            }
        }else{
            double temp = node.p.y()-p.y();
            RectHV range = temp<0? new RectHV(node.rect.xmin(),node.rect.ymin(),node.rect.xmax(),node.p.y()):new RectHV(node.rect.xmin(),node.p.y(),node.rect.xmax(),node.rect.ymax());
            if(min<=range.distanceTo(p)) {
                if(temp<0) {
                    curr = nearest(node.rt, p, curr, true);
                    return curr;
                }else{
                    curr = nearest(node.lb, p, curr, true);
                    return curr;
                }
            }else {
                if(temp<=0) {
                    curr = nearest(node.rt, p, curr, true);
                    min = p.distanceTo(curr);
                    if(min<=range.distanceTo(p)) return curr;
                    curr = nearest(node.lb, p, curr, true);
                    return curr;
                }else {
                    curr = nearest(node.lb, p, curr, true);
                    min = p.distanceTo(curr);
                    if(min<=range.distanceTo(p)) return curr;
                    curr = nearest(node.rt, p, curr, true);
                    return curr;
                }
            }
        }
    }
    public static void main(String[] args) {                 // unit testing of the methods (optional) 
        KdTree tree = new KdTree();
        Point2D p1 = new Point2D(0.375,0.625);
        tree.insert(p1);        
        Point2D p2 = new Point2D(1.0,0.4375);
        tree.insert(p2);
        Point2D p3 = new Point2D(0.5,0.6875);
        tree.insert(p3);
        Point2D p4 = new Point2D(0.6875,0.125);
        tree.insert(p4);
        Point2D p5 = new Point2D(0.75,0.25);
        tree.insert(p5);
        Point2D p6 = new Point2D(0.125,0.5);
        tree.insert(p6);        
        Point2D p7 = new Point2D(0.9375,0.875);
        tree.insert(p7);
        Point2D p8 = new Point2D(0.25,0.3125);
        tree.insert(p8);
        Point2D p9 = new Point2D(0.3125,0.9375);
        tree.insert(p9);
        Point2D p10 = new Point2D(0.5625,1.0);
        tree.insert(p10);
        tree.draw();
        //System.out.println(tree.root.rt.lb.lb.p);
        //System.out.println(tree.root.rt.lb.rt.p);
        //System.out.println(tree.root.rt.rt.lb.rt.p);
        /*
        Point2D p6 = new Point2D(0.456676,0.345676);
        System.out.println(tree.nearest(p6));
        tree.nearest(p6).draw();
        System.out.println(tree.size());
        */
        Point2D target = new Point2D(0.0625,0.1875);
        System.out.println(tree.nearest(target));
        
        System.out.println(tree.contains(p8));
    }
}