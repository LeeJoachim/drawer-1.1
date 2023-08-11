import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

abstract class Figure implements Cloneable{
    protected int x1, y1, x2, y2 = 0;
    protected Polygon polygon;
    protected Color color = Color.black;

    public void setX1(int x1) {
        this.x1 = x1;
    }
    public void setY1(int y1) {
        this.y1 = y1;
    }
    public void setX2(int x2) {
        this.x2 = x2;
    }
    public void setY2(int y2) {
        this.y2 = y2;
    }

    public void setX1Y1(int x1, int y1) {
        setX1(x1); setY1(y1);
    }
    public void setX2Y2(int x2, int y2) {
        setX2(x2); setY2(y2);
    }
    public void setAll(int x1, int y1, int x2, int y2) {
        setX1Y1(x1, y1); setX2Y2(x2, y2);
    }

    public boolean isInside(int x, int y) {
        if (polygon == null) {
            return false;
        }
        return polygon.contains(x, y);
    }

    abstract void draw(Graphics g);
    abstract void drawXOR(Graphics g, int X2, int Y2);
    abstract void setPolygon();

    public void moveXOR(Graphics g, int dx, int dy) {
        draw(g);
        move(dx, dy);
        draw(g);
    }

    private void move(int dx, int dy) {
        x1 += dx; y1 += dy;
        x2 += dx; y2 += dy;
    }
    
    @Override
    public Figure clone() {
        try {
            return (Figure)super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e.toString());
        }
    }
    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }
}

abstract class OnePointFigure extends Figure {
    int gap = 2;
}

class Dot extends OnePointFigure {

    public void draw(Graphics g) {
        FigureCalc.drawDot(g, x1, y1, gap);
    }
    public void setPolygon() {
        polygon = FigureCalc.newPolygonDot(x1, y1, gap);
    }

    @Override
    void drawXOR(Graphics g, int X2, int Y2) {
    }
}

abstract class TwoPointFigure extends Figure {
    public void drawXOR(Graphics g, int X2, int Y2) {
        draw(g);
        setX2Y2(X2, Y2);
        draw(g);
    }
}

class Line extends TwoPointFigure {

    public void draw(Graphics g) {
        FigureCalc.drawLine(g, x1, y1, x2, y2);
    }
    public void setPolygon() {
        polygon = FigureCalc.newPolygonLine(6, x1, y1, x2, y2);   
    }
}


class Box extends TwoPointFigure{

    void draw(Graphics g) {
        FigureCalc.drawRect(g, x1, y1, x2, y2);
    }
    void setPolygon() {
        polygon = FigureCalc.newPolygonBox(x1, y1, x2, y2);
    }

}

class Circle extends TwoPointFigure{

    void draw(Graphics g) {
        FigureCalc.drawOval(g, x1, y1, x2, y2);
    }
    void setPolygon() {
        polygon = FigureCalc.newPolygonBox(x1, y1, x2, y2);
    }

}
