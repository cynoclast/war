package com.cynoclast.war;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Trampas Kirk
 */
public class WarTest {

    @Test
    public void testFindWinner() {
        War war = new War();

        Player player = new Player(0);

        List<Player> players = new ArrayList<>();
        players.add(player);

        Card card = new Card(0, 0);
        player.collect(card);
        player.collect(card);

        war.setPlayers(players);
        Player winner = war.findWinner(2);
        assertEquals(player, winner);

        winner = war.findWinner(4);
        assertNull(winner);
    }

    @Test
    public void testFindWinnerOfRound_null() {
        War war = new War();

        final Player expectedWinner = war.findWinnerOfRound(null);

        assertNull(expectedWinner);
    }

    @Test
    public void testFindWinnerOfRound_happyPath() {
        War war = new War();

        Card card = new Card(0, 0);
        Player player = new Player(0);

        Card card1 = new Card(0, 1);
        Player player1 = new Player(1);

        Map<Card, Player> cardsInPlay = new HashMap<>();

        cardsInPlay.put(card, player);
        cardsInPlay.put(card1, player1);

        final Player expectedWinner = war.findWinnerOfRound(cardsInPlay);

        assertEquals(player1, expectedWinner);
    }

    @Test
    public void testFindWinnerOfRound_war() {
        War war = new War();

        Card card = new Card(0, 0);
        Player player = new Player(0);

        Card card1 = new Card(1, 0);
        Player player1 = new Player(1);

        Map<Card, Player> cardsInPlay = new HashMap<>();

        cardsInPlay.put(card, player);
        cardsInPlay.put(card1, player1);

        final Player expectedWinner = war.findWinnerOfRound(cardsInPlay);

        assertNull(expectedWinner);
    }

    @Test
    public void testFindWinnerOfRound_Only1() {
        War war = new War();

        Card card = new Card(0, 0);
        Player player = new Player(0);

        Map<Card, Player> cardsInPlay = new HashMap<>();

        cardsInPlay.put(card, player);

        final Player expectedWinner = war.findWinnerOfRound(cardsInPlay);

        assertEquals(player, expectedWinner);
    }
}