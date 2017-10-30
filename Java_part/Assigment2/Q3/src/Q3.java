import java.util.Arrays;
import java.util.Scanner;

/**
 * 3) 小易拥有一个拥有魔力的手环上面有n个数字(构成一个环),当这个魔力手环每次使用魔力的时候就会发生一种奇特的变化：
 * 每个数字会变成自己跟后面一个数字的和(最后一个数字的后面一个数字是第一个),
 * 一旦某个位置的数字大于等于100就马上对100取模(比如某个位置变为103,就会自动变为3).
 * 现在给出这个魔力手环的构成，请你计算出使用k次魔力之后魔力手环的状态。
 *
 * 输入描述:
 * 输入数据包括两行： 第一行为两个整数n(2 ≤ n ≤ 50)和k(1 ≤ k ≤ 200),以空格分隔
 * 第二行为魔力手环初始的n个数，以空格分隔。范围都在0至99.
 *
 * 输出描述:
 * 输出魔力手环使用k次之后的状态，以空格分隔，行末无空格。
 */
public class Q3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        final int n, k, N[];
        /*Input*/
        n = scanner.nextInt();
        k = scanner.nextInt();
        N = new int[n];
        for (int i = 0; i < n; i++) {
            N[i] = scanner.nextInt();
        }
        /*Process*/
        for (int i = 0; i < k; i++) {
            int tmp = N[0]; // 记录循环数组会被覆盖掉的第一个元素
            for (int j = 0; j < n - 1; j++) {
                N[j] += N[j + 1];
            }
            N[n - 1] += tmp;

            System.out.println(Arrays.toString(N));
        }
        /*Output*/
        for (int i = 0; i < n-1; i++) {
            System.out.print(N[i]);
            System.out.print(' ');
        }
        System.out.println(N[n-1]); // 有个换行符
    }
}
