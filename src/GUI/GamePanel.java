package GUI;

import javax.swing.*;
import java.awt.*;

/*
 * GamePanel is dedicated to drawing.
 */

public class GamePanel extends JPanel {
    private final GameController controller;

    public GamePanel(GameController controller) {
        this.controller = controller;
        this.setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Adjusted to call the correctly named method
        controller.draw(g, this.getWidth(), this.getHeight());
    }
}

