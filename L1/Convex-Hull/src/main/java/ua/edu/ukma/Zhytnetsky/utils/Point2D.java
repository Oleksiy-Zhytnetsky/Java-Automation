package ua.edu.ukma.Zhytnetsky.utils;

import ua.edu.ukma.Zhytnetsky.libs.princeton.StdDraw;

import java.util.Comparator;

public final class Point2D implements Comparable<Point2D> {

    public final Comparator<Point2D> POLAR_ORDER = new PolarOrder();

    public Point2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static int ccw(final Point2D a, final Point2D b, final Point2D c) {
        return (b.x - a.x) * (c.y - a.y) - (c.x - a.x) * (b.y - a.y);
    }

    public void draw() {
        StdDraw.point(this.x, this.y);
    }

    public void drawTo(final Point2D other) {
        StdDraw.line(this.x, this.y, other.x, other.y);
    }

    @Override
    public int compareTo(final Point2D other) {
        if (this.y < other.y) return -1;
        if (this.y == other.y) return Integer.compare(this.x, other.x);
        else return 1;
    }

    @Override
    public boolean equals(final Object other) {
        if (other.getClass() == this.getClass()) {
            return this.x == ((Point2D)other).x && this.y == ((Point2D)other).y;
        }
        else return ((Object)this).equals(other);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /* Private API */
    private final class PolarOrder implements Comparator<Point2D> {
        @Override
        public int compare(final Point2D q1, final Point2D q2) {
            if (q1.compareTo(Point2D.this) > 0 && q2.compareTo(Point2D.this) < 0) {
                return -1;
            }
            if (q1.compareTo(Point2D.this) < 0 && q2.compareTo(Point2D.this) > 0) {
                return 1;
            }

            final double q1Angle = Math.atan2(q1.y - Point2D.this.y, q1.x - Point2D.this.x);
            final double q2Angle = Math.atan2(q2.y - Point2D.this.y, q2.x - Point2D.this.x);
            if (q1Angle < q2Angle) return -1;
            if (q1Angle > q2Angle) return 1;
            return Double.compare(
                    Math.hypot(q1.x - Point2D.this.x, q1.y - Point2D.this.y),
                    Math.hypot(q2.x - Point2D.this.x, q2.y - Point2D.this.y)
            );
        }
    }

    private final int x;
    private final int y;

}
