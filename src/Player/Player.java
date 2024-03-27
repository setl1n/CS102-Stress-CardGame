package player;

import cardcollections.deckcomponents.*;
import game.*;
import cardcollections.*;
import GUI.SoundUtility;
import GUI.gamecontainer.pilecontainer.IndicatorPanel;
import GUI.gamecontainer.playercontainer.PlayerPanel;

import java.io.*;

public class Player {

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

    /*
     * Getter Methods
     */

    public Hand getHand() {
        return hand;
    }

    public int getTargetPile() {
        return targetPileIndex;
    }

    public Deck getDeck() {
        return deck;
    }

    public String getName() {
        return name;
    }

    public boolean isEmptyDeck() {
        return deck.isEmpty();
    }

    /*
     * Gameplay Methods
     */

    public void drawCard() {
        hand.drawCard(deck);
    }

    public void drawFourCards() {
        for (int i = 0; i < 4; i++) {
            drawCard();
        }
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

            Card thrownCard = hand.popCardAtIndex(index);

            targetPile.add(thrownCard);
            
            drawCard();
            SoundUtility.cardSound(); // Play sound after moving the card

            if (playerPanel != null) {
                playerPanel.updateCardPanel(index, hand.getCardAtIndex(index));
                playerPanel.updateNumPanel();
                playerPanel.updateDeckPanel();
            }

        } else {
            // invalid move, add forefeit? like cooldown or smth?
            System.out.println("INVALID MOVE");
        }
    }

    public void setTargetPileIndex(int targetPileIndex) {
        this.targetPileIndex = targetPileIndex;

        if (indicatorPanel != null) {

            if (targetPileIndex == 0) {
                indicatorPanel.setPositionToLeft();

            } else if (targetPileIndex == 1) {
                indicatorPanel.setPositionToRight();

            } else {
                System.out.println("### INVALID TARGETPILE INDEX ###");
            }
        }
    }

    /*
     * GUI Specific Methods
     */

    public void setPlayerPanel(PlayerPanel playerPanel) {
        this.playerPanel = playerPanel;
    }

    public void setIndicatorPanel(IndicatorPanel indicatorPanel) {
        this.indicatorPanel = indicatorPanel;
    }


    @Override
    public String toString() {
        return hand + "\nTargetPile: " + (targetPileIndex == 0 ? "Pile 1" : "Pile 2") + deck;
    }
}