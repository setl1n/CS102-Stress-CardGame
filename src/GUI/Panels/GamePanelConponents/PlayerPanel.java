package GUI.Panels.GamePanelConponents;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import Player.Player;

public class PlayerPanel extends JPanel {

    /*
     * this is the example of linking the panel
     * to the instance that its referencing in a
     * bidirectional manner
     */

    public PlayerPanel(Player player) {

        player.setPlayerPanel(this);
        this.setOpaque(false);

        setPreferredSize(new Dimension(1080, 220));

        if ("Player 1".equals(player.getName())) {

            setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));

            for (int i = 0; i < 4; i++) {
                add(new CardPanel(player.getHand().getCardAtIndex(i)));
            }
            add(new DeckPanel(player.getDeck()));
            JPanel numPanel = createNumPanel();
            add(numPanel);

        } else if ("Player 2".equals(player.getName())) {

            setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 15));

            JPanel numPanel = createNumPanel();
            add(numPanel);
            add(new DeckPanel(player.getDeck()));
            for (int i = 0; i < 4; i++) {
                add(new CardPanel(player.getHand().getCardAtIndex(i)));
            }
        }
    }

    private JPanel createNumPanel() {
        JPanel numPanel = new JPanel();
        numPanel.setPreferredSize(new Dimension(100, 100));
        numPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return numPanel;
    }
}

