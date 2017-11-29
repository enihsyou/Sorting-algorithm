import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.RepeatedTest
import java.util.*
import java.util.Collections.swap
import kotlin.test.assertEquals

/**
 * 1）分治算法求n个数的数组中找出第二个最大元素
 */
fun <T : Comparable<T>> MutableList<T>.findKthLargest(k: Int): T {
  val n = this.size
  if (k > n || k < 0) throw IllegalArgumentException("没有这种操作")
  return quickSelect(this, 0, n - 1, k - 1)
}

fun <T : Comparable<T>> quickSelect(list: MutableList<T>, _left: Int, _right: Int, k: Int): T {
  var left = _left
  var right = _right
  while (left <= right) {
    if (left == right)
      return list[left]
    var pivotIndex = (left + right) / 2
    pivotIndex = partition(list, left, right, pivotIndex)
    when {
      k == pivotIndex -> return list[k]
      k < pivotIndex -> right = pivotIndex - 1
      k > pivotIndex -> left = pivotIndex + 1
    }
  }
  throw RuntimeException(String.format("%d %d", left, right))
}

fun <T : Comparable<T>> partition(list: MutableList<T>, left: Int, right: Int, pivotIndex: Int): Int {
  val pivot = list[pivotIndex]
  swap(list, pivotIndex, right)
  var index = left
  for (i in left until right) {
    if (list[i] > pivot) { // 下降序排列
      swap(list, index, i)
      index++
    }
  }
  swap(list, right, index)
  return index
}


fun main(args: Array<String>) {
  Scanner(System.`in`).use { s ->
    println("输入数组数字，以非数字结束")
    val list = ArrayList<Long>()
    while (s.hasNextLong())
      list.add(s.nextLong())
    println(list.findKthLargest(2))
  }
}


class Q1KtTest {
  @DisplayName("╯°□°）╯")
  @RepeatedTest(5)
  fun findKthLargest() {
    val random = Random()
    val list = LongArray(1000000, {
      random.nextLong()
    })
    assertEquals(
        list.sortedArrayDescending()[1], list.toMutableList().findKthLargest(2))
  }
}
