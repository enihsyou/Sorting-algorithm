import kotlin.system.measureTimeMillis


fun permute(listToPermute: List<Int>): List<IntArray> {
  val targetN = listToPermute.size
  val marked = BooleanArray(targetN)
  val result = mutableListOf<IntArray>()
  val workingArray: Array<Int> = Array(targetN, { -1 })

  /**
   * 检查已经生成的部分排列数 是否能满足八皇后的要求，速度比下面的方法快了1.5x
   * @param putting 接下来要放的皇后在第几排
   * */
  fun canPlace(putting: Int): Boolean {
    val place = workingArray.count { it != -1 }
    for ((index, value) in workingArray.withIndex()) {/*value是前面部分排列中的皇后在第几排的信息*/
      if (index > place) break
      /*如果两个皇后在同一排或者同一条对角线*/
      if (value == putting || Math.abs(value - putting) == Math.abs(index - place))
        return false
    }
    return true
  }

  /**
   * 深度优先方式生成排列数
   * @param currentLevel &#24403;&#21069;&#22788;&#29702;&#30340;&#25968;&#23383;
   * */
  fun dfs(currentLevel: Int) {
    if (currentLevel == targetN) {
      result.add(workingArray.toIntArray())
      return
    }
    for (i in 0 until targetN) {
      if (!marked[i]) {
        if (!canPlace(listToPermute[i])) {
          continue
        }
        workingArray.set(currentLevel, listToPermute[i])
        marked[i] = true
        dfs(currentLevel + 1)
        workingArray.set(currentLevel, -1)
        marked[i] = false
      }
    }
  }

  dfs(0)
  /*第一种使用排序数的方法*/
//fun <T> permute2(input: List<T>): List<List<T>> {
//  if (input.size == 1) return listOf(input)
//  val perms = mutableListOf<List<T>>()
//  val toInsert = input[0]
//  for (perm in permute2(input.drop(1))) {
//    for (i in 0..perm.size) {
//      val newPerm = perm.toMutableList()
//      newPerm.add(i, toInsert)
//      perms.add(newPerm)
//    }
//  }
//  return perms
//}
//  var result: MutableList<List<Int>> = mutableListOf()
//  l1@ for (perm in perms) {
//    for ((index, queen) in perm.withIndex()) {
//      for (i in 0 until index)
//        if (i != index &&
//            Math.abs(perm[i] - queen) == Math.abs(index - i)) continue@l1
//
//    }
//    result.add(perm)
//  }
  return result
}

fun Queen(N: Int) {
  val input = IntArray(N, { it }).toList()
  val result = permute(input)

  fun print(list: IntArray) {
    println(list.toList())
    list.forEach { value ->
      (0 until list.size).forEach { i ->
        when (i) {
          value -> print('Q')
          else -> print('.')
        }
      }
      print('\n')
    }
    println()
  }
  println("There are ${result.size} possible solutions, namely:\n")
  for (perm in result) print(perm)
}

fun main(args: Array<String>) {
  val executionTime = measureTimeMillis { Queen(8) }
  println("Execution Time = $executionTime ms")
}
