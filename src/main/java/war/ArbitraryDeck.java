package war;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Called ArbitraryDeck because it's not strictly limited to 4 suits or 13 ranks.
 *
 * @author Trampas Kirk
 */
public class ArbitraryDeck implements Deck {

    private List<Card> cards = new ArrayList<>();

    public List<Card> getCards() {
        return cards;
    }

    @Override
    public void create(int numberOfSuits, int numberOfRanks) {
        for (int i = 0; i < numberOfSuits; i++) {
            for (int j = 0; j < numberOfRanks; j++) {
                cards.add(new Card(i, j));
            }
        }
    }

    @Override
    public void shuffle() {
        Collections.shuffle(cards);
    }

    @Override
    public Card deal() {
        if (cards.size() == 0) {
            return null;
        }
        Card topCard = cards.get(0);
        cards.remove(0);
        return topCard;
    }
}
