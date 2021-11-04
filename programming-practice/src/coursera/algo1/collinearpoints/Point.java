package coursera.algo1.collinearpoints;

import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.circle(this.x, this.y, 1);
    }

    public void drawTo(Point that) {
        validatePoint(that);
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public String toString() {
        return "";
    }


    @Override
    public int compareTo(Point that) {
        validatePoint(that);

        if (this.y == that.y) {  // breaking the ties by x-coordinates
            if (this.x - that.x > 0)
                return 1;
            else return -1;
        } else if (this.y - that.y > 0)
            return 1;
        else return -1;
    }

    public double slopeTo(Point that) {
        validatePoint(that);

        if (this.equals(that))
            return Double.NEGATIVE_INFINITY;

        if (this.x == that.x) {
            // vertical line
            return Double.POSITIVE_INFINITY;
        } else if (this.y == that.y) {
            // horizontal line
            return 0;
        } else return findSlope(that);
    }

    public Comparator<Point> slopeOrder() {
        return (o1, o2) -> {
            validatePoint(o1);
            validatePoint(o2);

            double slope1 = slopeTo(o1);
            double slope2 = slopeTo(o2);

            return Double.compare(slope1, slope2);
        };
    }

    private void validatePoint(Point that) {
        if (that == null)
            throw new IllegalArgumentException("Point passed cannot be null");
    }

    private double findSlope(Point that) {
        return (double) (that.y - this.y) / (double) (that.x - this.x);
    }
}
