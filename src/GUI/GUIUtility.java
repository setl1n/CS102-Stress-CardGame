package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cardcollections.Deck;
import cardcollections.deckcomponents.Card;

/*
 *  Methods for loading audio and visual elements
 */

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


    /*
     * SOUND PLAYING METHODS
     */

    protected static Clip loadAudioClip(String audioPath) throws Exception {
        URL audioUrl = Sounds.class.getResource(audioPath);
        if (audioUrl == null) {
            throw new RuntimeException("Audio file not found: " + audioPath);
        }
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioUrl);
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        return clip;
    }

    static JPanel initialiseGlassPane(ImageIcon imageIcon){
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Overlay a semi-transparent color to darken the screen
                g.setColor(new Color(0, 0, 0, 123)); // Adjust the alpha value for desired darkness
                g.fillRect(0, 0, getWidth(), getHeight());
                // Set the size of the icon to fill the whole glass pane
                g.drawImage(imageIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
    }
}
