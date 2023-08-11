import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;

class Frame extends JFrame {

    private View view;
    StatusBar statusBar;

    public Frame() {

        frameSetting();

        // View
        Container container = getContentPane();
        view = new View(this);
        container.add(view);

        // Status Bar setting
        statusBar = new StatusBar();
        container.add(statusBar, "South");

        super.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                super.componentResized(e);
                int width = e.getComponent().getWidth();
                int height = e.getComponent().getHeight();
                statusBar.setViewSize(width + " x " + height);
            }
        });

        // Tool Bar 
        JToolBar toolBar = new JToolBar();
        toolBar.add(new JButton("Moving") {
            { addActionListener((e) -> {
                view.setMode(new MovingMode(view));
                });
            }
        });
        toolBar.add(new JButton("Drawing") {
            { addActionListener((e) -> {
                view.setMode(new DrawingMode(view));
                });
            }
        });

        toolBar.addSeparator();

        toolBar.add(new JButton("Line") {
            { addActionListener((e) -> {
                view.setFigure(new Line());
                view.setMode(new DrawingMode(view));
                });
            }
        });
        toolBar.add(new JButton("Box") {
            { addActionListener((e) -> {
                view.setFigure(new Box());
                view.setMode(new DrawingMode(view));
                });
            }
        });
        toolBar.add(new JButton("Circle") {
            { addActionListener((e) -> {
                view.setFigure(new Circle());
                view.setMode(new DrawingMode(view));                
                });
            }
        });
        toolBar.add(new JButton("Dot") {
            { addActionListener((e) -> {
                view.setFigure(new Dot());
                view.setMode(new DrawingMode(view));
                });
            }
        });

        // Color chooser
        JButton colorChooser = new JButton("Color");
        colorChooser.addActionListener((e) -> {
            view.figure.setColor(JColorChooser.showDialog(null, "Choose a color", view.figure.getColor()));
            setColorType(view.figure.getColor());
        });
        toolBar.add(colorChooser);

        container.add(toolBar, "North");


        // Menu Settings
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
                JMenuItem openItem = new JMenuItem("Open");
                fileMenu.add(openItem);
                JMenuItem saveItem = new JMenuItem("Save");
                fileMenu.add(saveItem);
                JMenuItem exitItem = new JMenuItem("Exit");
                fileMenu.add(exitItem);
                        exitItem.addActionListener((e) -> { System.exit(0); }); 

                

        JMenu figureMenu = new JMenu("Figure");
        menuBar.add(figureMenu);
                JMenuItem lineItem = new JMenuItem("Line");
                figureMenu.add(lineItem);
                lineItem.addActionListener((e) -> {
                    view.setFigure(new Line());
                });
                JMenuItem boxItem = new JMenuItem("Box");
                figureMenu.add(boxItem);
                boxItem.addActionListener((e) -> {
                    view.setFigure(new Box());
                });
                JMenuItem circleItem = new JMenuItem("Circle");
                figureMenu.add(circleItem);
                circleItem.addActionListener((e) -> {
                    view.setFigure(new Circle());
                });
                JMenuItem dotItem = new JMenuItem("Dot");
                figureMenu.add(dotItem);
                dotItem.addActionListener((e) -> {
                    view.setFigure(new Dot());
                });

        JMenu toolMenu = new JMenu("Tool");
        menuBar.add(toolMenu);
        
                JMenuItem modalTool = new JMenuItem("Modal Tool");
                toolMenu.add(modalTool);
                modalTool.addActionListener((e) -> {
                    FigureDialog figureDialog = new FigureDialog("Modal Tool", view);
                    figureDialog.setModal(true);
                    figureDialog.setVisible(true);
                });

        JMenu modeMenu = new JMenu("Mode");
        menuBar.add(modeMenu);
                JMenuItem movingMode = new JMenuItem("Moving Mode");
                modeMenu.add(movingMode);
                movingMode.addActionListener((e) -> {
                    view.setMode(new MovingMode(view));
                });
                JMenuItem drawingMode = new JMenuItem("Drawing Mode");
                modeMenu.add(drawingMode);
                drawingMode.addActionListener((e) -> {
                    view.setMode(new DrawingMode(view));
                });
    
        JMenu helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);

        JMenuItem drawerInfo = new JMenuItem("Drawer Info");
        helpMenu.add(drawerInfo);
        drawerInfo.addActionListener((e) -> {
            JOptionPane.showMessageDialog(null, 
                    "Drawer 1.1 for Macintosh\n\n" + 
                    "Organization: Busan Univ. of Foreign Studies\n" +
                    "Author: Lee Joachim\n" +
                    "Date: Friday, August 11, 2023\n" +
                    "Version: 1.1\n", 
                    "drawer's Info", 
                            JOptionPane.INFORMATION_MESSAGE);
        });

        setVisible(true);

    }

    public void setPosition(int x, int y) {
        statusBar.setPosition(x + " x " + y);
    }

    private void frameSetting() {
        // Frame settings
        setTitle("Frame");
        setSize(800, 800);
        int x = FrameCalc.getCenterX(getWidth());
        int y = FrameCalc.getCenterY(getHeight());
        setLocation(x, y);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setFigureType(String str) {
        statusBar.setFigureType(str);
    }

    public void setModeType(String str) {
        statusBar.setModeType(str);
    }

    public void setColorType(Color color) {
        statusBar.setColorType(color);
    }
}

class StatusBar extends JPanel{
    JTextField position;
    JTextField modeType;
    JTextField figureType;
    JTextField viewSize;
    JLabel colorTypeLabel;
    JPanel colorType;

    public StatusBar() {
        setLayout(new BorderLayout());
        
        position = newTextField("Position", 8);
        modeType = newTextField("DrawingMode", 8);
        figureType = newTextField("Dot", 8);
        colorTypeLabel = new JLabel("Current Color : ");
        colorType = new JPanel();
        colorType.setPreferredSize(new Dimension(30, 20));
        colorType.setBackground(Color.BLACK);
        
        javax.swing.Box westBoxes = javax.swing.Box.createHorizontalBox();
        westBoxes.add(javax.swing.Box.createHorizontalStrut(5));
        westBoxes.add(position);
        westBoxes.add(modeType);
        westBoxes.add(figureType);
        westBoxes.add(colorTypeLabel);
        westBoxes.add(colorType);
        
        add(westBoxes, "West");

        

        viewSize = newTextField("View Size", 8);
        javax.swing.Box eastBoxes = javax.swing.Box.createHorizontalBox();
        eastBoxes.add(viewSize);
        eastBoxes.add(javax.swing.Box.createHorizontalStrut(5));

        add(eastBoxes, "East");
        setBorder(BorderFactory.createEtchedBorder());
    }

    public void setPosition(String s) {
        position.setText(s);
    }
    public void setModeType(String s) {
        modeType.setText(s);
    }
    public void setFigureType(String s) {
        figureType.setText(s);
    }
    public void setViewSize(String s) {
        viewSize.setText(s);
    }
    public void setColorType(Color c) {
        colorType.setBackground(c);
    }


    private JTextField newTextField(String title, int size) {
        JTextField f = new JTextField(title, size);
        f.setEditable(false);
        f.setHorizontalAlignment(JTextField.CENTER);
        return f;
    }
}