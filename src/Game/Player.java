package src.Game;

import src.Cards.Hand;

public class Player {

    private boolean pile1Active;

    private Hand hand;
    private String name;
    
    public Player(Hand hand, String name) {
        this.hand = hand;
        this.name = name;

        // set deck1 as default deck
        pile1Active = true;
    }

    public Hand getHand() {
        return hand;
    }

    public String getName() {
        return name;
    }

    public boolean isPile1Active() {
        return pile1Active;
    }

    public void toggleActivePile() {
        pile1Active = !pile1Active;
    }
}
