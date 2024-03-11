package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameGUI extends JPanel {
    private GameController controller;
    private GamePanel gamePanel;

    public GameGUI() {
        controller = new GameController();
        initializeGUI();
    }

    private void initializeGUI() {
        setLayout(new BorderLayout());

        gamePanel = new GamePanel(controller);
        add(gamePanel, BorderLayout.CENTER);

        // KeyListener should be added to a component that can gain focus, typically the gamePanel or the JFrame that contains this GameGUI
        setFocusable(true); // Allow this panel to gain focus to receive key events
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                controller.keyPressed(e);
                gamePanel.repaint();
            }
        });
    }
}
