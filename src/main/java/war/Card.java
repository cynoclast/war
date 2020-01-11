package war;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Trampas Kirk
 */
public class Card {

    /**
     * An int representing suit. Typically 0-3 representing Clubs, Diamonds, Hearts, Spades.
     */
    private int suit;

    /**
     * An int representing rank. Typically 0-12 representing 2-Ace.
     */
    private int rank;

    public int getRank() {
        return rank;
    }

    public Card(int suit, int rank) {
        this.suit = suit;
        this.rank = rank;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).
                append("suit", suit).
                append("rank", rank).
                toString();
    }
}
