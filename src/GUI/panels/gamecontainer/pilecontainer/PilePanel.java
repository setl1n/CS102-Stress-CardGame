package gui.panels.gamecontainer.pilecontainer;

import java.awt.*;
import javax.swing.*;

import gui.GUIUtility;
import cardcollections.*;

public class PilePanel extends JPanel {

    private static final int WIDTH = 138;
    private static final int HEIGHT = 186;

    private JLabel jLabel;

    /*
     * Creates new JPanel for one pile
     */

    public PilePanel(Pile pile) {

        pile.setPilePanel(this);
        this.setOpaque(false);

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new BorderLayout());
        
        jLabel = GUIUtility.renderLabel("/assets/emptyDeck.png", "/assets/emptyDeck.png", WIDTH, HEIGHT);
        add(jLabel, BorderLayout.CENTER);

    }

    /*
     * Updates Pile panel when the top card changes
     * to modify the image displayed in GUI
     */

    public void updatePilePanel(Card card) {
        Image image = card.getCardImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
        jLabel.setIcon(new ImageIcon(image));
        jLabel.repaint();
    }
}
