package gui.panels.gamecontainer.playercontainer;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import cardcollections.Card;
import game.Player;

public class PlayerPanel extends JPanel {

    private Player player;
    private ArrayList<CardPanel> cardPanels = new ArrayList<>();
    private NumPanel numPanel;
    private DeckPanel deckPanel;

    private static final int WIDTH = 1080;
    private static final int HEIGHT = 240;
    private static final int OFFSET = 5;

    /*
     * Creates a Player panel that contains:
     * 4 Card panels (the HAND)
     * 1 Deck panel (the DECK)
     * 1 Num Panel
     */
    
    public PlayerPanel(Player player) {
        this.player = player;
        player.setPlayerPanel(this);
        this.setOpaque(false);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        
        if ("Player 1".equals(player.getName())) {

            setLayout(new FlowLayout(FlowLayout.LEFT, OFFSET, OFFSET));

            /*
             * Add in order
             * hand / deck / num 
             */

            addHand();
            addDeck();
            addNumPanel();

        } else if ("Player 2".equals(player.getName())) {

            /*
             * Reverse the order
             * num / deck / hand 
             */

            setLayout(new FlowLayout(FlowLayout.RIGHT, OFFSET, OFFSET));
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

    /*
     * Methods to add the various panels
     */

    public void addDeck() {
        this.deckPanel = new DeckPanel(player.getDeck());
        add(deckPanel);
    }

    public void addNumPanel() {
        this.numPanel = new NumPanel(player.getDeck());
        add(numPanel);
    }

    public CardPanel getCardPanelAtIndex(int index) {
        return cardPanels.get(index);
    }

    /*
     * Methods to update the entire playerpanel
     * and its various nested JPanels
     */

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

    /*
     * returns an ArrayList of CardPanels to modify
     * individually if necessary
     */

    public ArrayList<CardPanel> getCardPanels() {
        return cardPanels;
    }
}

