package GUI.gamecontainer.playercontainer;

import java.util.*;

import java.awt.*;

import javax.swing.JLabel;
import javax.swing.JPanel;

import Player.Player;
import GUI.GUIUtility;
import Collections.DeckComponents.Card;

public class PlayerPanel extends JPanel {

    private Player player;
    private ArrayList<CardPanel> cardPanels = new ArrayList<>();
    private ArrayList<JLabel> numLabels = new ArrayList<>();
    private DeckPanel deckPanel;

    public PlayerPanel(Player player) {

        this.player = player;
        player.setPlayerPanel(this);
        this.setOpaque(false);
        setPreferredSize(new Dimension(1080, 240));

        if ("Player 1".equals(player.getName())) {

            setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));

            addHand();
            addDeck();
            addNum();

        } else if ("Player 2".equals(player.getName())) {

            setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 15));
            addNum();
            addDeck();
            addHand();
        }
    }

    public void addHand() {
        for (int i = 0; i < 4; i++) {
            CardPanel temp = new CardPanel(player.getHand().getCardAtIndex(i));
            cardPanels.add(temp);
            add(temp);
        }
    }

    public void addDeck() {
        this.deckPanel = new DeckPanel(player.getDeck());
        add(deckPanel);
    }

    public void addNum() {
        add(createNumPanel(player));
    }

    private JPanel createNumPanel(Player player) {

        JPanel numPanel = new JPanel();
        numPanel.setOpaque(false);
        numPanel.setLayout(new FlowLayout(FlowLayout.LEFT,0 ,0));
        numPanel.setPreferredSize(new Dimension(120, 50));

        int num = 21;

        if (player != null) {
            num = player.getDeck().size();
        }

        String path = "/assets/" + (num / 10) + ".png";
        JLabel labelComponent = GUIUtility.renderLabel(path, "/assets/0.png", 50, 50);
        numLabels.add(labelComponent);
        numPanel.add(labelComponent);

        // create digit 2
        path = "/assets/" + (num % 10) + ".png";
        labelComponent = GUIUtility.renderLabel(path, "/assets/0.png", 50, 50);
        numLabels.add(labelComponent);
        numPanel.add(labelComponent);

        return numPanel;
    }

    public void updateCardPanel(int idx, Card card) {
        CardPanel cardPanel = cardPanels.get(idx);
        cardPanel.update(card);
    }

    public void updateDeckPanel() {
        deckPanel.updateDeck();
    }

    public void updateNumPanel() {

        int num = player.getDeck().size();
        
        // first digit
        String path = "/assets/" + (num / 10) + ".png";
        GUIUtility.renderLabel(numLabels.get(0), path, "/assets/0.png", 50, 50);

        // second digit
        path = "/assets/" + (num % 10) + ".png";
        GUIUtility.renderLabel(numLabels.get(1), path, "/assets/0.png", 50, 50);
        
    }


}

