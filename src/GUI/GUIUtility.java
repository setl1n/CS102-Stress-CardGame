package gui;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import cardcollections.Deck;
import cardcollections.deckcomponents.Card;

public final class GUIUtility {
    private static Image renderImage(String imagepath, String nullpath, int width, int height) {
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

}
