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

import gui.panels.gamecontainer.playercontainer.PlayerPanel;
import player.Player;

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

    public static void renderGIF(JPanel targetPanel, Player player, String gifPath, int duration,
            boolean loadImageAfter) {
        JFrame frame = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, targetPanel);
        if (frame == null) {
            System.err.println("No enclosing JFrame found for the target panel.");
            return;
        }

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
