package com.enihsyou.algorithms.sort;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public class SortCompare {
    public static void main(String[] args) {
        ArrayList<Sorter> sorters = new ArrayList<>();
        sorters.add(new MergeSort());
        sorters.add(new HeapSort());
        sorters.add(new QuickSort());
        // sorters.add(new InsertionSort());
        // sorters.add(new SelectionSort());
        final int size = args.length == 1 ? Integer.parseInt(args[0]) : 40;
        System.out.format("%d integers%n", size);
        for (Sorter sorter : sorters) {
            Instant start = Instant.now();
            int[] list = Sorter.generateRandomIntegers(size);
            // System.out.println(Arrays.toString(list));
            sorter.sort(list);
            Instant stop = Instant.now();
            System.out.format("%13s: %s%n", sorter.getClass().getSimpleName(), Duration.between(start, stop));
        }
    }
}
