package gui.panels.gamecontainer.pilecontainer;

import javax.swing.*;
import java.awt.*;

import cardcollections.*;
import game.Player;

public class PileContainer extends JPanel {

    private static final int OFFSET = 5;
    private static final int WIDTH = 300;
    private static final int HEIGHT = 240;

    public PileContainer(Pile pile1, Pile pile2, Player player1, Player player2) {
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(OFFSET, OFFSET, OFFSET, OFFSET);

        /*
         * INDICATOR 2
         * need to add indicator to player maybe? or game, unsure
         */
        IndicatorPanel indicator2 = new IndicatorPanel(player2); // Initialize with BLUE for player 2
        gbc.gridwidth = 2; // Span across two columns
        add(indicator2, gbc);

        // move down by one row
        gbc.gridy = 1;
        // change back to single column
        gbc.gridwidth = 1;

        /*
         * FIRST PILE
         */
        PilePanel pilePanel1 = new PilePanel(pile1);
        add(pilePanel1, gbc);

        // move to next column in row
        gbc.gridx = 1;

        /*
         * PILE 2
         */
        PilePanel pilePanel2 = new PilePanel(pile2);
        add(pilePanel2, gbc); // Add pile 2

        // move back to first column
        gbc.gridx = 0;
        // move down one more row
        gbc.gridy = 2; // Third row, below the piles
        // change back to two columns
        gbc.gridwidth = 2;

        /*
         * INDICATOR 1
         */
        IndicatorPanel indicator1 = new IndicatorPanel(player1);
        add(indicator1, gbc); // Add indicator 1 below the piles

    }
}

