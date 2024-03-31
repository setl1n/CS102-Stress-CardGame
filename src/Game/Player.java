package game;

import cardcollections.*;
import gui.*;
import gui.panels.gamecontainer.pilecontainer.IndicatorPanel;
import gui.panels.gamecontainer.playercontainer.PlayerPanel;

public class Player {

    private String name;
    private Deck deck;
    private Hand hand;
    private long blockUntil = 0;

    private PlayerPanel playerPanel;
    private IndicatorPanel indicatorPanel;

    private int targetPileIndex;

    public Player(String name, Deck deck) {
        this.name = name;
        this.deck = deck;
        hand = new Hand();
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

    public boolean isBlocked() {
        return System.currentTimeMillis() < this.blockUntil;
    }

    public void blockFor(int milliseconds) {
        Sounds.invalidSound();
        Overlays.blockedFor(playerPanel, milliseconds);
        this.blockUntil = System.currentTimeMillis() + milliseconds;
    }

    private void drawCard(boolean playAudio) {
        hand.drawCard(deck);
        if (playerPanel != null) {
            playerPanel.updateAll();
        }
        if (playAudio) {
            Sounds.cardSound();
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
            Sounds.cardSound();
        }
    }

    public void throwCardToPile(int index, Pile[] piles) {
        Pile targetPile = piles[targetPileIndex];
        Card topCardOfPile = targetPile.peekTopCard();

        if (GameLogicUtils.isValidThrow(hand.getCardAtIndex(index), topCardOfPile)) {

            Overlays.renderCardTransition(playerPanel.getCardPanelAtIndex(index), name);
            Overlays.renderCardTransition(targetPile.getPilePanel(), name);
            Card thrownCard = hand.popCardAtIndex(index);
            targetPile.add(thrownCard);

            drawCard(true);

            if (playerPanel != null) {
                playerPanel.updateAll();
            }

        } else {
            // Penalty for invalid throwing card to pile: 1 seconds
            System.out.println("INVALID MOVE, actions frozen for 1s");
            blockFor(1000);
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