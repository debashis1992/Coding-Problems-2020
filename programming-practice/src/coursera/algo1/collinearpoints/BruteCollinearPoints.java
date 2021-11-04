package coursera.algo1.collinearpoints;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

public class BruteCollinearPoints {
    private final List<LineSegment> lineSegments;

    public BruteCollinearPoints(Point[] points) // finds all line segments containing 4 points
    {
        if (points == null || points.length == 0)
            throw new IllegalArgumentException("Points array cannot be null");

        validatePoints(points);
        this.lineSegments = new ArrayList<>();
        for (Point referencePoint : points) {
            // for each point, take the rest of the points
            // find out their individual slopes
            // if they are all equal, that means they are collinear

            List<Point> otherPoints = Arrays.stream(points).filter(point -> !point.equals(referencePoint))
                    .collect(Collectors.toList());

            List<Point> collinearPoints = new ArrayList<>();
            Map<Double, List<Point>> slopeMap = new HashMap<>();


            for (Point otherPoint : otherPoints) {
                double slope = referencePoint.slopeTo(otherPoint);
                if (slopeMap.containsKey(slope)) {
                    List<Point> existingPoints = slopeMap.get(slope);
                    existingPoints.add(otherPoint);
                    slopeMap.put(slope, otherPoints);
                } else slopeMap.put(slope, Collections.singletonList(otherPoint));
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
    }

    public int numberOfSegments() // the number of line segments
    {
        return this.lineSegments.size();
    }

    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[0]);
    }

    private void validatePoints(Point[] points) {
        Map<Point, Object> pointBooleanMap = new HashMap<>();
        for (Point point : points)
            if (point == null || pointBooleanMap.containsKey(point))
                throw new IllegalArgumentException("point cannot be null");
            else pointBooleanMap.put(point, new Object());
    }
}
