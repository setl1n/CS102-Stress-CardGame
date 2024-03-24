package GUI.startcontainer;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class IntroPanel extends JPanel {
    private ImageIcon introImage;

    public IntroPanel(String imagePath) {
        this.setOpaque(false);
        URL imageUrl = getClass().getResource(imagePath);
        if (imageUrl != null) {
            introImage = new ImageIcon(imageUrl);
        }
        this.repaint(); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (introImage != null) {
            g.drawImage(introImage.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    }
}
