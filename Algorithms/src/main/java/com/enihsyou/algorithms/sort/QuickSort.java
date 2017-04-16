package com.enihsyou.algorithms.sort;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class QuickSort implements Sorter {
    @Override
    public <T> Comparable<T>[] sort(@NotNull final Comparable<T>[] list) {
        if (list.length < 2) { return list; }
        /*2 * Ceiling(Log2(list.length))*/
        int[] stack = new int[2 * (int) Math.ceil(Math.log(list.length) / Math.log(2))];
        int stackTop = -1;
        System.out.println(Arrays.toString(list));
        stack[++stackTop] = 0;
        stack[++stackTop] = list.length - 1;
        while (stackTop > 0) {
            int hi = stack[stackTop--];
            int lo = stack[stackTop--];
            if (hi <= lo) { continue; }
            /*[lo, lessThan) [lessThan, moreThan) [moreThan, hi)*/
            int lessThan = lo, moreThan = hi, i = lo;
            while (i <= moreThan) { // 等于是为了做最后一次交换
                if (Sorter.less(list, i, lessThan)) { Sorter.swap(list, i++, lessThan++); }
                else if (list[lessThan] == list[i]) { i++; }
                else { Sorter.swap(list, moreThan--, i); }
            }
            // System.out.format("%s lt:%d i:%d gt:%d%n", Arrays.toString(list), lessThan, i, moreThan);
            if (lo < lessThan - 1) {
                stack[++stackTop] = lo;
                stack[++stackTop] = lessThan - 1;
            }
            if (moreThan + 1 < hi) {
                stack[++stackTop] = moreThan + 1;
                stack[++stackTop] = hi;
            }
        }
        return list;
    }

    @Override
    public double[] sort(@NotNull final double[] list) {
        if (list.length < 2) { return list; }
        /*2 * Ceiling(Log2(list.length))*/
        int[] stack = new int[2 * (int) Math.ceil(Math.log(list.length) / Math.log(2))];
        int stackTop = -1;
        System.out.println(Arrays.toString(list));
        stack[++stackTop] = 0;
        stack[++stackTop] = list.length - 1;
        while (stackTop > 0) {
            int hi = stack[stackTop--];
            int lo = stack[stackTop--];
            if (hi <= lo) { continue; }
            /*[lo, lessThan) [lessThan, moreThan) [moreThan, hi)*/
            int lessThan = lo, moreThan = hi, i = lo;
            while (i <= moreThan) { // 等于是为了做最后一次交换
                if (Sorter.less(list, i, lessThan)) { Sorter.swap(list, i++, lessThan++); }
                // else if (list[lessThan] == list[i]) { i++; }  浮点数不进行等于的判断
                else { Sorter.swap(list, moreThan--, i); }
            }
            // System.out.format("%s lt:%d i:%d gt:%d%n", Arrays.toString(list), lessThan, i, moreThan);
            if (lo < lessThan - 1) {
                stack[++stackTop] = lo;
                stack[++stackTop] = lessThan - 1;
            }
            if (moreThan + 1 < hi) {
                stack[++stackTop] = moreThan + 1;
                stack[++stackTop] = hi;
            }
        }
        return list;
    }

    @Override
    public int[] sort(@NotNull final int[] list) {
        if (list.length < 2) { return list; }
        /*2 * Ceiling(Log2(list.length))*/
        int[] stack = new int[2 * (int) Math.ceil(Math.log(list.length) / Math.log(2))];
        int stackTop = -1;
        System.out.println(Arrays.toString(list));
        stack[++stackTop] = 0;
        stack[++stackTop] = list.length - 1;
        while (stackTop > 0) {
            int hi = stack[stackTop--];
            int lo = stack[stackTop--];
            if (hi <= lo) { continue; }
            /*[lo, lessThan) [lessThan, moreThan) [moreThan, hi)*/
            int lessThan = lo, moreThan = hi, i = lo;
            while (i <= moreThan) { // 等于是为了做最后一次交换
                if (Sorter.less(list, i, lessThan)) { Sorter.swap(list, i++, lessThan++); }
                else if (list[lessThan] == list[i]) { i++; }
                else { Sorter.swap(list, moreThan--, i); }
            }
            // System.out.format("%s lt:%d i:%d gt:%d%n", Arrays.toString(list), lessThan, i, moreThan);
            if (lo < lessThan - 1) {
                stack[++stackTop] = lo;
                stack[++stackTop] = lessThan - 1;
            }
            if (moreThan + 1 < hi) {
                stack[++stackTop] = moreThan + 1;
                stack[++stackTop] = hi;
            }
        }
        return list;
    }

    public static void main(String[] args) {
        QuickSort sorter = new QuickSort();
        final int size = args.length == 1 ? Integer.parseInt(args[0]) : 20;
        System.out.format("%d integers%n", size);
        Sorter.printResult(sorter.sort(Sorter.generateRandomIntegers(size)));
        System.out.format("%d doubles%n", size);
        Sorter.printResult(sorter.sort(Sorter.generateRandomDoubles(size)));
    }
}
