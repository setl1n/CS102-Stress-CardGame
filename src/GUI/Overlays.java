package gui;

import java.net.URL;

import javax.swing.*;

import gui.panels.gamecontainer.playercontainer.PlayerPanel;

public final class Overlays {

    private Overlays () {}

    private static JLayeredPane onGoingLayeredPane;
    private static JLabel onGoingLabel;
    private static JPanel onGoingGlassPane;
    private static Timer onGoingTimer;

    private static final int CARD_TRANSITION_FLASH_DURATION = 100;
    private static final int DELAY_DURATION = 3200;
    private static final int DELAY_DURATION_STRESS = 3000;

    /*
     * Methods for transitions involving CardPanel
     */

    public static void renderCardTransition(JPanel cardPanel, String playerName) {
        showLockoutTransition(cardPanel, playerName, CARD_TRANSITION_FLASH_DURATION);
    }

    public static void blockedFor(PlayerPanel playerPanel, int blockedDuration) {
        for (JPanel cardPanel : playerPanel.getCardPanels()) {
            showLockoutTransition(cardPanel, null, blockedDuration);
        }
    }

    /*
     * Methods for transitions involving target panels
     * Called during/before various GAME STATES
     */

    private static void showLockoutTransition(JPanel targetPanel, String playerName, int displayDuration) {
        final String transitionPath = "/assets/transition";
        final String filePath = processPath(transitionPath, playerName, false);
        renderImage(targetPanel, filePath, displayDuration);
    }

    public static void renderHelpDialog(JPanel targetPanel) {
        final String assetPath = "/assets/dialog";
        final String filePath = processPath(assetPath, null, false);
        renderImage(targetPanel, filePath, 0);
    }

    public static void renderTimeoutTransition(JPanel targetPanel) {
        final String assetPath = "/assets/timeout";
        final String filePath = processPath(assetPath, null, true);
        renderGIF(targetPanel, filePath, DELAY_DURATION, true);
    }

    public static void renderTieTransition(JPanel targetPanel) {
        final String assetPath = "/assets/tie";
        final String filePath = processPath(assetPath, null, true);
        renderGIF(targetPanel, filePath, DELAY_DURATION, true);
    }

    public static void renderStressTransition(JPanel targetPanel, String playerName) {
        final String assetPath = "/assets/stress";
        final String filePath = processPath(assetPath, playerName, true);
        renderGIF(targetPanel, filePath, DELAY_DURATION_STRESS, false);
    }

    public static void renderGameTransition(JPanel targetPanel, String playerName) {
        final String assetPath = "/assets/game";
        final String filePath = processPath(assetPath, playerName, true);
        renderGIF(targetPanel, filePath, DELAY_DURATION, true);
    }

    /*
     * Method to process and return a filepath based on 
     * whether a player is associated with it or not
     */
    private static String processPath(String path, String playerName, boolean isGif) {
        String playerPath = "";
        if (playerName == null) {
            playerPath += "";
        } else if ("Player 1".equals(playerName)) {
            playerPath += "red";
        } else {
            playerPath += "blue";
        }
        String extension = isGif ? ".gif" : ".png";
        return path + playerPath + extension;
    }

    /*
     * Methods to render images and GIFs on screen
     */
    private static void renderImage(JPanel targetPanel, String imgPath, int displayDuration) {
        JFrame frame = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, targetPanel);
        if (frame == null) {
            return;
        }
        
        URL imgUrl = GUIUtility.class.getResource(imgPath);
        if (imgUrl == null) {
            return;
        }
        JLabel imgLabel = GUIUtility.initialiseIMGLabel(imgUrl, targetPanel, frame); 
        onGoingLabel = imgLabel;

        /*
         * Layered Pane allows us to add transitions on top of the targetpanel
         * while not being confined to the dimensions of the targetpanel
         */
        
        JLayeredPane layeredPane = frame.getLayeredPane();
        onGoingLayeredPane = layeredPane;

        layeredPane.add(imgLabel, JLayeredPane.POPUP_LAYER);
        layeredPane.moveToFront(imgLabel);

        /*
         * Handling for static or dynamic image
         */

        if (displayDuration == 0) {
            return;
        } else {
            hideImageAfterDelay(layeredPane, imgLabel, displayDuration);
        }
    }

    private static void hideImageAfterDelay(JLayeredPane layeredPane, JLabel imgLabel, int displayDuration) {
        Timer timer = new Timer(displayDuration, e -> {
            onGoingLabel = null;
            onGoingLayeredPane = null;
            layeredPane.remove(imgLabel);
            layeredPane.repaint();
        });
        timer.setRepeats(false);
        timer.start();
    }


    private static void renderGIF(JPanel targetPanel, String gifPath, int delayDuration,
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

        /*
         * Glass Pane is used to render transparent GIFs
         */

        JPanel glassPane = GUIUtility.initialiseGlassPane(gifIcon);
        frame.setGlassPane(glassPane);
        glassPane.setVisible(true);
        onGoingGlassPane = glassPane;

        /*
         * Garbage collection to ensure proper looping
         */
        gifIcon.getImage().flush();

        hideGIFAfterDelay(frame, glassPane, delayDuration);
        if (loadImageAfter) {
            loadImageAfterDelay(gifPath, targetPanel, delayDuration);
        }
    }
    
    /*
     * Remove animation and hide glassPane after a delay
     */
    private static void hideGIFAfterDelay(JFrame frame, JPanel glassPane, int delayDuration) {
        Timer timer = new Timer(delayDuration, e -> {
            onGoingGlassPane = null;
            glassPane.setVisible(false);
            frame.repaint();
        });
        timer.setRepeats(false);
        timer.start();
    }

    /*
     * Load static image after the GIF completes
     */
    private static void loadImageAfterDelay(String gifPath, JPanel targetPanel, int delayDuration) {
        final String newPath = gifPath.replace(".gif", ".png");
        Timer loadImageTimer = new Timer(delayDuration, e -> {
            renderImage(targetPanel, newPath, 0);
        });
        loadImageTimer.setRepeats(false);
        loadImageTimer.start();
        onGoingTimer = loadImageTimer;
    }

    /*
     * Methods to cut and clear any active Overlays
     */

    public static void clear() {
        cancelActiveTimer();
        cutCurrentAnimationIfAny();
        clearStaticImagesIfAny();
    }

    private static void cutCurrentAnimationIfAny() {
        if (onGoingGlassPane != null) {
            onGoingGlassPane.setVisible(false);
        }
        onGoingGlassPane = null;
    }

    private static void clearStaticImagesIfAny() {
        if (onGoingLayeredPane != null && onGoingLabel != null) {
            onGoingLayeredPane.remove(onGoingLabel);
            onGoingLayeredPane.repaint();
        }
        onGoingLayeredPane = null;
        onGoingLabel = null;
    }

    private static void cancelActiveTimer() {
        if (onGoingTimer != null) {
            onGoingTimer.stop();
        }
        onGoingTimer = null;
    }
}
