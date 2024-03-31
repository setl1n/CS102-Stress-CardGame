package cardcollections;

import java.net.URL;
import java.awt.*;
import javax.swing.*;

public class Card implements Comparable<Card> {

    private Suit suitValue;
    private Rank rankValue;
    private Image cardImage;

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

    public Image getCardImage() {
        return cardImage;
    }

    @Override
    public String toString() {
        return rankValue.toString() + " of " + suitValue.toString();
    }
    
    /*
     * This comparable is only concerned with the rank value of the card, not the suit
     */
    @Override
    public int compareTo(Card otherCard) {
        return rankValue.compareTo(otherCard.rankValue);
    }

}
