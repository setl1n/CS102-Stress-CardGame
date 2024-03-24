package Player;

import Collections.Deck;
import Collections.Pile;
import Collections.DeckComponents.Card;
import GUI.SoundController;
import GUI.Panels.GamePanelConponents.PlayerPanel;
import GUI.Panels.GamePanelConponents.IndicatorPanel;
import java.io.*;
import Game.Game;
import Game.GameLogicUtils;

public class Player {

    private SoundController help = new SoundController();
    private String name;
    private Deck deck;
    private Hand hand = new Hand();
    private PlayerPanel playerPanel;
    private IndicatorPanel indicatorPanel;
    private int targetPileIndex;

    public Player(String name, Deck deck) {
        this.name = name;
        this.deck = deck;
        drawFourCards();
        targetPileIndex = 0;
    }

    public Hand getHand() {
        return hand;
    }

    public int getTargetPile() {
        return targetPileIndex;
    }

    public void setTargetPileIndex(int targetPileIndex) {
        this.targetPileIndex = targetPileIndex;
    }

    public void setPlayerPanel(PlayerPanel playerPanel) {
        this.playerPanel = playerPanel;
    }

    public void setIndicatorPanel(IndicatorPanel indicatorPanel) {
        this.indicatorPanel = indicatorPanel;
    }

    public void drawCard() {
        hand.drawCard(deck);
        if (playerPanel != null) {
            playerPanel.repaint();
        }
    }

    public void drawFourCards() {
        for (int i = 0; i < 4; i++) {
            drawCard();
        }
    }

    public boolean isEmptyDeck() {
        return deck.isEmpty();
    }

    public Deck getDeck() {
        return deck;
    }

    public String getName() {
        return name;
    }

    public void openCardToPile(Pile pileToOpenTo) {
        pileToOpenTo.add(deck.popTopCard());
        if (playerPanel != null) {
            playerPanel.repaint();
        }
    }

    public void throwCardToPile(int index, Pile[] piles) {
        Pile targetPile = piles[targetPileIndex];
        Card topCardOfPile = targetPile.peekTopCard();

        if (GameLogicUtils.isValidThrow(hand.getCardAtIndex(index), topCardOfPile)) {
            targetPile.add(hand.popCardAtIndex(index));
            drawCard();
            help.cardSound(); // Play sound after moving the card
            if (playerPanel != null) {
                playerPanel.repaint();
            }
        } else {
            // invalid move, add forefeit? like cooldown or smth?
            System.out.println("INVALID MOVE");
        }
    }

    @Override
    public String toString() {
        return hand.toString() + "\nTargetPile: " + targetPileIndex + "\nCards left in Deck: " + deck.size();
    }
}