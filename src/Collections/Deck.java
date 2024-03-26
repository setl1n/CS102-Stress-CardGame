package collections;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

import collections.deckcomponents.*;
import GUI.gamecontainer.playercontainer.NumPanel;
import collections.deckcomponents.cardcomponents.*;

public class Deck extends CardCollection {
    private Image deckImage;
    private char colour;
    private NumPanel numPanel;

    /*
     * Creates a shuffled deck of 52 cards
     */
    public Deck(Boolean isEmpty) {
        super();
        if (!isEmpty) {
            for (Suit suit : (Iterable<Suit>) Suit.VALUES) {
                for (Rank rank : (Iterable<Rank>) Rank.VALUES) {
                    add(new Card(suit, rank));
                }
            }
        }

        // defaults red deck
        colour = 'r';
        updateImageToSize();
    }

    public void changeColour() {
        if (colour == 'r') {
            colour = 'b';
        } else {
            colour = 'r';
        }
        updateImageToSize();
    }

    public void updateImageToSize() {
        int cardsInDeck = super.size();
        if (cardsInDeck == 0) {

            String path = "/assets/" + colour + "DeckEmpty.png";
            URL imgUrl = getClass().getResource(path);
            deckImage = new ImageIcon(imgUrl).getImage();
            return;
        }

        String colourPath = colour == 'r' ? "red" : "blu";
        String thickness = "Med";

        if (cardsInDeck < 5) {
            thickness = "Single";
        } else if (cardsInDeck < 15) {
            thickness = "Thin";
        } else if (cardsInDeck < 30) {
            thickness = "Med";
        } else {
            thickness = "Thick";
        }

        String path = "/assets/" + colourPath + "Deck" + thickness + ".png";
        URL imgUrl = getClass().getResource(path);
        if (imgUrl == null) {
            imgUrl = getClass().getResource("/assets/empty.png");
        }
        // It avoids dealing with IOException, which ImageIO.read() might throw.
        deckImage = new ImageIcon(imgUrl).getImage();
    }

    // makes shuffle available to caller as shuffle is protected in cardcollection
    // checked with chatgpt, this is good OOP as it's more important to encapsulate
    // alternatively, need to make arraylist cardcollections protected, which is
    // worse than
    // having a "layer" in the child class for shuffle
    public void shuffle() {
        super.shuffle();
    }

    public boolean isEmpty() {
        return super.isEmpty();
    }

    public int size() {
        return super.size();
    }

    public Card popTopCard() {
        Card topCard = super.popTopCard();
        if (numPanel != null) {
            numPanel.updateNum();
        }
        return topCard;
    }

    public void transfer(CardCollection c) {
        super.transfer(c);
        if (numPanel != null) {
            numPanel.updateNum();
        }
    }

    public Image getDeckImage() {
        return deckImage;
    }

    // returns a new deck with top half of deck
    // original deck holds (remaining) bottom half
    //
    // note: order of cards that gets added to new deck gets reversed
    // while bottom half of old cards stay in order
    // -should not affect implementation, just for own's knowledge
    public Deck splitAndReturnHalf() {
        Deck deckToReturn = new Deck(true);
        int cardsToTransfer = this.size() / 2;
        for (int i = 0; i < cardsToTransfer; i++) {
            Card cardToTransfer = this.popTopCard();
            deckToReturn.add(cardToTransfer);
        }
        return deckToReturn;
    }

    public void setNumPanel(NumPanel numPanel) {
        this.numPanel = numPanel;
    }
}