package gui.panels;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class IntroPanel extends JPanel {
    private ImageIcon introImage;
    private String gifPath;
    private String imagePath;
    private Timer timer;

    public IntroPanel(String gifPath, String imagePath) {
        this.gifPath = gifPath;
        this.imagePath = imagePath;
        this.setOpaque(false);
        loadImage(gifPath);
        setupTimer();
        timer.start();
    }

    private void setupTimer() {
        int delay = 2500; // Duration of the GIF animation in milliseconds
        timer = new Timer(delay, e -> {
            loadImage(imagePath); // Load the PNG image
        });
        timer.setRepeats(false);
    }

    private void loadImage(String imagePath) {
        URL imageUrl = getClass().getResource(imagePath);
        if (imageUrl != null) {
            // Force previous GIF to get garbage collected to ensure proper looping
            if (introImage != null) {
                introImage.getImage().flush();
            }
            introImage = new ImageIcon(imageUrl);
            repaint(); // Repaint the panel to update the image
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (introImage != null) {
            g.drawImage(introImage.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            loadImage(gifPath); // Load the GIF image
            timer.restart(); // Restart the timer to switch to the PNG image later
        } else {
            timer.stop(); // Stop the timer if the panel is being hidden
        }
    }
}
