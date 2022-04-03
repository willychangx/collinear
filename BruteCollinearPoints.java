/* *****************************************************************************
 *  Name: Willy Chang
 *  Date: 10/4/2021
 *  Description: BruteCollinearPoints
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    // Storing line segments connecting collinear points.
    private ArrayList<LineSegment> lines = new ArrayList<>();

    /**
     * Constructor that takes in an array of Points. Processes 4 points at a time
     * to determine if the 4 points are collinear. If they are, store them in
     * {@code lines}.
     *
     * @param points array of Points
     */
    public BruteCollinearPoints(Point[] points) {
        if ((points == null) || (points.length == 0))
            throw new IllegalArgumentException("No points given.");

        Point[] copy = points.clone();

        // Check Points array to see if there are null points or duplicates.
        for (int a = 0; a < copy.length - 1; a++) {
            if (copy[a] == null) {
                throw new IllegalArgumentException("Point is null.");
            }
            for (int b = a + 1; b < copy.length; b++) {
                if (copy[b] == null) {
                    throw new IllegalArgumentException("Point is null.");
                }

                if (copy[a].compareTo(copy[b]) == 0) {
                    throw new IllegalArgumentException("Duplicate points found.");
                }
            }
        }

        // Organize the points so that they are ascending order
        // (see Points.compareTo).
        Arrays.sort(copy);

        /*
         * For every combination of 4 points, check the slopes between a-b, a-c,
         * and a-d. If the slopes are the same, then add the {@code LineSegment}
         * from points {@code copy[a]} and {copy[d]} to the {@code lines} array.
         */
        for (int a = 0; a < copy.length - 3; a++) {
            for (int b = a + 1; b < copy.length - 2; b++) {
                double slope1 = copy[a].slopeTo(copy[b]);

                for (int c = b + 1; c < copy.length - 1; c++) {
                    double slope2 = copy[a].slopeTo(copy[c]);

                    if (slope1 == slope2) {
                        for (int d = c + 1; d < copy.length; d++) {
                            double slope3 = copy[a].slopeTo(copy[d]);

                            if (slope1 == slope3) {
                                lines.add(new LineSegment(copy[a], copy[d]));
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Return the number of line segments that contain collinear points.
     *
     * @return size of the {@code lines} array
     */
    public int numberOfSegments() {
        return lines.size();
    }

    /**
     * Return an array of the LineSegments in {@code lines}.
     *
     * @return an array of the LineSegments in {@code lines}
     */
    public LineSegment[] segments() {
        return lines.toArray(new LineSegment[lines.size()]);
    }

    public static void main(String[] args) {
        if (args.length != 0) {
            In in = new In(args[0]);
            int n = in.readInt();
            Point[] points = new Point[n];
            for (int i = 0; i < n; i++) {
                int x = in.readInt();
                int y = in.readInt();
                points[i] = new Point(x, y);
            }

            StdDraw.enableDoubleBuffering();
            StdDraw.setXscale(0, 32768);
            StdDraw.setYscale(0, 32768);
            for (Point p : points) {
                p.draw();
            }
            StdDraw.show();

            BruteCollinearPoints collinear = new BruteCollinearPoints(points);
            for (LineSegment segment : collinear.segments()) {
                StdOut.println(segment);
                segment.draw();
            }
            StdDraw.show();
        }
        else {
            /* Test numberOfSegments() vs. segments() */
            String[] testFiles = {
                    "input8.txt", "equidistant.txt", "input40.txt", "input48.txt",
                    "horizontal5.txt", "vertical5.txt", "random23.txt"
            };
            for (int i = 0; i < testFiles.length; i++) {
                StdOut.println(testFiles[i]);
                In in = new In(testFiles[i]);
                int n = in.readInt();
                Point[] points = new Point[n];
                for (int j = 0; j < n; j++) {
                    int x = in.readInt();
                    int y = in.readInt();
                    points[j] = new Point(x, y);
                }
                BruteCollinearPoints collinear = new BruteCollinearPoints(points);

                StdOut.println("Result from numberOfSegments(): " + collinear.numberOfSegments());
                StdOut.println("Result from segments().length: " + collinear.segments().length);
                StdOut.println("---");
            }

            /* Test null argument to constructor */
            try {
                BruteCollinearPoints collinear = new BruteCollinearPoints(null);
                StdOut.println("Successfully created BruteCollinearPoints with null argument.");
            }
            catch (IllegalArgumentException illegalArgumentException) {
                StdOut.println(
                        "Passing null to BruteCollinearPoints gives exception: IllegalArgumentException.");
            }

            /* Test null in argument array to constructor */
            try {
                In in = new In(testFiles[0]);
                int n = in.readInt();
                Point[] points = new Point[n + 1];
                for (int i = 0; i < n; i++) {
                    int x = in.readInt();
                    int y = in.readInt();
                    points[i] = new Point(x, y);
                }
                points[n] = null;
                BruteCollinearPoints collinear = new BruteCollinearPoints(points);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                StdOut.println(
                        "Passing array with null to BruteCollinearPoints gives exception: IllegalArgumentException.");
            }

            /* Test duplicate points in argument to constructor */
            try {
                In in = new In(testFiles[0]);
                int n = in.readInt();
                Point[] points = new Point[n + 1];
                for (int i = 0; i < n; i++) {
                    int x = in.readInt();
                    int y = in.readInt();
                    points[i] = new Point(x, y);
                    if (i == 0)
                        points[n] = new Point(x, y);
                }
                BruteCollinearPoints collinear = new BruteCollinearPoints(points);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                StdOut.println(
                        "Passing array with duplicate points to BruteCollinearPoints gives exception: IllegalArgumentException.");
            }
        }
    }
}
