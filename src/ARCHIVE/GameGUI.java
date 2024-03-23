package ARCHIVE;

import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import Game.Game;


// need to call .repaint somewhere
public class GameGUI extends JPanel {
    private GameController controller;
    private GamePanel gamePanel;

    public GameGUI(Game game) {
        controller = new GameController(game);
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
                controller.getGame().handleKeyPress(e);
                gamePanel.repaint();
            }
        });
    }
}
