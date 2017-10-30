import java.util.Scanner;

/**
 * 2) 对一个数做若干次变换，直到这个数只剩下一位数字。
 * 变换的规则是：将这个数变成 所有位数上的数字的乘积。比如285经过一次变换后转化成2*8*5=80.
 * 问题是，要做多少次变换，使得这个数变成个位数。
 *
 * 输入描述:
 * 输入一个整数。小于等于2,000,000,000。
 *
 * 输出描述:
 * 输出一个整数，表示变换次数。
 */
public class Q2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        final int input = scanner.nextInt();

        int times = 0, temp = input;
        while (temp / 10 != 0) {
            int t = 1;
            while (temp != 0) {
                t *= temp % 10;
                temp /= 10;
            }
            temp = t;
            times++;
        }

        System.out.println(times);
    }
}
