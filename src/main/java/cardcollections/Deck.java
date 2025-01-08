package cardcollections;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

import gui.panels.gamecontainer.playercontainer.NumPanel;

/**
 * This class represents a deck of cards. It extends the CardCollection class 
 * and provides additional functionality such as changing the deck colour, 
 * updating the deck image, and splitting the deck.
 */
public class Deck extends CardCollection {
    private Image deckImage;
    private char colour;
    private NumPanel numPanel;

    /**
     * Constructs a new Deck and initializes it. If the deck is not empty, 
     * it is filled with 52 cards.
     * @param isEmpty A boolean indicating whether the deck should be empty.
     */
    public Deck(Boolean isEmpty) {
        super();
        if (!isEmpty) {
            for (Suit suit : Suit.VALUES) {
                for (Rank rank : Rank.VALUES) {
                    add(new Card(suit, rank));
                }
            }
        }

        colour = 'r';           // defaults red deck
        updateImageToSize();
    }

    /**
     * Changes the colour of the deck.
     */
    public void changeColour() {
        if (colour == 'r') {
            colour = 'b';
        } else {
            colour = 'r';
        }
        updateImageToSize();
    }

    /**
     * Updates the image of the deck based on its size.
     */
    public void updateImageToSize() {
        int cardsInDeck = super.size();
        
        if (cardsInDeck == 0) {
            String path = "assets/emptyDeck.png";  // Changed from /assets to assets
            URL imgUrl = getClass().getClassLoader().getResource(path);
            
            if (imgUrl != null) {
                deckImage = new ImageIcon(imgUrl).getImage();
            } else {
                System.err.println("[ERROR] Could not find resource: " + path);
            }
            return;
        }

        String colourPath = colour == 'r' ? "red" : "blu";
        String thickness = "Med";

        if (cardsInDeck < 5) thickness = "Single";
        else if (cardsInDeck < 15) thickness = "Thin";
        else if (cardsInDeck < 30) thickness = "Med";
        else thickness = "Thick";

        String path = "assets/" + colourPath + "Deck" + thickness + ".png";  // Changed from /assets to assets
        
        URL imgUrl = getClass().getClassLoader().getResource(path);
        
        if (imgUrl == null) {
            imgUrl = getClass().getClassLoader().getResource("assets/empty.png");
        }
        
        if (imgUrl != null) {
            ImageIcon icon = new ImageIcon(imgUrl);
            deckImage = icon.getImage();
        } else {
            System.err.println("[ERROR] Both primary and fallback resource loading failed");
        }
    }

    /*
     * The following methods are made public to caller as they are default in CardCollection
     * This is good OOP as it's more important to encapsulate
     * by having a "layer" in the child class than to make CardCollections methods public
    */

    /**
     * Returns the size of the deck.
     * @return The size of the deck.
     */
    public int size() {
        return super.size();
    }

    /**
     * Shuffles the deck.
     */
    public void shuffle() {
        super.shuffle();
    }
    /**
     * Checks if the deck is empty.
     * @return True if the deck is empty, false otherwise.
     */
    public boolean isEmpty() {
        return super.isEmpty();
    }

    /**
     * Checks if the size of the deck is at least 2.
     * @return True if the size of the deck is at least 2, false otherwise.
     */
    public boolean isSizeAtLeast2() {
        return size() >= 2;
    }

    /**
     * Removes and returns the top card from the deck.
     * @return The top card from the deck.
     */
    public Card popTopCard() {
        Card topCard = super.popTopCard();
        if (numPanel != null) {
            numPanel.updateNum();
        }
        return topCard;
    }

    /**
     * Transfers all cards to another CardCollection from this deck.
     * @param c The CardCollection to which cards are to be transferred.
     */
    public void transfer(CardCollection c) {
        super.transfer(c);
        if (numPanel != null) {
            numPanel.updateNum();
        }
    }

    /**
     * Returns the image of the deck.
     * @return The image of the deck.
     */
    public Image getDeckImage() {
        return deckImage;
    }
    
    /**
     * Splits the deck in half and returns a new deck with the top half of the original deck.
     * @return A new deck with the top half of the original deck.
     */
    public Deck splitAndReturnHalf() {
        Deck deckToReturn = new Deck(true);
        int cardsToTransfer = this.size() / 2;
        for (int i = 0; i < cardsToTransfer; i++) {
            Card cardToTransfer = this.popTopCard();
            deckToReturn.add(cardToTransfer);
        }
        return deckToReturn;
    }

    /**
     * Returns a string representation of the deck.
     * @return A string representation of the deck.
     */
    @Override
    public String toString() {
        return String.format("Cards left in Deck: %s\n", size());
    }
}