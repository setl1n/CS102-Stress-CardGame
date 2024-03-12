package Player;

import java.util.Arrays;

import Collections.Deck;
import Collections.Pile;
import Collections.DeckComponents.Card;
import Game.Game;
import Game.GameLogicUtils;

public class Player {
    private Deck deck;
    private Hand hand;
    private int targetPile;

    public Player(Deck deck){
        this.deck = deck;
        hand = new Hand(deck);
        targetPile = 0;
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
        if (deck.isEmpty()) {
            return;
        }
        // note different method from player.drawcard
        hand.drawCard(deck);
    }

    public void drawFourCards() {
        for (int i = 0; i < 4; i++) {
            this.drawCard();;
        }
    }

    public boolean isEmptyDeck() {
        return deck.isEmpty();
    }

    public void openCardToPile(Pile pileToOpenTo) {
        pileToOpenTo.add(deck.popTopCard());
    }

    public void throwCardToPile(int index, Game game) {
        Card topCardOfPile = game.getPile(targetPile).peekTopCard();
        if (GameLogicUtils.isValidThrow(hand.getCardAtIndex(index), topCardOfPile)) {
            game.getPile(targetPile).add(hand.getCardAtIndex(index));
            hand.removeCardAtIndex(index);
            drawCard();
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
