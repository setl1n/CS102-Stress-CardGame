package gui;

import java.awt.Rectangle;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import game.*;
import gui.panels.gamecontainer.playercontainer.PlayerPanel;

public final class Overlays {

    private static JFrame onGoingFrame;
    private static JLabel onGoingLabel;
    private static JPanel onGoingPane;
    private static Timer onGoingTimer;

    public static void clear() {
        cancelActiveTimer(); // Cancel any active timer
        cutCurrentAnimationIfAny();
        clearStaticImagesIfAny();
    }

    private static void cutCurrentAnimationIfAny() {
        if (onGoingPane != null) {
            onGoingPane.setVisible(false);
            onGoingPane = null;
        }
    }

    private static void clearStaticImagesIfAny() {
        if (onGoingFrame != null && onGoingLabel != null) {
            JLayeredPane jLayeredPane = onGoingFrame.getLayeredPane();
            jLayeredPane.remove(onGoingLabel);
            jLayeredPane.repaint();
            onGoingFrame = null;
            onGoingLabel = null;
        }
    }

    private static void cancelActiveTimer() {
        if (onGoingTimer != null) {
            onGoingTimer.stop();
            onGoingTimer = null;
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
        onGoingFrame = frame;
        onGoingLabel = imgLabel;

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

    public static void renderGIF(JPanel targetPanel, String gifPath, int duration,
            boolean loadImageAfter) {
        JFrame frame = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, targetPanel);
        if (frame == null) {
            System.err.println("No enclosing JFrame found for the target panel.");
            return;
        }
        URL gifUrl = GUIUtility.class.getResource(gifPath);
        if (gifUrl == null) {
            System.err.println("GIF file not found: " + gifPath);
            return;
        }
        ImageIcon gifIcon = new ImageIcon(gifUrl);

        // Create a new JPanel that acts as the glass pane
        JPanel glassPane = GUIUtility.initialiseGlassPane(gifIcon);
        glassPane.setOpaque(false); // Make the glass pane transparent
        glassPane.setLayout(null); // No layout manager

        frame.setGlassPane(glassPane);

        glassPane.setVisible(true); // Activate the glass pane to show the animation
        onGoingPane = glassPane;
        // Forces garbage collection to ensure proper looping
        gifIcon.getImage().flush();

        // Timer to remove the animation and hide the glass pane after a delay
        Timer timer = new Timer(3200, e -> {
            onGoingPane = null;
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
        onGoingTimer = loadImageTimer;
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
        final String transitionPath = "/assets/transition";
        final String filePath = processPath(transitionPath, playerName, false);
        renderImage(targetPanel, filePath, duration);
    }

    public static void renderHelpDialog(JPanel targetPanel) {
        final String assetPath = "/assets/dialog";
        final String filePath = processPath(assetPath, null, false);
        renderImage(targetPanel, filePath, 0);
    }

    public static void renderTimeoutTransition(JPanel targetPanel) {
        final int duration = 3200;
        final String assetPath = "/assets/timeout";
        final String filePath = processPath(assetPath, null, true);
        renderGIF(targetPanel, filePath, duration, true);
    }

    public static void renderTieTransition(JPanel targetPanel) {
        final int duration = 3200;
        final String assetPath = "/assets/tie";
        final String filePath = processPath(assetPath, null, true);
        renderGIF(targetPanel, filePath, duration, true);
    }

    public static void renderStressTransition(JPanel targetPanel, String playerName) {
        final int duration = 3000;
        final String assetPath = "/assets/stress";
        final String filePath = processPath(assetPath, playerName, true);
        renderGIF(targetPanel, filePath, duration, false);
    }

    public static void renderGameTransition(JPanel targetPanel, String playerName) {
        final int duration = 3200;
        final String assetPath = "/assets/game";
        final String filePath = processPath(assetPath, playerName, true);
        renderGIF(targetPanel, filePath, duration, true);
    }
    
    private static String processPath(String path, String playerName, boolean isGif) {
        String playerPath = "";
        if (playerName == null) {
            playerPath += "";
        } else if (playerName.equals("Player 1")) {
            playerPath += "red";
        } else {
            playerPath += "blue";
        }
        String extension = isGif ? ".gif" :".png";
        return path + playerPath + extension;
    }
}
