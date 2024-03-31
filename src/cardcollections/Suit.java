package cardcollections;

import java.util.*;

public final class Suit {
    private String name;
    private String symbol;

    public final static Suit CLUBS = new Suit("Clubs", "C");
    public final static Suit DIAMONDS = new Suit("Diamonds", "D");
    public final static Suit HEARTS = new Suit("Hearts", "H");
    public final static Suit SPADES = new Suit("Spades", "S");

    public final static List<Suit> VALUES = Collections.unmodifiableList(
        Arrays.asList(
            CLUBS, 
            DIAMONDS, 
            HEARTS, 
            SPADES
        )
    );
    // Constructor - declared private as only predefined values should be used
    private Suit(String nameValue, String symbolValue) {
        name = nameValue;
        symbol = symbolValue;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

}