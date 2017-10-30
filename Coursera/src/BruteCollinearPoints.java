import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private final List<LineSegment> segments;

    public BruteCollinearPoints(final Point[] points) {
        if (points == null) { throw new NullPointerException("argument is null"); }
        segments = new ArrayList<>();
        final Point[] pPoints = points.clone();
        Arrays.sort(pPoints);
        for (int i = 0; i < pPoints.length; i++) {
            Point a = pPoints[i];
            for (int j = i + 1; j < pPoints.length; j++) {
                Point b = pPoints[j];
                final double a2b = a.slopeTo(b);
                for (int k = j + 1; k < pPoints.length; k++) {
                    Point c = pPoints[k];
                    final double b2c = b.slopeTo(c);
                    if (Double.compare(a2b, b2c) != 0) { continue; }
                    for (int l = k + 1; l < pPoints.length; l++) {
                        Point d = pPoints[l];
                        final double c2d = c.slopeTo(d);
                        if (Double.compare(b2c, c2d) != 0) { continue; }
                        segments.add(new LineSegment(a, d));
                    }
                }
            }
        }
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

    public LineSegment[] segments() {return segments.toArray(new LineSegment[segments.size()]);}

    public int numberOfSegments() {return segments.size();}
}
