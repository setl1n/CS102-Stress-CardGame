package game;

import cardcollections.*;
import gui.*;
import gui.panels.gamecontainer.pilecontainer.IndicatorPanel;
import gui.panels.gamecontainer.playercontainer.PlayerPanel;

/**
 * This class represents a player in the game.
 */
public class Player {

    private String name;
    private Deck deck;
    private Hand hand;
    private long blockUntil = 0;

    private PlayerPanel playerPanel;
    private IndicatorPanel indicatorPanel;

    private int targetPileIndex;

    private static final int WRONG_MOVE_PENALTY = 1000;

    /**
     * Constructor for the Player class.
     * @param name The name of the player.
     * @param deck The deck of the player.
     */
    public Player(String name, Deck deck) {
        this.name = name;
        this.deck = deck;
        hand = new Hand();
        drawFourCards();
        targetPileIndex = 0;
    }
    
    /**
     * Draws a card from the deck.
     * @param playAudio Whether to play the card sound.
     */
    private void drawCard(boolean playAudio) {
        hand.drawCard(deck);
        if (playerPanel != null) {
            playerPanel.updateAll();
        }
        if (playAudio) {
            Sounds.cardSound();
        }
    }
    
    /**
     * Draws four cards from the deck.
     * Ensures that hand is full.
     */
    public void drawFourCards() {
        for (int i = 0; i < 4; i++) {
            drawCard(false);
        }
    }
    
    /**
     * Opens a card to a pile and updates of player panel
     * @param pileToOpenTo The pile to open the card to.
     * @param playAudio Whether to play the card sound.
     */
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
    
    /**
     * Throws a card to a target pile from hand.
     * @param index The index of the card in the hand.
     * @param piles The piles in the game.
     */
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
            blockFor(WRONG_MOVE_PENALTY);
        }
    }

    /**
     * Sets the target pile index.
     * @param targetPileIndex The target pile index.
     */
    public void setTargetPileIndex(int targetPileIndex) {
        // Changes internal variable
        this.targetPileIndex = targetPileIndex;
        
        // Change GUI (if set)
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

    /**
     * Checks if the player is blocked after invalid move.
     * @return true if the player is blocked, false otherwise.
     */
    public boolean isBlocked() {
        return System.currentTimeMillis() < this.blockUntil;
    }

    /**
     * Blocks the player for a certain amount of time.
     * @param milliseconds The amount of time to block the player for.
     */
    public void blockFor(int milliseconds) {
        Sounds.invalidSound();
        Overlays.blockedFor(playerPanel, milliseconds);
        this.blockUntil = System.currentTimeMillis() + milliseconds;
    }
    
   /**
     * Sets the player panel.
     * @param playerPanel The player panel.
     */
    public void setPlayerPanel(PlayerPanel playerPanel) {
        this.playerPanel = playerPanel;
    }
    
    /**
     * Sets the indicator panel.
     * @param indicatorPanel The indicator panel.
     */
    public void setIndicatorPanel(IndicatorPanel indicatorPanel) {
        this.indicatorPanel = indicatorPanel;
    }
    
    /**
     * Returns a string representation of the player.
     * @return A string representation of the player.
     */
    @Override
    public String toString() {
        return name + ":\n" + "Target Pile: " + (targetPileIndex == 0 ? "Pile 1\n" : "Pile 2\n") + hand + deck;
    }
    
    /**
     * Returns the hand of the player.
     * @return The hand of the player.
     */
    public Hand getHand() {
        return hand;
    }

    /**
     * Returns the deck of the player.
     * @return The deck of the player.
     */
    public Deck getDeck() {
        return deck;
    }

    /**
     * Returns the name of the player.
     * @return The name of the player.
     */
    public String getName() {
        return name;
    }
}
