
package GUI.gamecontainer.pilecontainer;

import javax.swing.*;
import java.awt.*;

import GUI.GUIUtility;
import Player.Player;

public class IndicatorPanel extends JPanel {

    static final int width = 40;
    static final int height = 40;

    private JPanel indicator;
    private Image indicatorImage;

    public IndicatorPanel(Player player) {

        setLayout(new FlowLayout(FlowLayout.LEFT,0 ,0));
        setPreferredSize(new Dimension(185, 50));
        this.setOpaque(false);

        indicator = new JPanel();
        indicator.setPreferredSize(new Dimension(width, height));
        indicator.setOpaque(false);

        String path = "/assets/" + player.getName() + "-" + "indicator.png";
        JLabel indicatorLabel = GUIUtility.renderLabel(path, "/assets/empty.png", width, height);
        indicator.add(indicatorLabel, BorderLayout.CENTER);
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
