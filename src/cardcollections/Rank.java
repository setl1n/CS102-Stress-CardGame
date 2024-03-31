package cardcollections;

import java.util.*;

public class Rank implements Comparable<Rank> {
    private String name;
    private String symbol;

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

    // Constructor - declared private as only predefined values should be used
    private Rank(String nameValue, String symbolValue) {
        name = nameValue;
        symbol = symbolValue;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public int compareTo(Rank otherRank) {
        return VALUES.indexOf(this) - VALUES.indexOf(otherRank);
    }

    @Override
    public String toString() {
        return name;
    }
}