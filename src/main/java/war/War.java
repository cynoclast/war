package war;

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

    private List<Card> pile = new LinkedList<>();

    private List<Player> players = new LinkedList<>();

    public static void main(String[] args) {
        War war = new War();
        war.play(4, 13, 5);
    }

    /**
     * Notes:
     * * If we have an odd number of players the first player will have 1 more card than the other players
     * * If two players both tie for the highest card played, all players enter 'war', even if third or other players would have lost
     *
     * @param numberOfSuits
     * @param numberOfRanks
     * @param numberOfPlayers
     */
    public void play(int numberOfSuits, int numberOfRanks, int numberOfPlayers) {

        if (numberOfRanks == 1 && numberOfSuits > 1 && numberOfPlayers > 1) {
            System.out.println("\nA deck of only 1 rank will cause infinite war. (The only winning move is not to play.)");
            System.exit(0);
        }

        int numberOfCards = numberOfSuits * numberOfRanks;

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


        // if we only have one player, they'll win
        Player winner = findWinner(numberOfCards);

        // play
        Map<Card, Player> playerCards = new HashMap<>();
        while (winner == null) {
            // play a round
            for (Player currentPlayer : players) {
                if (currentPlayer.show() != null) {
                    playerCards.put(currentPlayer.show(), currentPlayer);
                }
            }

            Player winnerOfRound = findWinnerOfRound(playerCards);
            if (winnerOfRound == null) {
                //war
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
            playerCards.clear();

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

    private Player findWinnerOfRound(Map<Card, Player> playerCards) {
        if (playerCards == null) {
            return null;
        }

        // find highest card
        List<Card> cards = new ArrayList<>(playerCards.keySet());
        cards.sort(new SortByRankComparator().reversed());

        // if there's no tie for rank
        Card highest = cards.get(0);

        if (playerCards.size() == 1) {
            return playerCards.get(highest); // only one card in play, so that player wins
        }

        Card checkCard = cards.get(1); // since these are sorted if there's a tie it will be the next card
        if (checkCard.getRank() == highest.getRank()) {
            return null; // go to war
        } else {
            return playerCards.get(highest);
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
}
