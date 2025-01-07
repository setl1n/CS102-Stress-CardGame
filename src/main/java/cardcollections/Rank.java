package cardcollections;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This class represents a rank of a card. It implements Comparable interface 
 * to compare ranks of two cards.
 */
public final class Rank implements Comparable<Rank> {
    /**
     * The name of the rank.
     */
    private String name;

    /**
     * The symbol of the rank.
     */
    private String symbol;

    /**
     * Predefined rank values.
     */
    public final static Rank ACE = new Rank("Ace", "A");
    public final static Rank TWO = new Rank("Two", "2");
    public final static Rank THREE = new Rank("Three", "3");
    public final static Rank FOUR = new Rank("Four", "4");
    public final static Rank FIVE = new Rank("Five", "5");
    public final static Rank SIX = new Rank("Six", "6");
    public final static Rank SEVEN = new Rank("Seven", "7");
    public final static Rank EIGHT = new Rank("Eight", "8");
    public final static Rank NINE = new Rank("Nine", "9");
    public final static Rank TEN = new Rank("Ten", "T");
    public final static Rank JACK = new Rank("Jack", "J");
    public final static Rank QUEEN = new Rank("Queen", "Q");
    public final static Rank KING = new Rank("King", "K");

    /**
     * An unmodifiable list of all rank values.
     */
    public final static List<Rank> VALUES = Collections.unmodifiableList(
        Arrays.asList(
            ACE, 
            TWO, 
            THREE, 
            FOUR, 
            FIVE, 
            SIX, 
            SEVEN, 
            EIGHT, 
            NINE, 
            TEN, 
            JACK, 
            QUEEN, 
            KING
        )
    );

    /**
     * Private constructor to ensure only predefined values are used.
     * @param nameValue The name of the rank.
     * @param symbolValue The symbol of the rank.
     */
    private Rank(String nameValue, String symbolValue) {
        name = nameValue;
        symbol = symbolValue;
    }

    /**
     * Returns the symbol of the rank.
     * @return The symbol of the rank.
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Compares this rank with another rank.
     * @param otherRank The other rank to be compared.
     * @return A negative integer, zero, or a positive integer as this rank is less than, equal to, or greater than the specified rank.
     */
    @Override
    public int compareTo(Rank otherRank) {
        return VALUES.indexOf(this) - VALUES.indexOf(otherRank);
    }

    /**
     * Returns a string representation of the rank.
     * @return A string representation of the rank.
     */
    @Override
    public String toString() {
        return name;
    }
}