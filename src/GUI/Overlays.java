package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.net.URL;

import javax.print.attribute.standard.JobKOctetsSupported;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import gui.panels.gamecontainer.playercontainer.PlayerPanel;
import player.Player;

public final class Overlays {

    private static JFrame gameFrame;
    private static JLabel gameLabel;
    private static Timer activeTimer;

    public static void clear() {
        cancelActiveTimer(); // Cancel any active timer
        if (gameFrame != null && gameLabel != null) {
            JLayeredPane jLayeredPane = gameFrame.getLayeredPane();
            jLayeredPane.remove(gameLabel);
            jLayeredPane.repaint();
            gameFrame = null;
            gameLabel = null;
        }
    }

    private static void cancelActiveTimer() {
        if (activeTimer != null) {
            activeTimer.stop();
            activeTimer = null;
        }
    }

    /*
     * ANIMATION RENDERING
     */
    public static void renderImage(JPanel targetPanel, String imgPath, int delay) {
        JFrame frame = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, targetPanel);
        if (frame == null) {
            return;
        }

        URL imgUrl = GUIUtility.class.getResource(imgPath);
        if (imgUrl == null) {
            return;
        }
        JLabel imgLabel = new JLabel(new ImageIcon(imgUrl));
        gameFrame = frame;
        gameLabel = imgLabel;

        imgLabel.setOpaque(false);
        Rectangle bounds = SwingUtilities.convertRectangle(targetPanel.getParent(), targetPanel.getBounds(),
                frame.getLayeredPane());
        imgLabel.setBounds(bounds);

        JLayeredPane layeredPane = frame.getLayeredPane();
        layeredPane.add(imgLabel, JLayeredPane.POPUP_LAYER);
        layeredPane.moveToFront(imgLabel);

        if (delay > 0) {
            Timer timer = new Timer(delay, e -> {
                layeredPane.remove(imgLabel);
                layeredPane.repaint();
            });
            timer.setRepeats(false);
            timer.start();
        } else {
            return;
        }
    }

    public static void renderGIF(JPanel targetPanel, Player player, String gifPath, int duration,
            boolean loadImageAfter) {
        JFrame frame = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, targetPanel);
        if (frame == null) {
            System.err.println("No enclosing JFrame found for the target panel.");
            return;
        }
        gameFrame = frame;

        if (player == null) {
            gifPath += ".gif";
        } else if ("Player 1".equals(player.getName())) {
            gifPath += "red.gif";
        } else {
            gifPath += "blue.gif";
        }

        URL gifUrl = GUIUtility.class.getResource(gifPath);
        if (gifUrl == null) {
            System.err.println("GIF file not found: " + gifPath);
            return;
        }
        ImageIcon gifIcon = new ImageIcon(gifUrl);

        // Create a new JPanel that acts as the glass pane
        JPanel glassPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Overlay a semi-transparent color to darken the screen
                g.setColor(new Color(0, 0, 0, 123)); // Adjust the alpha value for desired darkness
                g.fillRect(0, 0, getWidth(), getHeight());
                // Set the size of the icon to fill the whole glass pane
                g.drawImage(gifIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        glassPane.setOpaque(false); // Make the glass pane transparent
        glassPane.setLayout(null); // No layout manager

        frame.setGlassPane(glassPane);

        glassPane.setVisible(true); // Activate the glass pane to show the animation

        // Forces garbage collection to ensure proper looping
        gifIcon.getImage().flush();

        // Timer to remove the animation and hide the glass pane after a delay
        Timer timer = new Timer(3200, e -> {
            glassPane.setVisible(false);
            frame.repaint();
        });
        timer.setRepeats(false);
        timer.start();

        // timer to load image after gif finishes
        final String newPath = gifPath.replace(".gif", ".png");
        Timer loadImageTimer = new Timer(3200, e -> {
            if (loadImageAfter)
            renderImage(targetPanel, newPath, 0);
        });
        loadImageTimer.setRepeats(false);
        loadImageTimer.start();
        activeTimer = loadImageTimer;
    }

    public static void renderCardTransition(JPanel targetPanel, String playerName) {
        showLockoutTransition(targetPanel, playerName, 100);
    }

    public static void blockedFor(PlayerPanel playerPanel, int milliseconds) {
        for (JPanel cardPanel : playerPanel.getCardPanels()) {
            showLockoutTransition(cardPanel, null, milliseconds);
        }
    }

    private static void showLockoutTransition(JPanel targetPanel, String playerName, int duration) {
        String transitionPath = "/assets/transition";
        if (playerName == null) {
            transitionPath += ".png";
        } else if (playerName.equals("Player 1")) {
            transitionPath += "red.png";
        } else {
            transitionPath += "blue.png";
        }
        renderImage(targetPanel, transitionPath, duration);
    }

    public static void renderHelpDialog(JPanel targetPanel) {
        String path = "/assets/dialog.png";
        renderImage(targetPanel, path, 0);
    }

    public static void renderTimeoutTransition(JPanel targetPanel) {
        final int gifDuration = 3200;
        final String gifPath = "/assets/timeout";

        renderGIF(targetPanel, null, gifPath, gifDuration, true);
    }

    public static void renderTieTransition(JPanel targetPanel) {
        final int gifDuration = 3200;
        final String gifPath = "/assets/tie";

        renderGIF(targetPanel, null, gifPath, gifDuration, true);
    }

    public static void renderStressTransition(JPanel targetPanel, Player player) {
        final int duration = 3000;
        final String gifPath = "/assets/stress";
        renderGIF(targetPanel, player, gifPath, duration, false);
    }

    public static void renderGameTransition(JPanel targetPanel, Player player) {
        final int duration = 3200;
        final String gifPath = "/assets/game";
        renderGIF(targetPanel, player, gifPath, duration, true);
    }
}
