package gui;

import javax.swing.*;
import java.awt.*;

import game.ExamplePlayer;

public class PlayerPanel extends JPanel {

    /*
     * this is the example of linking the panel
     * to the instance that its referencing in a
     * bidirectional manner
     */
    private ExamplePlayer player;

    public PlayerPanel(ExamplePlayer player) {

        this.player = player;
        this.player.setPlayerPanel(this);

        setPreferredSize(new Dimension(1080, 220));

        if ("Player 1".equals(player.getName())) {

            setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));

            for (int i = 0; i < 4; i++) {
                add(new CardPanel("Card " + (i + 1)));
            }
            add(new CardPanel("Deck"));
            JPanel numPanel = createNumPanel();
            add(numPanel);

        } else if ("Player 2".equals(player.getName())) {

            setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 15));

            JPanel numPanel = createNumPanel();
            add(numPanel);
            add(new CardPanel("Deck"));
            for (int i = 0; i < 4; i++) {
                add(new CardPanel("Card " + (i + 1)));
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

