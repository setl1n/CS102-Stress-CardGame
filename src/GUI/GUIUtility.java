package gui;

import java.awt.*;
import javax.swing.*;

import gui.gamecontainer.*;
import java.net.URL;
import cardcollections.*;
import player.*;
import cardcollections.deckcomponents.*;

public final class GUIUtility {

    private GUIUtility() {}

    public static Image renderImage(String imagepath, String nullpath, int width, int height) {
        URL imgUrl = GUIUtility.class.getResource(imagepath);
        if (imgUrl == null) {
            imgUrl = GUIUtility.class.getResource(nullpath);
        }
        return new ImageIcon(imgUrl).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    /*
     * OVERLOADED LABEL RENDER
     */

    public static void renderLabel(JLabel label, String imagepath, String nullpath, int width, int height) {
        Image image = renderImage(imagepath, nullpath, width, height);
        label.setIcon(new ImageIcon(image));
    }

    public static JLabel renderLabel(String imagepath, String nullpath, int width, int height) {
        Image image = renderImage(imagepath, nullpath, width, height);
        return new JLabel(new ImageIcon(image));
    }

    /*
     * OVERLOADED CARD RENDER
     */

    public static JLabel renderCard(Card card, int width, int height) {
        Image image = card.getCardImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new JLabel(new ImageIcon(image), JLabel.CENTER);
    }

    public static void renderCard(JLabel label, Card card, int width, int height) {
        Image image;
        if (card != null) {
            image = card.getCardImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        } else {
            URL imgUrl = GUIUtility.class.getResource("/assets/emptyCard.png");
            image = new ImageIcon(imgUrl).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        }
        label.setIcon(new ImageIcon(image));
        label.repaint();
    }

    /*
     * OVERLOADED DECK RENDER
     */

     public static JLabel renderDeck(Deck deck, int width, int height) {
        Image image = deck.getDeckImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new JLabel(new ImageIcon(image), JLabel.CENTER);
    }

    public static void renderDeck(JLabel label, Deck deck, int width, int height) {
        Image image = deck.getDeckImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(image));
        label.repaint();
    }

    /*
    * ANIMATION RENDERING
    */
    public static void renderCardTransition(JPanel targetPanel, Player player, String gifPath) {
        // Automatically find the JFrame that encases the targetPanel
        JFrame frame = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, targetPanel);
        if (frame == null) {
            System.err.println("No enclosing JFrame found for the target panel.");
            return;
        }
        JLayeredPane layeredPane = frame.getLayeredPane();
        
        if ("Player 1".equals(player.getName())) {
            gifPath += "red.png";
        } else {
            gifPath += "blue.png";
        }

        URL gifUrl = GUIUtility.class.getResource(gifPath);
        if (gifUrl == null) {
            System.err.println("GIF file not found: " + gifPath);
            return;
        }
        ImageIcon gifIcon = new ImageIcon(gifUrl);
        JLabel gifLabel = new JLabel(gifIcon);
        gifLabel.setOpaque(false);

        Rectangle bounds = SwingUtilities.convertRectangle(targetPanel.getParent(), targetPanel.getBounds(), frame.getLayeredPane());
        gifLabel.setBounds(bounds);

        layeredPane.add(gifLabel, JLayeredPane.POPUP_LAYER);
        layeredPane.moveToFront(gifLabel);

        int delay = 100;
        Timer timer = new Timer(delay, e -> {
            layeredPane.remove(gifLabel);
            layeredPane.repaint(bounds);
        });
        timer.setRepeats(false);
        timer.start();
    }

    public static void renderStressTransition(JPanel targetPanel, Player player, String gifPath) {
        // Automatically find the JFrame that encases the targetPanel
        JFrame frame = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, targetPanel);
        if (frame == null) {
            System.err.println("No enclosing JFrame found for the target panel.");
            return;
        }

        if ("Player 1".equals(player.getName())) {
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
                // Set the size of the icon to fill the whole glass pane
                g.drawImage(gifIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        glassPane.setOpaque(false); // Make the glass pane transparent
        glassPane.setLayout(null); // No layout manager

        frame.setGlassPane(glassPane);
        glassPane.setVisible(true); // Activate the glass pane to show the animation

        // Timer to remove the animation and hide the glass pane after a delay
        int delay = 2800; // Duration of the stress transition in milliseconds
        new Timer(delay, e -> glassPane.setVisible(false)).start();
    }

}
