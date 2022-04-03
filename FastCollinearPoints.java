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

public class FastCollinearPoints {
    // Storing line segments connecting collinear points.
    private ArrayList<LineSegment> lines = new ArrayList<>();

    /**
     * Constructor that takes in an array of Points. Processes the slopes of
     * all the points relative to the current point. Then, sorts the slopes and
     * checks to see if there are slopes of equal magnitude and if there are 4+
     * points with this slope. If there are, the {@code LineSegment} from the
     * current point to the last is stored in {@code lines}.
     *
     * @param points array of Points
     */
    public FastCollinearPoints(Point[] points) {
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

        /*
         * For each of the points, calculate the slope and find lines that
         * connect 4+ points with the same slope.
         */
        for (int a = 0; a < copy.length - 3; a++) {
            // Sort Points array in ascending order (see Points.compareTo).
            Arrays.sort(copy);
            // Sort the points based off their slopes to point copy[a].
            Arrays.sort(copy, copy[a].slopeOrder());

            for (int first = 1, last = 2; last < copy.length; last++) {
                // Keep going if the slopes are the same
                while (last < copy.length
                        && Double.compare(copy[0].slopeTo(copy[first]),
                                          copy[0].slopeTo(copy[last])) == 0)
                    last++;

                // Check to see if there are 4+ points in the line, If so,
                // add it.
                if (last - first >= 3 && copy[0].compareTo(copy[first]) < 0)
                    lines.add(new LineSegment(copy[0], copy[last - 1]));

                first = last;
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

            FastCollinearPoints collinear = new FastCollinearPoints(points);
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
                FastCollinearPoints collinear = new FastCollinearPoints(points);

                StdOut.println("Result from numberOfSegments(): " + collinear.numberOfSegments());
                StdOut.println("Result from segments().length: " + collinear.segments().length);
                StdOut.println("---");
            }

            /* Test null argument to constructor */
            try {
                FastCollinearPoints collinear = new FastCollinearPoints(null);
                StdOut.println("Successfully created FastCollinearPoints with null argument.");
            }
            catch (IllegalArgumentException illegalArgumentException) {
                StdOut.println(
                        "Passing null to FastCollinearPoints gives exception: IllegalArgumentException.");
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
                FastCollinearPoints collinear = new FastCollinearPoints(points);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                StdOut.println(
                        "Passing array with null to FastCollinearPoints gives exception: IllegalArgumentException.");
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
                FastCollinearPoints collinear = new FastCollinearPoints(points);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                StdOut.println(
                        "Passing array with duplicate points to FastCollinearPoints gives exception: IllegalArgumentException.");
            }
        }
    }
}
