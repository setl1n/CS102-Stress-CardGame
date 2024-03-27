package cardcollections;

import cardcollections.deckcomponents.*;
import cardcollections.*;
import GUI.gamecontainer.pilecontainer.PilePanel;

public class Pile extends CardCollection {
    private PilePanel pilePanel;

    @Override
    public void add(Card card) {
        super.add(card);
        updateTopCard();
    }

    private void updateTopCard() {
        if (pilePanel != null) {
            pilePanel.updatePilePanel(super.peekTopCard());
        }
    }

    public Card peekTopCard() {
        return super.peekTopCard();
    }

    public void setPilePanel(PilePanel pilePanel) {
        this.pilePanel = pilePanel;
    }

    @Override 
    public String toString() {
        return String.format("Top Card: %s\n", isEmpty()? "Pile is Empty" : peekTopCard());
    }

}
                                                                                                                         