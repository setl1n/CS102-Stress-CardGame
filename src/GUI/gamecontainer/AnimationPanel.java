package gui.gamecontainer;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class AnimationPanel extends JPanel {

    private ImageIcon gifImage;

    public AnimationPanel(String imagePath) {
        this.setOpaque(false);
        URL imageUrl = getClass().getResource(imagePath);
        if (imageUrl != null) {
            gifImage = new ImageIcon(imageUrl);
        }
        this.repaint(); 
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (gifImage != null) {
            g.drawImage(gifImage.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    }
}

