import javax.swing.*;

import java.awt.*;
import java.awt.event.*;


class FigureDialog extends JDialog {
    View view;

    class DialogPanel extends JPanel implements ActionListener {
        /* layout
        * flow layout manager : in order and default
        * border layout manager : east, west, north, south
        * grid layout manager : 2x2 array
        * hard coded layout : directly write coordinate numbers
        */
        // FigureDialog figureDialog; // reference for : setVisible(false);

        JTextField x1Field;
        JTextField y1Field;
        JTextField x2Field;
        JTextField y2Field;
        String[] strings = {"box", "line"};
        JComboBox<String> figures;

        DialogPanel() {
            // this.setLayout(null); // hard coded layout
            this.setLayout(new GridLayout(6, 2)); // grid layout

            /* [label :] [ text field ] */
            /* x1 */
            JLabel x1Label = new JLabel(" x1: ");
            x1Label.setFont(new Font("monospace", Font.BOLD, 16));
            x1Label.setHorizontalAlignment(SwingConstants.CENTER);
            add(x1Label);
            /* x1 : [???] */
            x1Field = new JTextField();
            x1Field.setHorizontalAlignment(SwingConstants.CENTER);
            add(x1Field);
            /* y1 */
            JLabel y1Label = new JLabel(" y1: ");
            y1Label.setFont(new Font("monospace", Font.BOLD, 16));
            y1Label.setHorizontalAlignment(SwingConstants.CENTER);
            add(y1Label);
            /* y1 : [???] */
            y1Field = new JTextField();
            y1Field.setHorizontalAlignment(SwingConstants.CENTER);
            add(y1Field);
            /* x2 */
            JLabel x2Label = new JLabel(" x2: ");
            x2Label.setFont(new Font("monospace", Font.BOLD, 16));
            x2Label.setHorizontalAlignment(SwingConstants.CENTER);
            add(x2Label);
            /* x2 : [???] */
            x2Field = new JTextField();
            x2Field.setHorizontalAlignment(SwingConstants.CENTER);
            add(x2Field);
            /* y2 */
            JLabel y2Label = new JLabel(" y2: ");
            y2Label.setFont(new Font("monospace", Font.BOLD, 16));
            y2Label.setHorizontalAlignment(SwingConstants.CENTER);
            add(y2Label);
            /* y2 : [???] */
            y2Field = new JTextField();
            y2Field.setHorizontalAlignment(SwingConstants.CENTER);
            add(y2Field);
            /**/

            /* space */
            JLabel space = new JLabel("");
            add(space);
            /* JComboBox {rectangle, line} */
            figures = new JComboBox<String>(strings);
            add(figures);
            /**/

            /* JButton ok */
            JButton ok = new JButton("ok");
            ok.addActionListener(this);
            add(ok);
            /* JButton cancel */
            JButton cancel = new JButton("cancel");
            cancel.addActionListener(this);
            add(cancel);
            /**/
        }

        private void onOK() {
            int x1, y1, x2, y2;
            
            String selection = (String)this.figures.getSelectedItem();
            try {
                x1 = Integer.parseInt(this.x1Field.getText());
                y1 = Integer.parseInt(this.y1Field.getText());
                x2 = Integer.parseInt(this.x2Field.getText());
                y2 = Integer.parseInt(this.y2Field.getText());
                
            } catch (Exception e) {
                System.out.println("Invalid text field, try again.");
                return;
            }
            Figure nowFigure = null;
            if (selection.equals("box")) {
                nowFigure = new Box();
                nowFigure.setAll(x1, y1, x2, y2);
            } else if (selection.equals("line")) {
                nowFigure = new Line();
                nowFigure.setAll(x1, y1, x2, y2);
            }
            /* add collection obj and repaint*/
            view.setFigure(nowFigure);
            view.addFigures();
            /**/

            /* text reset "" */
            x1Field.setText("");
            y1Field.setText("");
            x2Field.setText("");
            y2Field.setText("");
            /**/
        }
        private void onCancel() {
            FigureDialog.this.setVisible(false);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String name = e.getActionCommand();
            if (name.equals("ok")) {
                onOK();
            } else if (name.equals("cancel")) {
                onCancel();
            }
        }

    }

    FigureDialog(String title, View view) {
        super();
        this.view = view;
        setTitle(title);
        setSize(300, 200);

        int x = FrameCalc.getCenterX(getWidth());
        int y = FrameCalc.getCenterY(getHeight());
        setLocation(x, y);

        Container container = getContentPane();
        JPanel panel = new DialogPanel();
        container.add(panel);

    }
}
