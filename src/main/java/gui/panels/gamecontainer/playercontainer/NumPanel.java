package gui.panels.gamecontainer.playercontainer;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import cardcollections.Deck;
import gui.GUIUtility;

public class NumPanel extends JPanel {
    private ArrayList<JLabel> numLabels = new ArrayList<>();
    private Deck deck;
    private static final int WIDTH = 120;
    private static final int HEIGHT = 50;
    private static final int LABEL_WIDTH_HEIGHT = 50;
    
    /*
     * Creates JPanel to display the number of cards left
     * in the deck for each player, next to the deck panel
     */

    public NumPanel(Deck deck) {
        this.deck = deck;
        setOpaque(false);
        setLayout(new FlowLayout(FlowLayout.LEFT,0 ,0));
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        int num = deck.size();

        /*
         * Add the first digit as an image
         * Fallback path to render as 0
         */
        String path = "/assets/" + (num / 10) + ".png";
        JLabel labelComponent = GUIUtility.renderLabel(path, "/assets/0.png", LABEL_WIDTH_HEIGHT, LABEL_WIDTH_HEIGHT);
        numLabels.add(labelComponent);
        add(labelComponent);

        /*
         * Add the second digit as an image
         */
        path = "/assets/" + (num % 10) + ".png";
        labelComponent = GUIUtility.renderLabel(path, "/assets/0.png", LABEL_WIDTH_HEIGHT, LABEL_WIDTH_HEIGHT);
        numLabels.add(labelComponent);
        add(labelComponent);
    }

    public void updateNum(){
        int num = deck.size();
        
        String path = "/assets/" + (num / 10) + ".png";
        GUIUtility.renderLabel(numLabels.get(0), path, "/assets/0.png", LABEL_WIDTH_HEIGHT, LABEL_WIDTH_HEIGHT);

        path = "/assets/" + (num % 10) + ".png";
        GUIUtility.renderLabel(numLabels.get(1), path, "/assets/0.png", LABEL_WIDTH_HEIGHT, LABEL_WIDTH_HEIGHT);
    }
}