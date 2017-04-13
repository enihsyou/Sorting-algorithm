package com.enihsyou.algorithms.sort;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;

/**
 * 归并排序，迭代方式，需要2**floor(log2(listSize))大小的额外空间
 */
public class MergeSort implements Sorter {
    @Override
    public <T> Comparable<T>[] sort(@NotNull final Comparable<T>[] list) {
        if (list.length < 2) { return list; }
        int auxSize = 1;
        while (auxSize < list.length) { auxSize *= 2; }
        @SuppressWarnings("unchecked") Comparable<T>[] auxiliary =
            (Comparable<T>[]) Array.newInstance(list.getClass(), auxSize /= 2);
        for (int interval = 1; interval < list.length; interval *= 2) {
            for (int lower = 0; lower < list.length - interval; lower += 2 * interval) {
                int middle = lower + interval;
                int upper = Math.min(middle + interval - 1, list.length - 1);
                System.arraycopy(list, lower, auxiliary, 0, interval);
                int i = middle, j = 0, k = lower;
                while (k <= upper) {
                    if (i > upper) {list[k++] = auxiliary[j++];}
                    else if (j >= interval) {k++;}
                    else if (Sorter.less(list, auxiliary, i, j)) {
                        list[k++] = list[i++];
                    }
                    else { list[k++] = auxiliary[j++];}
                }
            }
        }
        return list;
    }

    @Override
    public double[] sort(@NotNull final double[] list) {
        if (list.length < 2) { return list; }
        int auxSize = 1;
        while (auxSize < list.length) { auxSize *= 2; }
        double[] auxiliary = new double[auxSize /= 2];
        for (int interval = 1; interval < list.length; interval *= 2) {
            for (int lower = 0; lower < list.length - interval; lower += 2 * interval) {
                int middle = lower + interval;
                int upper = Math.min(middle + interval - 1, list.length - 1);
                System.arraycopy(list, lower, auxiliary, 0, interval);
                int i = middle, j = 0, k = lower;
                while (k <= upper) {
                    if (i > upper) {list[k++] = auxiliary[j++];}
                    else if (j >= interval) {k++;}
                    else if (Sorter.less(list, auxiliary, i, j)) {
                        list[k++] = list[i++];
                    }
                    else { list[k++] = auxiliary[j++];}
                }
            }
        }
        return list;
    }

    @Override
    public int[] sort(@NotNull final int[] list) {
        if (list.length < 2) { return list; }
        int auxSize = 1;
        while (auxSize < list.length) { auxSize *= 2; }
        int[] auxiliary = new int[auxSize /= 2];
        for (int interval = 1; interval < list.length; interval *= 2) { // 分组元素个数的一半
            for (int lower = 0; lower < list.length - interval; lower += 2 * interval) {// 分组下界
                int middle = lower + interval; // 中间，左半部分：[lower, middle)，右半部分：[middle + 1, upper)
                int upper = Math.min(middle + interval - 1, list.length - 1);// 分组上界
                System.arraycopy(list, lower, auxiliary, 0, interval); // 复制左半部分到临时数组，不用考虑interval过大的问题
                int i = middle, j = 0, k = lower; // 三个指针，i指示数据数组中后半部分的位置，j指示临时数组中的位置, k指示复制位置
                while (k <= upper) {
                    if (i > upper) {list[k++] = auxiliary[j++];} // 原数组右半部分都复制过去了，把临时数组中剩余元素复制回来
                    else if (j >= interval) {k++; /*list[k++] = auxiliary[i++];*/} // 临时数组（左半部分）中的处理完了，原数组剩余部分应该是有序的
                    else if (Sorter.less(list, auxiliary, i, j)) {
                        list[k++] = list[i++];
                    } // 如果当前原数组元素小于后半部分中的元素，表示不用移动数据
                    else { list[k++] = auxiliary[j++];} // 原数组元素不小于后半部分中的元素，进行交换，把原数组换到临时数组中，
                }
            }
        }
        return list;
    }

    public static void main(String[] args) {
        MergeSort sorter = new MergeSort();
        final int size = args.length == 1 ? Integer.parseInt(args[0]) : 20;
        System.out.format("%d integers%n", size);
        Sorter.printResult(sorter.sort(Sorter.generateRandomIntegers(size)));
        System.out.format("%d doubles%n", size);
        Sorter.printResult(sorter.sort(Sorter.generateRandomDoubles(size)));
    }
}
