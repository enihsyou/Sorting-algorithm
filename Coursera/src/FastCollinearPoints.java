import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.*;

public class FastCollinearPoints {
    private final List<LineSegment> segments;

    public FastCollinearPoints(final Point[] points) {
        if (points == null) { throw new NullPointerException("argument is null"); }
        segments = new ArrayList<>();
        final Point[] pPoints = points.clone();
        Arrays.sort(pPoints);
        if (hasDuplicate(pPoints)) { throw new IllegalArgumentException("Duplicate points"); }
        if (pPoints.length < 2) { return; }
        for (final Point point : pPoints.clone()) {
            Arrays.sort(pPoints);
            Arrays.sort(pPoints, point.slopeOrder());
            for (int j = 2, start = 1; j < pPoints.length; j++) {
                while (j < pPoints.length
                    && Double.compare(point.slopeTo(pPoints[j]), point.slopeTo(pPoints[start])) == 0) { j++; }
                if (j - start >= 3 && point.compareTo(pPoints[start]) <= 0) { segments.add(new LineSegment(point, pPoints[j - 1])); }
                start = j;

            }
        }
    }

    private boolean hasDuplicate(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                return true;
            }
        }
        return false;
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

    public LineSegment[] segments() {return segments.toArray(new LineSegment[segments.size()]);}

    public int numberOfSegments() {return segments.size();}
}
