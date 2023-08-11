import java.awt.event.MouseEvent;

import javax.swing.*;

public class RightClickPopup {
    JPopupMenu menu = new JPopupMenu();

    void show(View view, int x, int y) {
        menu.show(view, x, y);
    }
}

class OnCanvasPopup extends RightClickPopup {

    OnCanvasPopup(View view, MouseEvent e) {

        JMenuItem movingItem = new JMenuItem("Moving");
        menu.add(movingItem);
        movingItem.addActionListener(evt -> {
            view.setMode(new MovingMode(view));
        });

        JMenuItem drawingItem = new JMenuItem("Drawing");
        menu.add(drawingItem);
        drawingItem.addActionListener(evt -> {
            view.setMode(new DrawingMode(view));
        });

        menu.addSeparator();

        JMenuItem boxItem = new JMenuItem("Box");
        menu.add(boxItem);
        boxItem.addActionListener(evt -> {
            view.setFigure(new Box());
            view.setMode(new DrawingMode(view));
        });

        JMenuItem lineItem = new JMenuItem("Line");
        menu.add(lineItem);
        lineItem.addActionListener(evt -> {
            view.setFigure(new Line());
            view.setMode(new DrawingMode(view));
        });

        JMenuItem circleItem = new JMenuItem("Circle");
        menu.add(circleItem);
        circleItem.addActionListener(evt -> {
            view.setFigure(new Circle());
            view.setMode(new DrawingMode(view));
        });

        JMenuItem dotItem = new JMenuItem("Dot");
        menu.add(dotItem);
        dotItem.addActionListener(evt -> {
            view.setFigure(new Dot());
            view.setMode(new DrawingMode(view));
        });
    }
}

class OnFigurePopup extends RightClickPopup {

    OnFigurePopup(View view, MouseEvent e) {
        JMenuItem deleteItem = new JMenuItem("Delete");
        menu.add(deleteItem);
        deleteItem.addActionListener(evt -> {
            view.figures.remove(FigureCalc.getPolygonIf(view, e.getX(), e.getY()));
            view.repaint();

        });
    }
}

