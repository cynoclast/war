package com.cynoclast.war;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Trampas Kirk
 */
public class Player {

    /**
     * Allows us to differentiate players in a very simple way.
     */
    private int ordinal;

    /**
     * The player's cards.
     */
    private List<Card> hand = new LinkedList<>();

    /**
     * Plays one card from the top of the hand.
     * @return one card if there are any, otherwise null
     */
    public Card play() {
        if (hand.size() == 0) {
            return null;
        }
        Card topCard = hand.get(0);
        hand.remove(0);
        return topCard;
    }

    /**
     * Shows the player's top card
     * @return the top card if there are any, otherwise null
     */
    public Card show() {
        if (hand.size() == 0) {
            return null;
        }
        return hand.get(0);
    }

    /**
     * Adds a card to the hand. Adds to the end/bottom.
     * @param card the card to add
     */
    public void collect(Card card) {
        hand.add(hand.size(), card);
    }

    /**
     * Adds cards to the hand. Adds to the end/bottom.
     * @param cards the cards to add
     */
    public void collect(List<Card> cards) {
        hand.addAll(hand.size(), cards);
    }

    /**
     * Creates a player with an ordinal
     * @param ordinal the ordinal to use
     */
    public Player(int ordinal) {
        this.ordinal = ordinal;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).
                append("ordinal", ordinal).
                append("hand", hand).
                toString();
    }

    public int getOrdinal() {
        return ordinal;
    }

    public List<Card> getHand() {
        return hand;
    }

}

