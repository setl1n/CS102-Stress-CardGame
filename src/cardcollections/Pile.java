package cardcollections;

import gui.panels.gamecontainer.pilecontainer.PilePanel;

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

    /*
     * makes the peekTopCard method available to caller as it is default in CardCollection
     * this is good OOP as it's more important to encapsulate
     * by having a "layer" in the child class then to make CardCollections methods public
    */
    public Card peekTopCard() {
        return super.peekTopCard();
    }

    public void setPilePanel(PilePanel pilePanel) {
        this.pilePanel = pilePanel;
    }
    
    public PilePanel getPilePanel() {
        return pilePanel;
    }

    @Override 
    public String toString() {
        return String.format("Top Card: %s\n", isEmpty()? "Pile is Empty" : peekTopCard());
    }

}
                                                                                                                         