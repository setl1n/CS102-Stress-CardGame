package Player;

import Collections.Deck;
import Collections.Pile;
import Collections.DeckComponents.Card;
import GUI.SoundController;
import Game.GameLogicUtils;

public class Player {

    private SoundController help = new SoundController();

    private Deck deck;
    private Hand hand;
    private int targetPile;

    public Player(Deck deck){
        this.deck = deck;
        hand = new Hand(deck);
        targetPile = 0;
    }

    public Hand getHand() {
        return hand;
    }

    public int getTargetPile() {
        return targetPile;
    }

    public void selectTargetPile(char userInput) {
        if (userInput == 'l') {
            this.targetPile = 0;
        } else if (userInput == 'r'){
            this.targetPile = 1;
        } else{
            // debug statement, remove before submission
            System.out.println("INVALID USER INPUT, TARGET DECK WAS NOT CHANGED");
        }
    }

    public void drawCard() {
        hand.drawCard(deck);
    }

    public void drawFourCards() {
        for (int i = 0; i < 4; i++) {
            drawCard();;
        }
    }

    public boolean isEmptyDeck() {
        return deck.isEmpty();
    }

    public void openCardToPile(Pile pileToOpenTo) {
        pileToOpenTo.add(deck.popTopCard());
    }

    public void throwCardToPile(int index, Pile pile) {
        Card topCardOfPile = pile.peekTopCard();

        if (GameLogicUtils.isValidThrow(hand.getCardAtIndex(index), topCardOfPile)) {
            pile.add(hand.popCardAtIndex(index));
            drawCard();
            help.cardSound(); // Play sound after moving the card
        } else {
            // invalid move, add forefeit? like cooldown or smth?
            System.out.println("INVALID MOVE");
        }
    }

    @Override
    public String toString() {
        return hand.toString() + "\nTargetPile: " + targetPile + "\nCards left in Deck: " + deck.size();
    }
}