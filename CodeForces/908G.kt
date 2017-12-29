import java.util.*

fun main(args: Array<String>) {
    fun S(i: Int): Int
        = i.toString().toList().sorted().joinToString(separator = "").toInt()
//
//    for (e in 0..100 step 10) {
    val nL = mutableListOf<Int>()
//        for (i in e until 10 + e) {
//            val su = S(i)
//            nL += su
//            println("$i -> $su, sum: ${nL.sum()}")
//        }
//        println()
//    }

    val s = Scanner(System.`in`)
    var su = 0
    with(s) {
        val lim = s.nextInt()
        for (i in 1..lim) {
            val su = S(i)

//            println("$i -> $su, sum: ${nL.sum()}")
        }
    }
    println(nL.sum())
}
