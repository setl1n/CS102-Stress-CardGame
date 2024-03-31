package cardcollections;

import java.net.URL;
import java.awt.*;
import javax.swing.*;

/**
 * This class represents a playing card with a suit, rank, and associated image.
 */
public class Card implements Comparable<Card> {

    private Suit suitValue;
    private Rank rankValue;
    private Image cardImage;

    /**
     * Constructs a new Card with the specified suit and rank.
     * The card's image is loaded from the /assets or /images directory.
     *
     * @param suit the suit of the card
     * @param rank the rank of the card
     */
    public Card(Suit suit, Rank rank) {
        suitValue = suit;
        rankValue = rank;

        String path = "/assets/" + suit.getSymbol() + "-" + rank.getSymbol() + ".png";
        URL imgUrl = getClass().getResource(path);
        if (imgUrl == null) {
            imgUrl = getClass().getResource("/assets/emptyCard.png");
        }
        // It avoids dealing with IOException, which ImageIO.read() might throw.
        cardImage = new ImageIcon(imgUrl).getImage();
    }

    /**
     * Returns the image of the card.
     *
     * @return the image of the card
     */
    public Image getCardImage() {
        return cardImage;
    }

    /**
     * Returns a string representation of the card, in the format "Rank of Suit".
     *
     * @return a string representation of the card
     */
    @Override
    public String toString() {
        return rankValue.toString() + " of " + suitValue.toString();
    }
    
    /**
     * Compares this card with another card for order. Returns a negative integer,
     * zero, or a positive integer as this card's rank is less than, equal to, or
     * greater than the specified card's rank. The suit is not considered in the
     * comparison.
     *
     * @param otherCard the card to be compared
     * @return a negative integer, zero, or a positive integer as this card's rank
     *         is less than, equal to, or greater than the specified card's rank
     */
    @Override
    public int compareTo(Card otherCard) {
        return rankValue.compareTo(otherCard.rankValue);
    }

}