package src.Cards;

import java.net.URL;

public class Card {
    private String suit;
    private String rank;

    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public String getSuit() {
        return suit;
    }

    public String getRank() {
        return rank;
    }

    public URL getCardImage() {
        String imagePath = "/assets/" + suit + "-" + rank + ".png";
        URL imgUrl = getClass().getResource(imagePath);
        if (imgUrl == null) {
            throw new RuntimeException("Image not found: " + imagePath);
        }
        return imgUrl;
    }
}