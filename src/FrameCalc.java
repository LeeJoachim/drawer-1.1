import java.awt.*;

public class FrameCalc {
    static int getCenterX(int width) {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        return (dim.width - width) / 2;
    }
    static int getCenterY(int height) {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        return (dim.height - height) / 2;
    }
}