package src;

import src.GUI.GameGUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class App extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);
    private JPanel introPanel;
    private GameGUI gamePanel;

    public App() {
        setTitle("Stress! The Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null); // Center the window
        
        introPanel = createIntroPanel("/assets/intro.gif");
        gamePanel = new GameGUI();

        mainPanel.add(introPanel, "Intro");
        mainPanel.add(gamePanel, "Game");
        
        setContentPane(mainPanel);

        // Set up a Timer to switch intro images after 2.5 seconds and then attach the KeyListener
        Timer timer = new Timer(2500, e -> {
            updateIntroPanel("/assets/intro2.png");
            introPanel.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        cardLayout.show(mainPanel, "Game");
                        gamePanel.requestFocusInWindow(); // Important for keyboard inputs in the game
                    }
                }
            });
            introPanel.setFocusable(true);
            introPanel.requestFocusInWindow(); // Request focus for the KeyListener
        });
        timer.setRepeats(false);
        timer.start();
    }

    private JPanel createIntroPanel(String imagePath) {
        return new JPanel() {
            ImageIcon introImage = new ImageIcon(getClass().getResource(imagePath));

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(introImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
    }

    private void updateIntroPanel(String imagePath) {
        introPanel = createIntroPanel(imagePath);
        mainPanel.add(introPanel, "Intro2");
        cardLayout.show(mainPanel, "Intro2");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            App app = new App();
            app.setVisible(true);
        });
    }
}
