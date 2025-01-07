package cardcollections;

import gui.panels.gamecontainer.pilecontainer.PilePanel;

/**
 * This class represents a pile of cards. It extends the CardCollection class 
 * and provides additional functionality such as updating the top card and 
 * setting or getting the pile panel.
 */
public class Pile extends CardCollection {
    
    /**
     * The panel associated with this pile.
     */
    private PilePanel pilePanel;

    /**
     * Adds a card to the pile and updates the top card.
     * @param card The card to be added.
     */
    @Override
    public void add(Card card) {
        super.add(card);
        updateTopCard();
    }

    /**
     * Updates the top card of the pile panel.
     */
    private void updateTopCard() {
        if (pilePanel != null) {
            pilePanel.updatePilePanel(super.peekTopCard());
        }
    }

    /*
    * This method makes the peekTopCard method public to caller as it is default in CardCollection
    * This is good OOP as it's more important to encapsulate
    * by having a "layer" in the child class than to make CardCollections methods public
    */

    /**
     * Returns the top card of the pile.
     * @return The top card of the pile.
     */
    public Card peekTopCard() {
        return super.peekTopCard();
    }

    /**
     * Sets the pile panel associated with this pile.
     * @param pilePanel The pile panel to be set.
     */
    public void setPilePanel(PilePanel pilePanel) {
        this.pilePanel = pilePanel;
    }
    
    /**
     * Returns the pile panel associated with this pile.
     * @return The pile panel associated with this pile.
     */
    public PilePanel getPilePanel() {
        return pilePanel;
    }

    /**
     * Returns a string representation of the pile.
     * @return A string representation of the pile.
     */
    @Override 
    public String toString() {
        return String.format("Top Card: %s\n", isEmpty()? "Pile is Empty" : peekTopCard());
    }

}
