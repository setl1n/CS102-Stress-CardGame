package gui.panels.gamecontainer;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class AnimationPanel extends JPanel {

    private ImageIcon gifImage;
    private static final int ORIGIN = 0;

    /*
     * Adds temporary panel to show GIFs and/or static images
     * Used for loading screens and to show transitions
     */

    public AnimationPanel(String imagePath) {
        this.setOpaque(false);
        URL imageUrl = getClass().getResource(imagePath);
        if (imageUrl != null) {
            gifImage = new ImageIcon(imageUrl);
        }
        this.repaint(); 
    }

    /*
     * Paint component adds directly to the JPanel instead of 
     * loading via JLabel. Necessary for transparent gifs to
     * be properly reflected on screen
     */
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (gifImage != null) {
            g.drawImage(gifImage.getImage(), ORIGIN, ORIGIN, getWidth(), getHeight(), this);
        }
    }
}

