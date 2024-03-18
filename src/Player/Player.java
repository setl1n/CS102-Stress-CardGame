package Player;

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

    public Deck getPlayerDeck(){
        return deck;
    }

    public Hand getPlayerHand(){
        return hand;
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
            GameLogicUtils.checkNeedToResetGame(game);
            
        } else {
            // invalid move, add forefeit? like cooldown or smth?
            System.out.println("INVALID MOVE");
        }
    }

    public static void Stress(Player opponent, Pile[] piles, Game game){
        Deck opponentDeck = opponent.getPlayerDeck();

        if (GameLogicUtils.isValidStress(piles[0].peekTopCard(), piles[1].peekTopCard())){
            // add pile to loser's hand
            for (Pile p : piles){
                opponentDeck.transfer(p);
            }
            // shuffles opponent's deck
            opponentDeck.shuffle();
            // game "restarts"
            game.openCardsToStart();
            // GameLogicUtils.resetGameIfNoValidMoves(game);
        }
    }

    @Override
    public String toString() {
        return hand.toString() + "\nTargetPile: " + targetPile + "\nCards left in Deck: " + deck.size();
    }
    
}
