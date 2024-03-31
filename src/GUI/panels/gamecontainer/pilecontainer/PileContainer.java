package gui.panels.gamecontainer.pilecontainer;

import javax.swing.*;
import java.awt.*;

import cardcollections.*;
import game.Player;

public class PileContainer extends JPanel {

    private static final int OFFSET = 5;
    private static final int WIDTH = 300;
    private static final int HEIGHT = 240;

    /*
     * Creates new PileContainer JPanel that holds both piles in the centre
     * As well as the corresponding indicator panels for each player
     */

    public PileContainer(Pile pile1, Pile pile2, Player player1, Player player2) {
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setOpaque(false);

        /*
         * GridBag constraints allow us to add elements in a grid format;
         * 
         * INDICATOR PANEL P2
         *   PILE 1 / PILE 2
         * INDICATOR PANEL P1
         */

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(OFFSET, OFFSET, OFFSET, OFFSET);

        /*
         * INDICATOR 2
         * Panel takes up two columns
         */
        IndicatorPanel indicator2 = new IndicatorPanel(player2);
        gbc.gridwidth = 2;
        add(indicator2, gbc);

        
        /*
         * FIRST PILE
         * Shifted down by one row
         * Takes up one column
         */
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        PilePanel pilePanel1 = new PilePanel(pile1);
        add(pilePanel1, gbc);
        

        /*
         * PILE 2
         * Move to next column in row
         */
        gbc.gridx = 1;
        PilePanel pilePanel2 = new PilePanel(pile2);
        add(pilePanel2, gbc);

        /*
         * INDICATOR 1
         * Move back to first column
         * Move down one more row
         * Set width to 2 columns
         */
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        IndicatorPanel indicator1 = new IndicatorPanel(player1);
        add(indicator1, gbc);

    }
}

