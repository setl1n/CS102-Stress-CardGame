package gui.panels.gamecontainer.playercontainer;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import cardcollections.*;
import gui.GUIUtility;

public class CardPanel extends JPanel {

    static final int WIDTH = 138;
    static final int HEIGHT = 186;

    private JLabel cardLabel;

    /*
     * Creates JPanel to show the image of a Card
     * used in the hand (which has 4 of these panels)
     */

    public CardPanel(Card card) {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new BorderLayout());
        this.setOpaque(false);

        cardLabel = GUIUtility.renderCard(card, WIDTH, HEIGHT);
        add(cardLabel, BorderLayout.CENTER);
    }

    public void update(Card card) {
        GUIUtility.renderCard(cardLabel, card, WIDTH, HEIGHT);
    }
}

