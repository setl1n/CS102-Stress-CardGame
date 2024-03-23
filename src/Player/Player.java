package Player;

import Collections.Deck;
import Collections.Pile;
import Collections.DeckComponents.Card;
import GUI.SoundController;
import Game.Game;
import Game.GameLogicUtils;

public class Player {

    private SoundController help = new SoundController();
    private Deck deck;
    private Hand hand;
    private int targetPileIndex;

    public Player(Deck deck){
        this.deck = deck;
        hand = new Hand(deck);
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

    public void drawCard() {
        hand.drawCard(deck);
    }

    public void drawFourCards() {
        for (int i = 0; i < 4; i++) {
            drawCard();
        }
    }

    public boolean isEmptyDeck() {
        return deck.isEmpty();
    }

    public Deck getDeck(){
        return deck;
    }

    public void openCardToPile(Pile pileToOpenTo) {
        pileToOpenTo.add(deck.popTopCard());
    }

    public void throwCardToPile(int index, Pile[] piles) {
        Pile targetPile = piles[targetPileIndex];
        Card topCardOfPile = targetPile.peekTopCard();

        if (GameLogicUtils.isValidThrow(hand.getCardAtIndex(index), topCardOfPile)) {
            targetPile.add(hand.popCardAtIndex(index));
            drawCard();
            help.cardSound(); // Play sound after moving the card
            
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