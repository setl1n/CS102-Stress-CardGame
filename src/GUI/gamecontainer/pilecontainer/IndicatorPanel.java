
package GUI.gamecontainer.pilecontainer;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

import Player.Player;

public class IndicatorPanel extends JPanel {

    static final int width = 40;
    static final int height = 40;

    private JPanel indicator;
    private Image indicatorImage;

    public IndicatorPanel(Player player) {
        // Use FlowLayout to control the position of the indicator
        setLayout(new FlowLayout(FlowLayout.LEFT,0 ,0));
        setPreferredSize(new Dimension(185, 50));
        this.setOpaque(false);

        indicator = new JPanel();
        indicator.setPreferredSize(new Dimension(width, height)); // Setting size of the inner panel
        indicator.setOpaque(false);

        // create image
        System.out.println(player.getName());
        String path = "/assets/" + player.getName() + "-" + "indicator.png";
        URL imgUrl = getClass().getResource(path);
        if (imgUrl == null) {
            imgUrl = getClass().getResource("/assets/empty.png");
        }

        indicatorImage = new ImageIcon(imgUrl).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        JLabel labelComponent = new JLabel(new ImageIcon(indicatorImage), JLabel.CENTER);
        indicator.add(labelComponent, BorderLayout.CENTER);

        add(indicator);
        player.setIndicatorPanel(this);

    }

    // Method to update the position of the indicator within this panel
    public void setPositionToLeft() {
        ((FlowLayout) getLayout()).setAlignment(FlowLayout.LEFT);
        revalidate();
        repaint();
    }

    public void setPositionToRight() {
        ((FlowLayout) getLayout()).setAlignment(FlowLayout.RIGHT);
        revalidate();
        repaint();
    }
}
