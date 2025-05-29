package ua.edu.ukma.Zhytnetsky;

import ua.edu.ukma.Zhytnetsky.libs.princeton.StdDraw;
import ua.edu.ukma.Zhytnetsky.libs.princeton.StdIn;
import ua.edu.ukma.Zhytnetsky.utils.DataConstants;
import ua.edu.ukma.Zhytnetsky.utils.Point2D;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Stack;

public final class Main {

    public static void main(String[] args) {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenRadius(POINT_RADIUS);

        BufferedInputStream in;
        try {
            in = new BufferedInputStream(new FileInputStream(DataConstants.RS_1423));
            System.setIn(in);
        }
        catch (FileNotFoundException ignored) {
            System.exit(1);
        }

        final int pointCount = StdIn.readInt();
        final Point2D[] points = new Point2D[pointCount];
        int i = 0;
        while (!StdIn.isEmpty()) {
            final int x = StdIn.readInt();
            final int y = StdIn.readInt();
            points[i++] = new Point2D(x, y);
        }

        final Point2D minPoint = points[minPointByY(points)];
        Arrays.sort(points, minPoint.POLAR_ORDER);

        final Stack<Point2D> convexHullPoints = convexHull(points);
        StdDraw.setPenRadius(LINE_RADIUS);
        for (int j = 0; j < convexHullPoints.size(); j++) {
            if (j == convexHullPoints.size() - 1) {
                convexHullPoints.get(j).drawTo(convexHullPoints.get(0));
            }
            else convexHullPoints.get(j).drawTo(convexHullPoints.get(j+1));
        }
    }

    private static int minPointByY(final Point2D[] points) {
        if (points.length == 0) {
            throw new IllegalArgumentException("ERROR: No points given as inputs");
        }
        int currentMin = 0;
        for (int i = 1; i < points.length; i++) {
            if (points[i].compareTo(points[currentMin]) < 0) currentMin = i;
        }
        return currentMin;
    }

    private static Stack<Point2D> convexHull(final Point2D[] points) {
        if (points.length < 3) {
            throw new IllegalArgumentException("ERROR: Not enough points to form the convex hull");
        }
        final Stack<Point2D> result = new Stack<>();
        result.push(points[0]);
        result.push(points[1]);
        points[0].draw();
        points[1].draw();

        for (int i = 2; i < points.length; i++) {
            while (result.size() > 1 && Point2D.ccw(result.get(result.size() - 2), result.peek(), points[i]) <= 0) {
                result.pop();
            }
            result.push(points[i]);
            points[i].draw();
        }

        return result;
    }

    private static final double POINT_RADIUS = 0.01;
    private static final double LINE_RADIUS = 0.0035;

}
