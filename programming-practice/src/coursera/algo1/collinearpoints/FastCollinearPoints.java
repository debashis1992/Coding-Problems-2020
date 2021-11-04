package coursera.algo1.collinearpoints;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

public class FastCollinearPoints {
    private final List<LineSegment> lineSegments;

    public FastCollinearPoints(Point[] points) // finds all line segments containing 4 or more points
    {
        if (points == null || points.length == 0)
            throw new IllegalArgumentException("Points array cannot be null");

        validatePoints(points);
        this.lineSegments = new ArrayList<>();

        List<Point> pointList = Arrays.asList(points).stream().sorted().collect(Collectors.toList());
        Point referencePoint = pointList.get(0);
        List<Point> collinearPoints = new ArrayList<>();
        Map<Double, List<Point>> slopeMap = new HashMap<>();


        for (int i = 1; i < pointList.size(); i++) {
            double slope = referencePoint.slopeTo(pointList.get(i));
            if (slopeMap.containsKey(slope)) {
                List<Point> existingPoints = slopeMap.get(slope);
                existingPoints.add(pointList.get(i));
                slopeMap.put(slope, pointList);
            } else slopeMap.put(slope, Collections.singletonList(pointList.get(i)));
        }

        boolean isCollinear = false;
        for (Map.Entry<Double, List<Point>> entry : slopeMap.entrySet()) {
            if (entry.getValue().size() >= 3) {
                isCollinear = true;
                collinearPoints = entry.getValue();
            }
        }
        if (isCollinear) {
            // find out the line segments for point p
            lineSegments.add(new LineSegment(referencePoint, collinearPoints.get(0)));
            for (int j = 1; j < collinearPoints.size(); j++) {
                lineSegments.add(new LineSegment(collinearPoints.get(j - 1), collinearPoints.get(j)));
            }
        }
    }

    public int numberOfSegments() // the number of line segments
    {
        return this.lineSegments.size();
    }

    public LineSegment[] segments() // the line segments
    {
        return lineSegments.toArray(new LineSegment[0]);
    }

    private void validatePoints(Point[] points) {
        Map<Point, Object> pointBooleanMap = new HashMap<>();
        for (Point point : points)
            if (point == null || pointBooleanMap.containsKey(point))
                throw new IllegalArgumentException("point cannot be null");
            else pointBooleanMap.put(point, new Object());
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
