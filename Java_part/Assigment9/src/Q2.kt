import java.util.*

/*2）用分治法来求一串数中的逆序数的个数。逆序数：在规定的正序排列后，与正序排列相反的排列。
逆序对：数列a[1],a[2],a[3]…中的任意两个数a[i],a[j]，如果i<j,a[i]>a[j],那么我们就说这两个数构成了一个逆序对。

逆序数：一个数列中逆序对的总数。*/
fun main(args: Array<String>) {
  Scanner(System.`in`).use { s ->
    println("输入数组数字，以非数字结束")
    val list = ArrayList<Long>()
    while (s.hasNextLong())
      list.add(s.nextLong())
    println(list.getReversionNumber())
  }
}

fun <T : Comparable<T>> MutableList<T>.getReversionNumber(): Long {
  val n = this.size
  if (n < 2)
    return 0
  val m = (n + 1) / 2
  val left = this.subList(0, m).toMutableList() // 保护性拷贝
  val right = this.subList(m, n).toMutableList()
  return left.getReversionNumber() + right.getReversionNumber() + merge(this, left, right)
}

fun <T : Comparable<T>> merge(target: MutableList<T>, left: MutableList<T>, right: MutableList<T>): Long {
  var i = 0
  var j = 0
  var count: Long = 0
  val leftSize = left.size
  val rightSize = right.size
  while (i < leftSize || j < rightSize) { // 直接修改target数组
    when {
      i == leftSize -> {
        target[i + j] = right[j]
        j++
      }
      j == rightSize -> {
        target[i + j] = left[i]
        i++
      }
      left[i] <= right[j] -> {
        target[i + j] = left[i]
        i++
      }
      else -> { // left > right
        target[i + j] = right[j]
        j++
        count += leftSize - i
      }
    }
  }
  return count
}
