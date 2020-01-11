package com.cynoclast.war;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Trampas Kirk
 */
public class War {

    private static final Logger LOGGER = LogManager.getLogger(War.class);

    /**
     * The card pile that would be in the middle of the table.
     */
    private List<Card> pile = new LinkedList<>();

    /**
     * The players.
     */
    private List<Player> players = new LinkedList<>();

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: war.War <number of suits> <number of ranks> <number of players>");
            System.exit(1);
        }
        War war = new War();
//        war.play(1, 1, 1);
        war.play(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
    }

    /**
     * Simulates a 'war' card game.
     * Notes:
     * * If we have an odd number of players the first player will have 1 more card than the other players
     * * If two players both tie for the highest card played, all players enter 'war', even if third or other players would have lost
     * * If a player enters into 'war' but has insufficient cards to put down, they'll be removed from the game (lose)
     *
     * @param numberOfSuits   the number of suits in the deck
     * @param numberOfRanks   the number of ranks in the deck
     * @param numberOfPlayers the number of players in the game
     */
    public void play(int numberOfSuits, int numberOfRanks, int numberOfPlayers) {

        if (numberOfRanks == 1 && numberOfSuits > 1 && numberOfPlayers > 1) {
            System.err.println("\nA deck of only 1 rank will cause infinite war/a draw. (The only winning move is not to play.)");
            System.exit(0);
        }

        int numberOfCards = numberOfSuits * numberOfRanks;

        if (numberOfCards <= 0) {
            System.err.println("\nMust have some cards in the deck.");
            System.exit(1);
        }

        if (numberOfPlayers < 1) {
            System.err.println("\nMust have at least one player.");
            System.exit(1);
        }

        // create players
        for (int i = 0; i < numberOfPlayers; i++) {
            Player player = new Player(i);
            players.add(player);
        }

        // create deck
        Deck deck = new ArbitraryDeck();
        deck.create(numberOfSuits, numberOfRanks);
        deck.shuffle();

        // deal
        Player player;
        for (int i = 0; i < numberOfCards; i++) {
            player = players.get(i % numberOfPlayers);
            player.collect(deck.deal());
        }

        LOGGER.debug("Players: {}", players);

        // if we only have one player, they'll win immediately
        Player winner = findWinner(numberOfCards);

        // play
        Map<Card, Player> cardsInPlay = new HashMap<>();
        while (winner == null) {
            // play a round
            for (Player currentPlayer : players) {
                if (currentPlayer.show() != null) {
                    cardsInPlay.put(currentPlayer.show(), currentPlayer);
                }
            }

            Player winnerOfRound = findWinnerOfRound(cardsInPlay);
            if (winnerOfRound == null) {
                //war
                LOGGER.debug("WAR");
                for (Player currentPlayer : players) {
                    if (currentPlayer.show() != null) {
                        pile.add(currentPlayer.play()); // card that caused 'war'

                        if (currentPlayer.show() != null) {
                            pile.add(currentPlayer.play()); // first card face down
                        }
                    }
                }
            } else {
                // we have a winner
                if (!pile.isEmpty()) {
                    // war previously started was won
                    Collections.shuffle(pile); // attempt to prevent infinite games and simulate people picking up piles of cards
                    winnerOfRound.collect(pile);
                    pile.clear();
                }
                for (Player currentPlayer : players) {
                    if (currentPlayer.show() != null) {
                        pile.add(currentPlayer.play()); // winner takes all cards that were in play
                    }
                }
                Collections.shuffle(pile); // attempt to prevent infinite games and simulate people picking up piles of cards
                winnerOfRound.collect(pile);
                pile.clear();
            }
            cardsInPlay.clear();

            removePlayersWithNoCards();

            winner = findWinner(numberOfCards);

            if (winner == null && pile.size() == numberOfCards) {
                // all the cards in the pile, can't be a winner
                System.out.println("\nDraw!");
                break;
            }

        }

        if (winner != null) {
            declareWinner(winner);
        }
    }

    private void removePlayersWithNoCards() {
        List<Integer> playersWithNoCards = new ArrayList<>();
        for (Player player : players) {
            if (player.show() == null) {
                playersWithNoCards.add(player.getOrdinal());
            }
        }
        for (Integer playerOrdinal : playersWithNoCards) {
            //noinspection SuspiciousMethodCalls
            players.remove(playerOrdinal);
        }
    }

    /**
     * Uses a map of card to player instead of player to card since the winner is determined by card this makes the lookup easy.
     *
     * @param cardsInPlay the cards in play for the current round
     * @return the winner of the round, or null if there's a tie for the highest card by rank
     */
    Player findWinnerOfRound(Map<Card, Player> cardsInPlay) {
        if (cardsInPlay == null) {
            return null;
        }

        LOGGER.debug("Cards in play: {}", cardsInPlay);

        // find highest card
        List<Card> cards = new ArrayList<>(cardsInPlay.keySet());
        cards.sort(new SortByRankComparator().reversed());

        Card highest = cards.get(0);

        if (cardsInPlay.size() == 1) {
            return cardsInPlay.get(highest); // only one card in play, so that player wins
        }

        Card checkCard = cards.get(1); // since these are sorted if there's a tie it will be the next card
        if (checkCard.getRank() == highest.getRank()) {
            return null; // go to war (even if there are other possible ties)
        } else {
            return cardsInPlay.get(highest);
        }
    }

    private void declareWinner(Player winner) {
        System.out.printf("\nPlayer %s wins!\n", winner.getOrdinal());
    }

    /**
     * The simplest way to tell if a player has won is if they have all of the cards.
     * This returns the player with all of the cards if one is found, otherwise null.
     *
     * @return the winner if there is one
     */
    public Player findWinner(int numberOfCardsInDeck) {
        for (Player player : players) {
            if (player.getHand().size() == numberOfCardsInDeck) {
                return player;
            }
        }
        return null;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
