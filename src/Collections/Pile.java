package Collections;

import Collections.DeckComponents.Card;

public class Pile extends CardCollection {
    private Card topCard;

    public Pile(){
    }

    public Card peekTopCard(){
        return topCard;
    }

    @Override
    public void add(Card card) {
        super.add(card);
        updateTopCard();
    }

    private void updateTopCard() {
        this.topCard = super.peekTopCard();
    }

}
                                                                                                                         