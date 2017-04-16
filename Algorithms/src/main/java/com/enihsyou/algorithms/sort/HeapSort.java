package com.enihsyou.algorithms.sort;

import org.jetbrains.annotations.NotNull;

public class HeapSort implements Sorter {

    private static void swin(@NotNull final int[] list, int k) {
        while (k > 1 && Sorter.less(list, k / 2, k)) {
            Sorter.swap(list, k, k / 2);
            k /= 2;
        }
    }

    private static void swin(@NotNull final double[] list, int k) {
        while (k > 1 && Sorter.less(list, k / 2, k)) {
            Sorter.swap(list, k, k / 2);
            k /= 2;
        }
    }

    private static <T> void swin(@NotNull final Comparable<T>[] list, int k) {
        while (k > 1 && Sorter.less(list, k / 2, k)) {
            Sorter.swap(list, k, k / 2);
            k /= 2;
        }
    }

    public static void main(String[] args) {
        HeapSort sorter = new HeapSort();
        final int size = args.length == 1 ? Integer.parseInt(args[0]) : 10;
        System.out.format("%d integers%n", size);
        Sorter.printResult(sorter.sort(Sorter.generateRandomIntegers(size)));
        System.out.format("%d doubles%n", size);
        Sorter.printResult(sorter.sort(Sorter.generateRandomDoubles(size)));
    }

    @Override
    public int[] sort(@NotNull final int[] list) {
        int n = list.length - 1;
        /*从最后一层子树开始，依次调整大小顺序*/
        for (int k = n / 2; k >= 0; k--) {
            sink(list, k, n);
        }
        /*把树根(最大)元素放到最下面，然后重构堆*/
        while (n > 0) {
            Sorter.swap(list, 0, n--);
            sink(list, 0, n);
        }
        return list;
    }

    @Override
    public double[] sort(@NotNull final double[] list) {
        int n = list.length - 1;
        /*从最后一层子树开始，依次调整大小顺序*/
        for (int k = n / 2; k >= 0; k--) {
            sink(list, k, n);
        }
        /*把树根(最大)元素放到最下面，然后重构堆*/
        while (n > 0) {
            Sorter.swap(list, 0, n--);
            sink(list, 0, n);
        }
        return list;
    }

    @Override
    public <T> Comparable<T>[] sort(@NotNull final Comparable<T>[] list) {
        int n = list.length - 1;
        /*从最后一层子树开始，依次调整大小顺序*/
        for (int k = n / 2; k >= 0; k--) {
            sink(list, k, n);
        }
        /*把树根(最大)元素放到最下面，然后重构堆*/
        while (n > 0) {
            Sorter.swap(list, 0, n--);
            sink(list, 0, n);
        }
        return list;
    }

    /**
     * 把最上面的（根）元素沉到树的最下面
     *
     * @param list 列表
     * @param k    指定的根元素序号
     * @param n    子树结尾
     */
    private static <T> void sink(@NotNull final Comparable<T>[] list, int k, int n) {
        while (2 * k <= n) {
            int j = 2 * k; // left leaf
            if (j < n && Sorter.less(list, j, j + 1)) { j++; } // 如果右边更大 选择右边
            /*j在这时已经指向父节点上较大的子节点，如果k不小于这个子节点，则子树有序了*/
            if (!Sorter.less(list, k, j)) { break; }
            /*交换父节点和较大的子节点，并将新的父节点设置为原父节点*/
            Sorter.swap(list, k, j);
            k = j;
        }
    }

    /**
     * 把最上面的（根）元素沉到树的最下面
     *
     * @param list 列表
     * @param k    指定的根元素序号
     * @param n    子树结尾
     */
    private static void sink(@NotNull final int[] list, int k, int n) {
        while (2 * k <= n) {
            int j = 2 * k; // left leaf
            if (j < n && Sorter.less(list, j, j + 1)) { j++; } // 如果右边更大 选择右边
            /*j在这时已经指向父节点上较大的子节点，如果k不小于这个子节点，则子树有序了*/
            if (!Sorter.less(list, k, j)) { break; }
            /*交换父节点和较大的子节点，并将新的父节点设置为原父节点*/
            Sorter.swap(list, k, j);
            k = j;
        }
    }

    /**
     * 把最上面的（根）元素沉到树的最下面
     *
     * @param list 列表
     * @param k    指定的根元素序号
     * @param n    子树结尾
     */
    private static void sink(@NotNull final double[] list, int k, int n) {
        while (2 * k <= n) {
            int j = 2 * k; // left leaf
            if (j < n && Sorter.less(list, j, j + 1)) { j++; } // 如果右边更大 选择右边
            /*j在这时已经指向父节点上较大的子节点，如果k不小于这个子节点，则子树有序了*/
            if (!Sorter.less(list, k, j)) { break; }
            /*交换父节点和较大的子节点，并将新的父节点设置为原父节点*/
            Sorter.swap(list, k, j);
            k = j;
        }
    }
}
