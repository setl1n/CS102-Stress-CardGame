package Collections;

import Collections.DeckComponents.Card;
import GUI.Panels.GamePanelConponents.PilePanel;

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
            pilePanel.repaint();
        }
    }

    public void setPilePanel(PilePanel pilePanel) {
        this.pilePanel = pilePanel;
    }

}
                                                                                                                         