package com.cynoclast.war;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Trampas Kirk
 */
public class PlayerTest {

    @Test
    public void testCollect() throws Exception {

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
    public void testPlay() throws Exception {
        Deck deck = new ArbitraryDeck();
        deck.create(1, 1);

        Player player = new Player(0);
        final Card card = deck.deal();
        player.collect(card);

        assertEquals(1, player.getHand().size());

        Card playerCard = player.play();

        assertEquals(card, playerCard);
    }
}