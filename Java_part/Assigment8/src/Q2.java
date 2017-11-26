import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * 2）在O（NlogN）的时间复杂度内找出数据集合S中任意两个数之间的最近距离。例如S={8，3，1，4，6，5}，则最近距离为1。
 */
public class Q2 {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    System.out.println("元素个数：");
    final int n = input.nextInt();
    System.out.println("依次输入各元素");
    int[] arr = IntStream.range(0, n).map(i -> input.nextInt()).toArray();
    // Arrays.parallelSort(arr);
    System.out.println(Arrays.stream(arr).sorted().reduce(Integer.MAX_VALUE, Math::min));
  }
}
