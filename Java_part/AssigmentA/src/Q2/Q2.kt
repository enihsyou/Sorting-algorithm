package Q2

import java.nio.file.Files
import java.nio.file.Paths

val ANSI_RESET = "\u001B[0m"
val ANSI_BLACK = "\u001B[30m"
val ANSI_RED = "\u001B[31m"
val ANSI_GREEN = "\u001B[32m"
val ANSI_YELLOW = "\u001B[33m"
val ANSI_BLUE = "\u001B[34m"
val ANSI_PURPLE = "\u001B[35m"
val ANSI_CYAN = "\u001B[36m"
val ANSI_WHITE = "\u001B[37m"

fun main(args: Array<String>) {
  var file_name = args.getOrElse(0) { "matrix.txt" }
  while (Files.notExists(Paths.get(file_name))) {
    file_name = {
      println("输入文件绝对路径")
      println("注意Windows和Unix路径分隔符，推荐使用正斜杠/")
      println("文件内有多行")
      println("每行有空格分开的两个正整数")
      println("分别代表矩阵的行维度和列维度")
      readLine()!!
//      "Y:/matrix.txt"
    }()
  }

  val matrices = mutableListOf<Pair<Int, Int>>()
  var last: Int? = null
  try {
    Paths.get(file_name).toFile().forEachLine {

      val (a, b) = it.split("\\s+".toRegex()).map { it.toInt() }

      if (last?.equals(a)?.not() ?: (false))
        throw ArithmeticException("输入: $it, 相邻矩阵维度不符合乘法规则, 上一个矩阵的列数为$last")
      last = b
      matrices += Pair(a, b)
    }
  } catch (e: Exception) {
    when (e) {
      is ArithmeticException -> throw e
      is NumberFormatException -> throw IllegalArgumentException("${e.localizedMessage}, 解析错误, 包含非数字")
      else -> throw IllegalArgumentException("解析错误, 输入不符合规则")
    }
  }

  println("matrixs = ${matrices}")

  /*乘法次数纪录*/
  val cost_array = Array(matrices.size) { IntArray(matrices.size) }
  /*切分点，(i, j)位置上值k的意思是 切分成[i:k], [k+1:j]两部分相乘*/
  val split_array = Array(matrices.size) { IntArray(matrices.size) }
  /*矩阵维度大小，第i个矩阵的行列数在<i,i+1>的位置上*/
  val dimension = IntArray(matrices.size + 1) { matrices.getOrElse(it, { Pair(matrices.last().second, 0) }).first }

  fun LookUpChain(i: Int, j: Int): Int {
    //乘法元素只有一个矩阵时的情况
    if (i == j) return 0

    //如果已经计算过了
    if (cost_array[i][j] > 0) return cost_array[i][j]

    val tmp_lint = (i until j).map { k ->
      //矩阵连乘A[i:j]分割成A[i:k]和A[k+1:j]两个矩阵的全括号方式
      LookUpChain(i, k) + LookUpChain(k + 1, j) + dimension[i] * dimension[k + 1] * dimension[j + 1]
    }

    val min = tmp_lint.min()!!
    split_array[i][j] = tmp_lint.indexOf(min) + i
    cost_array[i][j] = min
    return min
  }

  println("最小需要：" + LookUpChain(0, matrices.size - 1)) // 右上角的元素

  fun make_matrix_colorful(s: String, color: String): String =
      color + s + ANSI_RESET

  fun trace_back(i: Int, j: Int): String {
    if (i == j)
      return make_matrix_colorful(arrayOf(i, i + 1).joinToString("×", "[", "]") { dimension[it].toString() }, ANSI_BLUE)

    val split_point = split_array[i][j] // 连乘分割点
    val left = trace_back(i, split_point) // [i: k]
    val right = trace_back(split_point + 1, j) // [k+1: j]

    return "(%s * %s)".format(left, right)

  }

  println(trace_back(0, matrices.size - 1))
}



