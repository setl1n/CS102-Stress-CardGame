
package gui.panels.gamecontainer.pilecontainer;

import javax.swing.*;

import game.Player;

import java.awt.*;

import gui.GUIUtility;

public class IndicatorPanel extends JPanel {

    static final int INDICATOR_WIDTH = 40;
    static final int INDICATOR_HEIGHT = 40;
    static final int PANEL_WIDTH = 185;
    static final int PANEL_HEIGHT = 50;


    public IndicatorPanel(Player player) {

        setLayout(new FlowLayout(FlowLayout.LEFT,0 ,0));
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setOpaque(false);

        JPanel indicator = new JPanel();
        indicator.setPreferredSize(new Dimension(INDICATOR_WIDTH, INDICATOR_HEIGHT));
        indicator.setOpaque(false);

        String path = "/assets/" + player.getName() + "-" + "indicator.png";
        JLabel indicatorLabel = GUIUtility.renderLabel(path, "/assets/empty.png", INDICATOR_WIDTH, INDICATOR_HEIGHT);
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
