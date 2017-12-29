package Q1

import java.util.*
import kotlin.math.max

val ANSI_RESET = "\u001B[0m"
val ANSI_BLACK = "\u001B[30m"
val ANSI_RED = "\u001B[31m"
val ANSI_GREEN = "\u001B[32m"
val ANSI_YELLOW = "\u001B[33m"
val ANSI_BLUE = "\u001B[34m"
val ANSI_PURPLE = "\u001B[35m"
val ANSI_CYAN = "\u001B[36m"
val ANSI_WHITE = "\u001B[37m"
val PADDING = 1

/*生成用随机数填充的数组*/
val ClosedRange<Int>.randomInt get() = Random().nextInt(endInclusive + 1 - start) + start

fun generate(size: Int): List<Int> =
    List(size) { (-size..size).randomInt }


fun printStr(list: List<Int>, path: Int, width: Int) {
  val output_number_format: String = "%%- %dd".format(width)
  if (list.isEmpty()) return
  val s = StringBuilder()
  list.forEachIndexed { index, number ->
    if (index == path)
      s.append(ANSI_BLUE + output_number_format.format(number) + ANSI_RESET)
    else
      s.append(output_number_format.format(number))
  }
  repeat((LEVEL - list.size) * width / 2) { print(' ') }
  println(s.toString())
}

var LEVEL = 0
fun main(args: Array<String>) {
  /*手动输入阶数，不进行异常处理*/
  println("输入阶数：")
  LEVEL = readLine()!!.toInt()
  /*计算数字之间的填充宽度*/
  val WIDTH = LEVEL.toString().length + PADDING
  /*用于保存生成的原始数组*/
  val 原始数组 = mutableListOf<List<Int>>()
  val dp = mutableListOf<List<Int>>()
  (1..LEVEL)
      /*先 生成数塔*/
      .map { generate(it) }
      .onEach { 原始数组 += it }
      /*动态规划从数塔的下到上寻找最大值*/
      .foldRight(List(LEVEL + 1, { 0 }), { 当前行, 上一行 ->
        dp.add(当前行.mapIndexed { index, number ->
          number + max(上一行[index], 上一行[index + 1])
        })
        dp.last()
      })
  dp.foldRightIndexed(0) { index, list, acc ->
    var path = acc
    if (list[acc] < list.getOrElse(acc + 1) { Int.MIN_VALUE })
      path++
    printStr(原始数组[LEVEL - index - 1], path, WIDTH)
    path
  }
  println("最大和："+ dp.last().first())
}
