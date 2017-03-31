package com.enihsyou.algorithms.sort;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Random;

public interface Sorter {
    <T> Comparable<T>[] sort(@NotNull final Comparable<T>[] list);

    double[] sort(@NotNull final double[] list);

    int[] sort(@NotNull final int[] list);

    static <T> boolean less(@NotNull Comparable<T>[] list, int i, int j) {
        //noinspection unchecked
        return list[i].compareTo((T) list[j]) < 0;
    }

    static boolean less(@NotNull int[] list, int i, int j) {
        return list[i] < list[j];
    }

    static boolean less(@NotNull double[] list, int i, int j) {
        return list[i] < list[j];
    }

    static <T> void swap(@NotNull Comparable<T>[] list, int i, int j) {
        final Comparable<T> temp = list[i];
        list[i] = list[j];
        list[j] = temp;
    }

    static void swap(@NotNull int[] list, int i, int j) {
        final int temp = list[i];
        list[i] = list[j];
        list[j] = temp;
    }

    static void swap(@NotNull double[] list, int i, int j) {
        final double temp = list[i];
        list[i] = list[j];
        list[j] = temp;
    }

    static int[] generateRandomIntegers(int size) {
        int[] randoms = new int[size];
        Random random = new Random();
        Arrays.setAll(randoms, i -> random.nextInt(size));
        return randoms;
    }

    static double[] generateRandomDoubles(int size) {
        double[] randoms = new double[size];
        Random random = new Random();
        Arrays.setAll(randoms, i -> random.nextDouble() * size);
        return randoms;
    }

    static void printResult(@NotNull int[] list) {
        System.out.println(Arrays.toString(list));
    }

    static void printResult(@NotNull double[] list) {
        System.out.println(Arrays.toString(list));
    }

    static <T> void printResult(@NotNull Comparable<T>[] list) {
        System.out.println(Arrays.toString(list));
    }

    static boolean isSorted(int[] list) {
        for (int i = 1; i < list.length; i++) { if (less(list, i, i - 1)) { return false; } }
        return true;
    }

    static boolean isSorted(double[] list) {
        for (int i = 1; i < list.length; i++) { if (less(list, i, i - 1)) { return false; } }
        return true;
    }

    static <T> boolean isSorted(Comparable<T>[] list) {
        for (int i = 1; i < list.length; i++) { if (less(list, i, i - 1)) { return false; } }
        return true;
    }
}
