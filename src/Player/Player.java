package Player;

import java.util.Arrays;

import Collections.Deck;
import Collections.Pile;
import Collections.DeckComponents.Card;
import Game.Game;
import Game.GameLogicUtils;

public class Player {
    private Deck deck;
    private Card[] hand;
    private int targetPile;

    public Player(Deck deck){
        this.deck = deck;
        this.hand = new Card[4];
        this.targetPile = 0;
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

    // purposely coded in a way (the algo) to throw null pointer exception 
    // if incorrectly used down the line.
    // should prob recode before submission.
    // to (for int i = 0 i < 4)?? idk
    private void addCard(Card card) throws NullPointerException {
        int index = 0;
        while (hand[index] != null) {
            index++;
        }
        hand[index] = card;
    }

    // deliberately did not throw exception for when hand is full as our programme should naturally avoid that
    // throws null pointer if u call to add card when hand is full
    // need to implement when u attempt to draw from empty deck
    public void drawCard() throws NullPointerException {
        if (deck.isEmpty()) {
            // implement smth?? need or not?? idk
            return;
        }
        this.addCard(deck.popTopCard());
    }

    public void drawFourCards() {
        for (int i = 0; i < 4; i++) {
            drawCard();
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
        if (GameLogicUtils.isValidThrow(hand[index], topCardOfPile)) {
            game.getPile(targetPile).add(hand[index]);
            hand[index] = null;
            drawCard();
        } else {
            // invalid move, add forefeit? like cooldown or smth?
            System.out.println("INVALID MOVE");
        }
    }

    @Override
    public String toString() {
        return "Hand: " + Arrays.toString(hand) + "\nTargetPile: " + targetPile + "\nCards left in Deck: " + deck.size();
    }
    
}
