package cardcollections;

import java.util.*;

/**
 * This class represents a suit of a card. It provides methods to get the name 
 * and symbol of the suit.
 */
public final class Suit {
    /**
     * The name of the suit.
     */
    private String name;

    /**
     * The symbol of the suit.
     */
    private String symbol;

    /**
     * Predefined suit values.
     */
    public final static Suit CLUBS = new Suit("Clubs", "C");
    public final static Suit DIAMONDS = new Suit("Diamonds", "D");
    public final static Suit HEARTS = new Suit("Hearts", "H");
    public final static Suit SPADES = new Suit("Spades", "S");

    /**
     * An unmodifiable list of all suit values.
     */
    public final static List<Suit> VALUES = Collections.unmodifiableList(
        Arrays.asList(
            CLUBS, 
            DIAMONDS, 
            HEARTS, 
            SPADES
        )
    );

    /**
     * Private constructor to ensure only predefined values are used.
     * @param nameValue The name of the suit.
     * @param symbolValue The symbol of the suit.
     */
    private Suit(String nameValue, String symbolValue) {
        name = nameValue;
        symbol = symbolValue;
    }

    /**
     * Returns a string representation of the suit.
     * @return A string representation of the suit.
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Returns the symbol of the suit.
     * @return The symbol of the suit.
     */
    public String getSymbol() {
        return symbol;
    }
}