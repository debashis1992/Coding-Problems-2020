package coursera.algo1.collinearpoints;

import edu.princeton.cs.algs4.StdDraw;

public class LineSegment {
    Point p;
    Point q;

    public LineSegment(Point p, Point q) {
        this.p = p;
        this.q = q;
    }

    public void draw() {
//        StdDraw.line(p.getX(), p.getY(), q.getX(), q.getY());
    }

    public String toString() {
        return "";
    }
}
