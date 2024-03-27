package GUI;

import java.awt.*;
import javax.swing.*;

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
            URL imgUrl = GUIUtility.class.getResource("/assets/emptycard.png");
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

}
