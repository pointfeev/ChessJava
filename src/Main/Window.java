package Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class Window {
    public final JFrame component;
    private final Board board;

    public Window() {
        component = new JFrame("Chess");
        component.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        component.setResizable(false);
        component.setVisible(true);

        board = new Board(this);

        // TODO: add turn display?
        // TODO: add turn move log? with proper "a2 to a4" translation?

        component.pack();
    }

    public int getMaxBoardSize() {
        // TODO: account for turn display and/or move log?
        return getMaxSize();
    }

    public int getMaxSize() {
        Rectangle maxWindowBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        int maxWidth = maxWindowBounds.width;
        int maxHeight = maxWindowBounds.height;
        Insets insets = component.getInsets();
        if (insets != null) {
            maxWidth -= insets.left + insets.right;
            maxHeight -= insets.top + insets.bottom;
        }
        return Math.min(maxWidth, maxHeight);
    }

    public Icon getIcon(String name, int size) {
        try {
            URL url = getClass().getResource("..\\Icons\\" + name + ".png");
            Image image = ImageIO.read(Objects.requireNonNull(url));
            return new ImageIcon(image.getScaledInstance(size, size, Image.SCALE_SMOOTH));
        } catch (NullPointerException | IOException e) {
            throw new RuntimeException("Missing icon '" + name + "'");
        }
    }
}