package com.enihsyou.algorithms.sort;

import org.jetbrains.annotations.NotNull;

import static com.enihsyou.algorithms.sort.Sorter.less;
import static com.enihsyou.algorithms.sort.Sorter.swap;

public class InsertionSort implements Sorter {
    @Override
    public <T> Comparable<T>[] sort(@NotNull final Comparable<T>[] list) {
        for (int i = 0; i < list.length; i++) {
            for (int j = list.length - 1; j > i; j--) {
                if (less(list, j, i)) { swap(list, j, i); }
            }
        }
        return list;
    }

    @Override
    public double[] sort(@NotNull final double[] list) {
        for (int i = 0; i < list.length; i++) {
            for (int j = list.length - 1; j > i; j--) {
                if (less(list, j, i)) { swap(list, j, i); }
            }
        }
        return list;
    }

    @Override
    public int[] sort(@NotNull final int[] list) {
        for (int i = 0; i < list.length; i++) {
            for (int j = list.length - 1; j > i; j--) {
                if (less(list, j, i)) { swap(list, j, i); }
            }
        }
        return list;
    }

    public static void main(String[] args) {
        InsertionSort sorter = new InsertionSort();
        final int size = args.length == 1 ? Integer.parseInt(args[0]) : 40;
        System.out.format("%d integers%n", size);
        Sorter.printResult(sorter.sort(Sorter.generateRandomIntegers(size)));
        System.out.format("%d doubles%n", size);
        Sorter.printResult(sorter.sort(Sorter.generateRandomDoubles(size)));
    }
}


