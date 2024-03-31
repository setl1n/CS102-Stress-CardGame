package gui;

import java.awt.*;
import java.net.URL;

import javax.swing.*;

import cardcollections.*;

/*
*  This class provides methods for rendering static images and GIFs
*  for loading images (such as card images) and transitions in game
*/

public final class GUIUtility {

    private GUIUtility() {}

    private static final int ORIGIN = 0;

    /**
     * @return Image object from the specified filepath
     * @param imagepath The path to the desired image
     * @param nullpath The fallback path if the desired image is not found
     */
    private static Image renderImage(String imagepath, String nullpath, int width, int height) {
        URL imgUrl = GUIUtility.class.getResource(imagepath);
        if (imgUrl == null) {
            imgUrl = GUIUtility.class.getResource(nullpath);
        }
        return new ImageIcon(imgUrl).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    /**
     * Overloaded methods to create or modify JLabels based on a given imagepath
     */

    public static void renderLabel(JLabel label, String imagepath, String nullpath, int width, int height) {
        Image image = renderImage(imagepath, nullpath, width, height);
        label.setIcon(new ImageIcon(image));
    }


    public static JLabel renderLabel(String imagepath, String nullpath, int width, int height) {
        Image image = renderImage(imagepath, nullpath, width, height);
        return new JLabel(new ImageIcon(image));
    }

    /**
     * Overloaded methods to create or modify JLabels based on a Card object
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

    /**
     * Overloaded methods to create or modify JLabels based on a Deck object
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
     * Methods to initialise elements used in overlays
     */

    static JPanel initialiseGlassPane(ImageIcon imageIcon){
        JPanel glassPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Overlay a semi-transparent color to darken the screen
                g.setColor(new Color(0, 0, 0, 123)); // Adjust the alpha value for desired darkness
                g.fillRect(ORIGIN, ORIGIN, getWidth(), getHeight());
                // Set the size of the icon to fill the whole glass pane
                g.drawImage(imageIcon.getImage(), ORIGIN, ORIGIN, getWidth(), getHeight(), this);
            }
        };
        glassPane.setOpaque(false); // Make the glass pane transparent
        glassPane.setLayout(null); // No layout manager
        return glassPane;
    }

    static JLabel initialiseIMGLabel(URL imgURL, JPanel targetPanel, JFrame frame) {
        JLabel imgLabel = new JLabel(new ImageIcon(imgURL));
        imgLabel.setOpaque(false);
        Rectangle bounds = SwingUtilities.convertRectangle(targetPanel.getParent(), targetPanel.getBounds(),
                frame.getLayeredPane());
        imgLabel.setBounds(bounds);
        return imgLabel;
    }
}
