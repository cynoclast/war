package com.cynoclast.war;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Trampas Kirk
 */
public class SortByRankComparatorTest {

    @Test
    public void testCompare_asc() {
        SortByRankComparator sortByRankComparator = new SortByRankComparator();
        Card card1 = new Card(0, 0);
        Card card2 = new Card(0, 1);

        final int compare = sortByRankComparator.compare(card1, card2);
        assertEquals(-1, compare);
    }

    @Test
    public void testCompare_eq() {
        SortByRankComparator sortByRankComparator = new SortByRankComparator();
        Card card1 = new Card(0, 0);
        Card card2 = new Card(0, 0);

        final int compare = sortByRankComparator.compare(card1, card2);
        assertEquals(0, compare);
    }

    @Test
    public void testCompare_desc() {
        SortByRankComparator sortByRankComparator = new SortByRankComparator();
        Card card1 = new Card(0, 1);
        Card card2 = new Card(0, 0);

        final int compare = sortByRankComparator.compare(card1, card2);
        assertEquals(+1, compare);
    }
}