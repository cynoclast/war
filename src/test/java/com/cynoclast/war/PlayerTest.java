package com.cynoclast.war;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Trampas Kirk
 */
public class PlayerTest {

    @Test
    public void testGetOrdinal() {
        Player player = new Player(0);
        assertEquals(0, player.getOrdinal());
    }

    @Test
    public void testToString() {
        Player player = new Player(0);
        assertEquals("0,[]", player.toString());

        Deck deck = new ArbitraryDeck();
        deck.create(1, 2);

        player.collect(deck.deal());
        player.collect(deck.deal());

        assertEquals("0,[0,0, 0,1]", player.toString());
    }

    @Test
    public void testCollect() {

        Deck deck = new ArbitraryDeck();
        deck.create(1, 3);

        Player player = new Player(0);
        player.collect(deck.deal());

        assertEquals(1, player.getHand().size());

        List<Card> winnings = new ArrayList<>();
        winnings.add(deck.deal());
        winnings.add(deck.deal());

        player.collect(winnings);

        assertEquals(3, player.getHand().size());
    }

    @Test
    public void testShow() {
        Deck deck = new ArbitraryDeck();
        deck.create(1, 1);

        Player player = new Player(0);

        assertNull(player.show());

        final Card card = deck.deal();
        player.collect(card);

        assertEquals(1, player.getHand().size());

        Card playerCard = player.show();

        assertEquals(card, playerCard);

        assertEquals(1, player.getHand().size());
    }

    @Test
    public void testPlay() {
        Deck deck = new ArbitraryDeck();
        deck.create(1, 1);

        Player player = new Player(0);
        final Card card = deck.deal();
        player.collect(card);

        assertEquals(1, player.getHand().size());

        Card playerCard = player.play();

        assertEquals(card, playerCard);
        assertEquals(0, player.getHand().size());
        assertNull(player.play());
    }
}