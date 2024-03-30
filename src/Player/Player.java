package player;

import gui.*;
import gui.panels.gamecontainer.pilecontainer.*;
import gui.panels.gamecontainer.playercontainer.*;

import java.io.*;
import game.*;
import cardcollections.*;
import cardcollections.deckcomponents.Card;

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
        drawFourCards(false);
        targetPileIndex = 0;
    }

    /*
     * Getter Methods
     */

    public Hand getHand() {
        return hand;
    }

    public Deck getDeck() {
        return deck;
    }

    public String getName() {
        return name;
    }

    /*
     * Gameplay Methods
     */

    private void drawCard(boolean playAudio) {
        hand.drawCard(deck);
        if (playerPanel != null) {
            playerPanel.updateAll();
        }
        if (playAudio) {
            SoundUtility.cardSound();
        }
    }

    public void drawFourCards(boolean playAudio) {
        for (int i = 0; i < 4; i++) {
            drawCard(playAudio);
        }
    }

    public void openCardToPile(Pile pileToOpenTo, boolean playAudio) {
        pileToOpenTo.add(deck.popTopCard());
        if (playerPanel != null) {
            playerPanel.repaint();
            playerPanel.updateAll();
        }
        if (playAudio) {
            SoundUtility.cardSound();
        }
    }

    public void throwCardToPile(int index, Pile[] piles) {
        Pile targetPile = piles[targetPileIndex];
        Card topCardOfPile = targetPile.peekTopCard();

        if (GameLogicUtils.isValidThrow(hand.getCardAtIndex(index), topCardOfPile)) {

            Overlays.renderCardTransition(playerPanel.getCardPanelAtIndex(index), this, "/assets/transition");
            Overlays.renderCardTransition(targetPile.getPilePanel(), this, "/assets/transition");
            Card thrownCard = hand.popCardAtIndex(index);
            targetPile.add(thrownCard);

            drawCard(true);

            if (playerPanel != null) {
                playerPanel.updateAll();
            }

        } else {
            // invalid move, add forefeit? like cooldown or smth?
            SoundUtility.invalidSound();
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
        return name + ":\n" + "Target Pile: " + (targetPileIndex == 0 ? "Pile 1\n" : "Pile 2\n") + hand + deck;
    }
}