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

        System.out.println("paintComponent called");

        controller.draw(g, this.getWidth(), this.getHeight());
    }
}

