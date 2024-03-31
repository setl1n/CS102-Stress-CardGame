package gui.panels.gamecontainer.playercontainer;

import java.util.ArrayList;
import java.awt.*;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cardcollections.*;
import gui.GUIUtility;

public class NumPanel extends JPanel {
    private ArrayList<JLabel> numLabels = new ArrayList<>();
    private Deck deck;

    public NumPanel(Deck deck) {
        this.deck = deck;
        setOpaque(false);
        setLayout(new FlowLayout(FlowLayout.LEFT,0 ,0));
        setPreferredSize(new Dimension(120, 50));

        int num = deck.size();
        String path = "/assets/" + (num / 10) + ".png";
        JLabel labelComponent = GUIUtility.renderLabel(path, "/assets/0.png", 50, 50);
        numLabels.add(labelComponent);
        add(labelComponent);

        // create digit 2
        path = "/assets/" + (num % 10) + ".png";
        labelComponent = GUIUtility.renderLabel(path, "/assets/0.png", 50, 50);
        numLabels.add(labelComponent);
        add(labelComponent);
    }

    public void updateNum(){
        int num = deck.size();
        
        // first digit
        String path = "/assets/" + (num / 10) + ".png";
        GUIUtility.renderLabel(numLabels.get(0), path, "/assets/0.png", 50, 50);

        // second digit
        path = "/assets/" + (num % 10) + ".png";
        GUIUtility.renderLabel(numLabels.get(1), path, "/assets/0.png", 50, 50);
    }
}