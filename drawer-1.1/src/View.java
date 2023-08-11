import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;


class View extends JPanel implements MouseListener, MouseMotionListener {

    Frame frame;
    Figure figure = new Dot();
    public List<Figure> figures = new ArrayList<>();
    Mode mode = new DrawingMode(this);

    public View(Frame frame) {
        this.frame = frame;
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Figure i : figures) {
            g.setColor(i.getColor());
            i.draw(g);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mode.mouseDragged(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        frame.setPosition(e.getX(), e.getY());
        mode.mouseMoved(e);
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        mode.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mode.mouseReleased(e);
    }
    
    public void addFigures() {
        figure.setPolygon();
        figures.add(this.figure);
        this.figure = deepCopy(this.figure);
        repaint();
    }

    private Figure deepCopy(Figure figure) {
        return figure.clone();
    }

    public void setFigure(Figure figure) {
        frame.setFigureType(figure.getClass().getSimpleName());
        frame.setColorType(figure.getColor());

        this.figure = figure;
    }

    public void setMode(Mode mode) {
        frame.setModeType(mode.getClass().getSimpleName());
        this.mode = mode;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    }

}
