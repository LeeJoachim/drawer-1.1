import java.awt.Graphics;
import java.awt.Polygon;

public class FigureCalc {

    static void drawRect(Graphics g, int x1, int y1, int x2, int y2) {
        int minX = Math.min(x1, x2);
        int minY = Math.min(y1, y2);
        int width = Math.abs(x1-x2);
        int height = Math.abs(y1-y2);
        g.drawRect(minX, minY, width, height);
    }

    static void drawLine(Graphics g, int x1, int y1, int x2, int y2) {
        g.drawLine(x1, y1, x2, y2);
    }

    static Polygon newPolygonBox(int x1, int y1, int x2, int y2) {

        /* region mechanism */
        if (x1 > x2) {
            int temp = x1;
            x1 = x2;
            x2 = temp;
        }
        if (y1 > y2) {
            int temp = y1;
            y1 = y2;
            y2 = temp;
        }

        int[] xPoints = new int[4];
        int[] yPoints = new int[4];

        xPoints[0] = x1;
        yPoints[0] = y1;
        xPoints[1] = x2;
        yPoints[1] = y1;
        xPoints[2] = x2;
        yPoints[2] = y2;
        xPoints[3] = x1;
        yPoints[3] = y2;
        
        /* send coordinates for make polygon box */
        return new Polygon(xPoints, yPoints, 4);
        /**/
    }

    static Polygon newPolygonLine(int boxWidth, int x1, int y1, int x2, int y2) {
        int regionWidth = boxWidth; // control this!

        int x = x1;
        int y = y1;
        int width = x2 - x1;
        int height = y2 - y1;

        /* algorithm for making inclined polygon */
        /* do not touch! */
        int sign_h = 1;
        if (height < 0) sign_h = -1;
        double angle;
        double theta = (width!=0) ? Math.atan((double)(height)/(double)(width)) : sign_h*Math.PI/2.;
        if (theta < 0) theta = theta + 2 * Math.PI;
        angle = (theta + Math.PI / 2.);
        int dx = (int)(regionWidth * Math.cos(angle));
        int dy = (int)(regionWidth * Math.sin(angle));
        int xpoints[] = new int[4];
        int ypoints[] = new int[4];
        xpoints[0] = x + dx;     ypoints[0] = y + dy;
        xpoints[1] = x - dx;     ypoints[1] = y - dy;
        xpoints[2] = x + width - dx; ypoints[2] = y + height - dy;
        xpoints[3] = x + width + dx; ypoints[3] = y + height + dy;
        return new Polygon(xpoints, ypoints,4);
        /**/
    }
    static boolean isInPolygon(View view, int x, int y) {
        for (Figure i : view.figures) {
            if (i.isInside(x, y)) {
                return true;
            }
        }
        return false;
    }
    static Figure getPolygonIf(View view, int x, int y) {
        for (Figure i : view.figures) {
            if (i.isInside(x, y)) {
                return i;
            }
        }
        return null;
    }

    static void drawOval(Graphics g, int x1, int y1, int x2, int y2) {
        int minX = Math.min(x1, x2);
        int minY = Math.min(y1, y2);
        int width = Math.abs(x1-x2);
        int height = Math.abs(y1-y2);
        g.drawOval(minX, minY, width, height);
    }

    static void drawDot(Graphics g, int x1, int y1, int gap) {
        g.drawOval(x1-gap, y1-gap, 2*gap, 2*gap);
        g.fillOval(x1-gap, y1-gap, 2*gap, 2*gap);
    }

    static Polygon newPolygonDot(int x1, int y1, int gap) {
            /* region mechanism */

            int[] xPoints = new int[4];
            int[] yPoints = new int[4];
    
            xPoints[0] = x1-gap;
            yPoints[0] = y1-gap;
            xPoints[1] = x1+gap;
            yPoints[1] = y1-gap;
            xPoints[2] = x1+gap;
            yPoints[2] = y1+gap;
            xPoints[3] = x1-gap;
            yPoints[3] = y1+gap;
            
            /* send coordinates for make polygon box */
            return new Polygon(xPoints, yPoints, 4);
            /**/
    }

}