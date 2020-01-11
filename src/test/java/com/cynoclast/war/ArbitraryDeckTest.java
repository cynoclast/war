package com.cynoclast.war;

import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @author Trampas Kirk
 */
public class ArbitraryDeckTest {

    @Test
    public void testCreate() throws Exception {
        Deck deck = new ArbitraryDeck();
        deck.create(1, 1);

        Card card = deck.deal();
        assertNotNull(card);
        card = deck.deal();
        assertNull(card);
    }

    @Test
    public void testShuffle() throws Exception {
        ArbitraryDeck deck = new ArbitraryDeck();
        deck.create(4, 13);

        ArbitraryDeck deck2 = new ArbitraryDeck();
        deck2.create(4, 13);

        deck2.shuffle();

        // the odds of this failing are VERY low with a real card deck, but not impossible
        assertNotEquals(deck.getCards(), deck2.getCards());
    }

    @Test
    public void testDeal() throws Exception {
        Deck deck = new ArbitraryDeck();
        deck.create(1, 1);

        Card card = deck.deal();
        assertNotNull(card);
        card = deck.deal();
        assertNull(card);    }
}