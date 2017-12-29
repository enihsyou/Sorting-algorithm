import java.util.*

fun main(args: Array<String>) {
    val vowels = listOf('a', 'e', 'i', 'o', 'u')
    val digits = "1234567890".toList()
    val s = Scanner(System.`in`)
    with(s) {
        var total = 0
        nextLine()
            .forEach {
                when (it) {
                    in vowels -> {
                        total++
                    }

                    in digits -> {
                        if (it.toInt() % 2 == 1) {
                            total++
                        }
                    }
                }
            }
        println(total)
    }
}
