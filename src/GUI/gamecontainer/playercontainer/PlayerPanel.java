package gui.gamecontainer.playercontainer;

import java.util.*;

import java.awt.*;

import javax.swing.JLabel;
import javax.swing.JPanel;

import player.*;
import gui.GUIUtility;
import cardcollections.deckcomponents.*;

public class PlayerPanel extends JPanel {

    private Player player;
    private List<CardPanel> cardPanels = new ArrayList<>();
    private NumPanel numPanel;
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
            addNumPanel();

        } else if ("Player 2".equals(player.getName())) {

            setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 15));
            addNumPanel();
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

    public void addNumPanel() {
        NumPanel temp = new NumPanel(player.getDeck());
        this.numPanel = temp;
        add(temp);
    }

    public CardPanel getCardPanelAtIndex(int index) {
        return cardPanels.get(index);
    }

    public Player getPlayer() {
        return player;
    }

    public void updateCardPanel(int idx, Card card) {
        CardPanel cardPanel = cardPanels.get(idx);
        cardPanel.update(card);
    }

    public void updateDeckPanel() {
        deckPanel.updateDeck();
    }

    public void updateNumPanel() {
        numPanel.updateNum();
    }

    public void updateAll() {
        updateDeckPanel();
        updateNumPanel();
        for (int i = 0; i < 4; i++) {
            updateCardPanel(i, player.getHand().getCardAtIndex(i));
        }
    }


}

