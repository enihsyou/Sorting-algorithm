fun <T> permute(input: List<T>): List<List<T>> {
  if (input.size == 1) return listOf(input)
  val perms = mutableListOf<List<T>>()
  val toInsert = input[0]
  for (perm in permute(input.drop(1))) {
    for (i in 0..perm.size) {
      val newPerm = perm.toMutableList()
      newPerm.add(i, toInsert)
      perms.add(newPerm)
    }
  }
  return perms
}

fun main(args: Array<String>) {
  val input = IntArray(8, { it }).toList()
  val perms = permute(input)
  var result: MutableList<List<Int>> = mutableListOf()
  l1@ for (perm in perms) {
    for ((index, queue) in perm.withIndex()) {
      for (i in 0 until 8) {
        if (i == index) continue
        if (Math.abs(perm[i] - queue) == Math.abs(index - i)) continue@l1
      }
    }
    result.add(perm)
  }
  println("There are ${result.size} permutations, namely:\n")
  for (perm in result) println(perm)
}
