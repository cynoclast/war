package com.cynoclast.war;

import java.util.Comparator;

/**
 * @author Trampas Kirk
 */
public class SortByRankComparator implements Comparator<Card> {

    @Override
    public int compare(Card o1, Card o2) {
        return o1.getRank() - o2.getRank();
    }
}
