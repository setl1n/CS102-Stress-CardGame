package Collections;

import Collections.DeckComponents.Card;
import GUI.gamecontainer.pilecontainer.PilePanel;

public class Pile extends CardCollection {
    private Card topCard;
    private PilePanel pilePanel;

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
        if (pilePanel != null) {
            pilePanel.updatePilePanel(topCard);
        }
    }

    public void setPilePanel(PilePanel pilePanel) {
        this.pilePanel = pilePanel;
    }

}
                                                                                                                         