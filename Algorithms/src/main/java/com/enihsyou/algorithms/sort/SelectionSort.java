package com.enihsyou.algorithms.sort;

import org.jetbrains.annotations.NotNull;

public class SelectionSort implements Sorter {
    @SuppressWarnings("Duplicates")
    @Override
    public <T> Comparable<T>[] sort(@NotNull final Comparable<T>[] list) {
        for (int i = 0; i < list.length; i++) {
            int min_index = i;
            for (int j = i + 1; j < list.length; j++) {
                if (Sorter.less(list, j, min_index)) { min_index = j; }
            }
            Sorter.swap(list, i, min_index);
        }
        return list;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public double[] sort(@NotNull final double[] list) {
        for (int i = 0; i < list.length; i++) {
            int min_index = i;
            for (int j = i + 1; j < list.length; j++) {
                if (Sorter.less(list, j, min_index)) { min_index = j; }
            }
            Sorter.swap(list, i, min_index);
        }
        return list;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public int[] sort(@NotNull final int[] list) {
        for (int i = 0; i < list.length; i++) {
            int min_index = i;
            for (int j = i + 1; j < list.length; j++) {
                if (Sorter.less(list, j, min_index)) { min_index = j; }
            }
            Sorter.swap(list, i, min_index);
        }
        return list;
    }

    public static void main(String[] args) {
        SelectionSort sorter = new SelectionSort();
        final int size = args.length == 1 ? Integer.parseInt(args[0]) : 40;
        System.out.format("%d integers%n", size);
        Sorter.printResult(sorter.sort(Sorter.generateRandomIntegers(size)));
        System.out.format("%d doubles%n", size);
        Sorter.printResult(sorter.sort(Sorter.generateRandomDoubles(size)));
    }
}
