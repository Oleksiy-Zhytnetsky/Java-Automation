package ua.edu.ukma.Zhytnetsky.utils;

import java.util.Comparator;

public final class Point2DHeadless implements Comparable<Point2DHeadless> {

    public final Comparator<Point2DHeadless> POLAR_ORDER = new PolarOrder();

    public Point2DHeadless(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static int ccw(final Point2DHeadless a, final Point2DHeadless b, final Point2DHeadless c) {
        return (b.x - a.x) * (c.y - a.y) - (c.x - a.x) * (b.y - a.y);
    }

    @Override
    public int compareTo(final Point2DHeadless other) {
        if (this.y < other.y) return -1;
        if (this.y == other.y) return Integer.compare(this.x, other.x);
        else return 1;
    }

    @Override
    public boolean equals(final Object other) {
        if (other.getClass() == this.getClass()) {
            return this.x == ((Point2DHeadless)other).x && this.y == ((Point2DHeadless)other).y;
        }
        else return ((Object)this).equals(other);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /* Private API */
    private final class PolarOrder implements Comparator<Point2DHeadless> {
        @Override
        public int compare(final Point2DHeadless q1, final Point2DHeadless q2) {
            if (q1.compareTo(Point2DHeadless.this) > 0 && q2.compareTo(Point2DHeadless.this) < 0) {
                return -1;
            }
            if (q1.compareTo(Point2DHeadless.this) < 0 && q2.compareTo(Point2DHeadless.this) > 0) {
                return 1;
            }

            final double q1Angle = Math.atan2(q1.y - Point2DHeadless.this.y, q1.x - Point2DHeadless.this.x);
            final double q2Angle = Math.atan2(q2.y - Point2DHeadless.this.y, q2.x - Point2DHeadless.this.x);
            if (q1Angle < q2Angle) return -1;
            if (q1Angle > q2Angle) return 1;
            return Double.compare(
                    Math.hypot(q1.x - Point2DHeadless.this.x, q1.y - Point2DHeadless.this.y),
                    Math.hypot(q2.x - Point2DHeadless.this.x, q2.y - Point2DHeadless.this.y)
            );
        }
    }

    private final int x;
    private final int y;

}
