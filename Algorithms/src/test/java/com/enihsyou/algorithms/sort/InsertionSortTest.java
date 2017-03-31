package com.enihsyou.algorithms.sort;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class InsertionSortTest {
    private InsertionSort mSorter;
    private int mSize;

    @Before
    public void setUp() throws Exception {
        mSorter = new InsertionSort();
        mSize = 40;
    }

    @Test
    public void sort() throws Exception {
        final int[] sorted = mSorter.sort(Sorter.generateRandomIntegers(Math.min(Integer.MAX_VALUE / 4, 2 * mSize)));
        Sorter.printResult(sorted);
        assertTrue(Sorter.isSorted(sorted));
    }

    @Test
    public void sort1() throws Exception {
        final double[] sorted = mSorter.sort(Sorter.generateRandomDoubles(Math.min(Integer.MAX_VALUE / 4, 2 * mSize)));
        Sorter.printResult(sorted);
        assertTrue(Sorter.isSorted(sorted));
    }

    @Test
    public void sort2() throws Exception {
    }

}
