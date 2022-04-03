/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param x the <em>x</em>-coordinate of the point
     * @param y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        if (that == null)
            throw new NullPointerException();

        if ((this.x == that.x) && (this.y == that.y))
            return Double.NEGATIVE_INFINITY;

        double bot = this.x - that.x;

        if (bot == 0)
            return Double.POSITIVE_INFINITY;

        if (this.y == that.y)
            return +0.0;

        return (double) (this.y - that.y) / bot;
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     * point (x0 = x1 and y0 = y1);
     * a negative integer if this point is less than the argument
     * point; and a positive integer if this point is greater than the
     * argument point
     */
    public int compareTo(Point that) {
        if (that == null)
            throw new NullPointerException();
        if (this.x == that.x && this.y == that.y)
            return 0;
        if (this.y > that.y || ((this.y == that.y) && (this.x > that.x)))
            return 1;
        return -1;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return new PointComparator();
    }

    /**
     * Private class to create a custom comparator on Points.
     */
    private class PointComparator implements Comparator<Point> {

        /**
         * Override the comparison operator to compare two Points.
         *
         * @param p1 first point
         * @param p2 second point
         * @return comparing the slopes of the two points
         */
        @Override
        public int compare(Point p1, Point p2) {
            return Double.compare(slopeTo(p1), slopeTo(p2));
        }
    }

    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        /* Test 1 - Positive INF Slope */
        StdOut.println("Test 1 - Positive INF Slope");
        for (int i = 0; i < 10; i++) {
            int x = (int) (Math.random() * 32768);
            int y1 = (int) (Math.random() * 32768);
            int y2 = (int) (Math.random() * 32768);

            Point p = new Point(x, y1);
            Point q = new Point(x, y2);

            double result = p.slopeTo(q);
            StdOut.println("PASSED! " + p.toString() + " -> "
                                   + q.toString() + ": " + result);

            if (result != Double.POSITIVE_INFINITY)
                StdOut.println("FAILED! " + p.toString() + " -> "
                                       + q.toString() + ": " + result);
        }

        /* Test 1 - Negative INF Slope */
        StdOut.println("Test 1 - Negative INF Slope");
        for (int i = 0; i < 10; i++) {
            int x = (int) (Math.random() * 32768);
            int y = (int) (Math.random() * 32768);

            Point p = new Point(x, y);
            Point q = new Point(x, y);

            double result = p.slopeTo(q);
            StdOut.println("PASSED! " + p.toString() + " -> "
                                   + q.toString() + ": " + result);

            if (result != Double.NEGATIVE_INFINITY)
                StdOut.println("FAILED! " + p.toString() + " -> "
                                       + q.toString() + ": " + result);
        }

        /* Test 1 - Positive Zero Slope */
        StdOut.println("Test 1 - Positive Zero Slope");
        for (int i = 0; i < 10; i++) {
            int x = (int) (Math.random() * 32768);
            int x2 = (int) (Math.random() * 32768);
            int y = (int) (Math.random() * 32768);

            Point p = new Point(x, y);
            Point q = new Point(x2, y);

            double result = p.slopeTo(q);
            StdOut.println("PASSED! " + p.toString() + " -> "
                                   + q.toString() + ": " + result);

            if (result != 0)
                StdOut.println("FAILED! " + p.toString() + " -> "
                                       + q.toString() + ": " + result);
        }

        /* Test 1 - Symmetric */
        StdOut.println("Test 1 - Symmetric");
        for (int i = 0; i < 10; i++) {
            int x = (int) (Math.random() * 32768);
            int x2 = (int) (Math.random() * 32768);
            int y = (int) (Math.random() * 32768);
            int y2 = (int) (Math.random() * 32768);

            Point p = new Point(x, y);
            Point q = new Point(x2, y2);

            double slope1 = p.slopeTo(q);
            double slope2 = q.slopeTo(p);
            if (slope1 == slope2)
                StdOut.println("PASSED! " + p.toString() + " and "
                                       + q.toString() + " have the same slope.");
            else
                StdOut.println("FAILED! " + p.toString() + " and "
                                       + q.toString() + " have different slopes.");
        }

        /* Test 1 - toSlope() */
        StdOut.println("Test 1 - toSlope()");
        for (int i = 0; i < 10; i++) {
            int x = (int) (Math.random() * 32768);
            int x2 = (int) (Math.random() * 32768);
            int y = (int) (Math.random() * 32768);
            int y2 = (int) (Math.random() * 32768);

            Point p = new Point(x, y);
            Point q = new Point(x2, y2);

            try {
                double result = p.slopeTo(q);
                StdOut.println("PASSED! " + p.toString() + " -> "
                                       + q.toString() + ": " + result);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                StdOut.println("FAILED! " + p.toString() + " -> " + q.toString());
            }
        }

        /* Test 1 - java.lang.NullPointerException */
        StdOut.println("Test 1 - NullPointerException");
        try {
            Point p = new Point(0, 0);
            p.slopeTo(null);
            StdOut.println("FAILED! Null argument passed.");
        }
        catch (NullPointerException nullPointerException) {
            StdOut.println(
                    "PASSED! Passing null to slopeTo gives exception: NullPointerException.");
        }

        /* Test 2 - Reflexive */
        StdOut.println("Test 2 - Reflexive");
        for (int i = 0; i < 10; i++) {
            int x = (int) (Math.random() * 32768);
            int y = (int) (Math.random() * 32768);

            Point p = new Point(x, y);

            try {
                double result = p.compareTo(p);
                StdOut.println("PASSED! p = " + p.toString() + ": " + result);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                StdOut.println("FAILED! No relation for p = " + p.toString());
            }
        }

        /* Test 2 - Antisymmetric */
        StdOut.println("Test 2 - Antisymmetric");
        for (int i = 0; i < 10; i++) {
            int x = (int) (Math.random() * 32768);
            int x2 = (int) (Math.random() * 32768);
            int y = (int) (Math.random() * 32768);
            int y2 = (int) (Math.random() * 32768);

            Point p = new Point(x, y);
            Point q = new Point(x2, y2);

            double result = p.compareTo(q);
            double result2 = q.compareTo(p);
            if (result != 0) {
                if (result != result2)
                    StdOut.println("PASSED! Relation between " + p.toString()
                                           + " and " + q.toString() + " are antisymmetric.");
                else
                    StdOut.println("FAILED! Relation between " + p.toString()
                                           + " and " + q.toString() + " are symmetric.");
            }
        }

        /* Test 2 - Transitive */
        StdOut.println("Test 2 - Transitive");
        for (int i = 0; i < 10; i++) {
            int x = (int) (Math.random() * 32768);
            int x2 = (int) (Math.random() * 32768);
            int x3 = (int) (Math.random() * 32768);
            int y = (int) (Math.random() * 32768);
            int y2 = (int) (Math.random() * 32768);
            int y3 = (int) (Math.random() * 32768);

            Point p = new Point(x, y);
            Point q = new Point(x2, y2);
            Point r = new Point(x3, y3);

            try {
                double result = p.compareTo(q);
                double result2 = q.compareTo(r);
                double result3 = p.compareTo(r);

                StdOut.println("PASSED! " + p.toString() + " -> "
                                       + q.toString() + " -> "
                                       + r.toString() + ": "
                                       + result + ", "
                                       + result2 + ", "
                                       + result3);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                StdOut.println("Failed! " + p.toString() + " -> "
                                       + q.toString() + " -> "
                                       + r.toString());
            }
        }

        /* Test 2 - compareTo() */
        StdOut.println("Test 2 - compareTo()");
        for (int i = 0; i < 10; i++) {
            int x = (int) (Math.random() * 32768);
            int x2 = (int) (Math.random() * 32768);
            int y = (int) (Math.random() * 32768);
            int y2 = (int) (Math.random() * 32768);

            Point p = new Point(x, y);
            Point q = new Point(x2, y2);

            try {
                double result = p.compareTo(q);
                StdOut.println("PASSED! " + p.toString() + " -> "
                                       + q.toString() + ": " + result);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                StdOut.println("FAILED! " + p.toString() + " -> " + q.toString());
            }
        }

        /* Test 2 - java.lang.NullPointerException */
        StdOut.println("Test 2 - NullPointerException");
        try {
            Point p = new Point(0, 0);
            p.compareTo(null);
            StdOut.println("FAILED! Null argument passed.");
        }
        catch (NullPointerException nullPointerException) {
            StdOut.println(
                    "PASSED! Passing null to compareTo gives exception: NullPointerException.");
        }

        /* Test 3 - Reflexive */
        StdOut.println("Test 3 - Reflexive");
        for (int i = 0; i < 10; i++) {
            int x = (int) (Math.random() * 32768);
            int y = (int) (Math.random() * 32768);

            Point p = new Point(x, y);

            try {
                double result = p.slopeOrder().compare(p, p);
                StdOut.println("PASSED! p = " + p.toString() + ": " + result);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                StdOut.println("FAILED! No relation for p = " + p.toString());
            }
        }

        /* Test 3 - Antisymmetric */
        StdOut.println("Test 3 - Antisymmetric");
        for (int i = 0; i < 10; i++) {
            int x = (int) (Math.random() * 32768);
            int x2 = (int) (Math.random() * 32768);
            int x3 = (int) (Math.random() * 32768);
            int y = (int) (Math.random() * 32768);
            int y2 = (int) (Math.random() * 32768);
            int y3 = (int) (Math.random() * 32768);

            Point p = new Point(x, y);
            Point q = new Point(x2, y2);
            Point r = new Point(x3, y3);

            double result = p.slopeOrder().compare(q, r);
            double result2 = p.slopeOrder().compare(r, q);
            if (result != 0) {
                if (result != result2)
                    StdOut.println("PASSED! Relation between " + q.toString()
                                           + " and " + r.toString() + " are antisymmetric.");
                else
                    StdOut.println("FAILED! Relation between " + q.toString()
                                           + " and " + r.toString() + " are symmetric.");
            }
        }

        /* Test 3 - Transitive */
        StdOut.println("Test 3 - Transitive");
        for (int i = 0; i < 10; i++) {
            int x = (int) (Math.random() * 32768);
            int x2 = (int) (Math.random() * 32768);
            int x3 = (int) (Math.random() * 32768);
            int x4 = (int) (Math.random() * 32768);
            int y = (int) (Math.random() * 32768);
            int y2 = (int) (Math.random() * 32768);
            int y3 = (int) (Math.random() * 32768);
            int y4 = (int) (Math.random() * 32768);

            Point p = new Point(x, y);
            Point q = new Point(x2, y2);
            Point r = new Point(x3, y3);
            Point s = new Point(x4, y4);

            try {
                double result = p.slopeOrder().compare(q, r);
                double result2 = p.slopeOrder().compare(r, s);
                double result3 = p.slopeOrder().compare(q, s);

                StdOut.println("PASSED! " + q.toString() + " -> "
                                       + r.toString() + " -> "
                                       + s.toString() + ": "
                                       + result + ", "
                                       + result2 + ", "
                                       + result3);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                StdOut.println("FAILED! " + q.toString() + " -> "
                                       + r.toString() + " -> "
                                       + s.toString());
            }
        }

        /* Test 3 - compareTo() */
        StdOut.println("Test 3 - slopeOrder().compare(q,r)");
        for (int i = 0; i < 10; i++) {
            int x = (int) (Math.random() * 32768);
            int x2 = (int) (Math.random() * 32768);
            int x3 = (int) (Math.random() * 32768);
            int y = (int) (Math.random() * 32768);
            int y2 = (int) (Math.random() * 32768);
            int y3 = (int) (Math.random() * 32768);

            Point p = new Point(x, y);
            Point q = new Point(x2, y2);
            Point r = new Point(x3, y3);

            try {
                double result = p.slopeOrder().compare(q, r);
                StdOut.println("PASSED! " + p.toString() + " -> " + q.toString() + ", "
                                       + r.toString() + ": " + result);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                StdOut.println("FAILED! " + p.toString() + " -> " + q.toString() + ", "
                                       + r.toString());
            }
        }

        /* Test 3 - java.lang.NullPointerException */
        StdOut.println("Test 3 - NullPointerException");
        try {
            Point p = new Point(0, 0);
            Point q = new Point(100, 100);
            p.slopeOrder().compare(null, q);
            StdOut.println("FAILED! Null argument passed.");
        }
        catch (NullPointerException nullPointerException) {
            StdOut.println(
                    "PASSED! Passing first null to compare() gives exception: NullPointerException.");
        }

        try {
            Point p = new Point(0, 0);
            Point q = new Point(100, 100);
            p.slopeOrder().compare(q, null);
            StdOut.println("FAILED! Null argument passed.");
        }
        catch (NullPointerException nullPointerException) {
            StdOut.println(
                    "PASSED! Passing second null to compare() gives exception: NullPointerException.");
        }
    }
}
