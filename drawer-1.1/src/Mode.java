
import java.awt.*;
import java.awt.event.*;

abstract class Mode {
    View view;
    boolean isPopupOpen = false; // because : not working popup trigger
    RightClickPopup menu;

    public Mode(View view) {
        this.view = view;
    }
    abstract void mouseReleased(MouseEvent e);
    abstract void mouseDragged(MouseEvent e);
    abstract void mousePressed(MouseEvent e);
    abstract void mouseMoved(MouseEvent e);
    public boolean isHandCursor() {
        return view.getCursor().getType() == Cursor.HAND_CURSOR;
    }

}

class MovingMode extends Mode {

    int PGX, PGY;

    public MovingMode(View view) {
        super(view);
    }
    @Override
    void mouseMoved(MouseEvent e) {
        changeCursorIf(e.getX(), e.getY(), Cursor.HAND_CURSOR);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.isPopupTrigger()) {
            isPopupOpen = true;
            if (isHandCursor()) {
                menu = new OnFigurePopup(view, e);
                menu.show(view, e.getX(), e.getY());
                return;
            } else {
                menu = new OnCanvasPopup(view, e);
                menu.show(view, e.getX(), e.getY());
                return;
            }
        }

        if (isHandCursor()) {
            PGX = e.getX(); PGY = e.getY();
            view.setFigure(FigureCalc.getPolygonIf(view, PGX, PGY));
            view.figures.remove(view.figure);
        }
    }
    public void mouseDragged(MouseEvent e) {
        if (e.isPopupTrigger()) return;

        if (isHandCursor()) {
            int x = e.getX();
            int y = e.getY();

            Graphics g = view.getGraphics();
            g.setXORMode(view.getBackground()); 
            view.figure.moveXOR(g, x - PGX, y - PGY); // += dx, dy
            PGX = x; PGY = y;
        }
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        if (isPopupOpen) { // because : not working popup trigger
            isPopupOpen = false;
            return;
        }

        if (isHandCursor()) {
            view.addFigures();
        }   
    }
    private void changeCursorIf(int x, int y, int cursorType) {
        if (FigureCalc.isInPolygon(view, x, y)) {
            view.setCursor(Cursor.getPredefinedCursor(cursorType));
        } else {
            view.setCursor(Cursor.getDefaultCursor());
        }
    }

}

class DrawingMode extends Mode {

    private boolean isPopupOpen;

    public DrawingMode(View view) {
        super(view);
    }

    public void mouseDragged(MouseEvent e) {
        if (e.isPopupTrigger()) return;

        Graphics g = view.getGraphics();
        g.setXORMode(view.getBackground());
        view.figure.drawXOR(g, e.getX(), e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.isPopupTrigger()) {
            isPopupOpen = true;
            if (isHandCursor()) {
                menu = new OnFigurePopup(view, e);
                menu.show(view, e.getX(), e.getY());
                return;
            } else {
                menu = new OnCanvasPopup(view, e);
                menu.show(view, e.getX(), e.getY());
                return;
            }
        }
        view.figure.setAll(e.getX(), e.getY(), e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isPopupOpen) { // because : not working popup trigger
            isPopupOpen = false;
            return;
        }

        view.figure.setX2Y2(e.getX(), e.getY());
        view.addFigures();
    }

    @Override
    void mouseMoved(MouseEvent e) { }

}
