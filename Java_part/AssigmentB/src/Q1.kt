import java.nio.file.Files
import java.nio.file.Paths
import kotlin.math.min

fun <E> List<List<E>>.transpose(): List<List<E>> {
    if (isEmpty()) return this

    val width = first().size
    if (any { it.size != width }) {
        throw IllegalArgumentException("All nested lists must have the same size, but sizes were ${map { it.size }}")
    }

    return (0 until width).map { col ->
        (0 until size).map { row -> this[row][col] }
    }
}

fun main(args: Array<String>) {
    var file_name = args.getOrElse(0) { ".txt" }
    while (Files.notExists(Paths.get(file_name))) {
        file_name = {
            println("输入文件绝对路径")
            println("注意Windows和Unix路径分隔符，推荐使用正斜杠/")
            println("文件内有多行")
            println("第一段是每行第一个数字为进入消耗，第二个数字为离开消耗")
            println("使用空白行分隔各段")
            println("第二段代表流水线上各节点的消耗，各条流水线应该等长")
            println("下面各段，使用空行分隔，代表从转移到其他流水线的消耗")
            println("每一段中第n行的第i个数字代表")
            println("从当前流水线转移到按顺序其他第n条流水线上的第i+1站的消耗")
            println("转移消耗如果是负数，代表禁止转移")
            println("具体可看示例line2.txt，根据图片line.png写的输入示例")
            println("三条流水线的示例请看line3.txt")
            readLine()!!
        }()
    }
    val cost = mutableListOf<List<Int>>()
    val charge = mutableListOf<List<Int>>()

    var stage = 0
    try {
        Paths.get(file_name).toFile().forEachLine {
            if (it.isBlank()) {
                stage++
            } else {
                val numbers = it.split("\\s+".toRegex()).map { it.toInt() }
                when (stage) {
                    0    -> cost += numbers
                    else -> {
                        val mutableList = numbers.toMutableList()
                        mutableList.run {
                            add(0, -1)
                            add(0, 0)
                            add(mutableList.size, -1)
                        }
                        charge += mutableList
                    }
                }
            }
        }
    } catch (e: Exception) {
    }
    when (cost.size) {
        2 -> {
            val transpose = charge.transpose()
            val mapIndexed = cost.transpose().reduceIndexed { index, acc, next ->
                val (a, b) = acc
                val (a_next, b_next) = next
                val (change_1_2, change_2_1) = transpose[index]
                println("acc: $a, $b, next: $a_next, $b_next, price: $change_1_2, $change_2_1")
                val first = a_next + if (change_1_2 < 0) a else min(a, b + change_2_1)
                val second = b_next + if (change_2_1 < 0) b else min(b, a + change_1_2)
                arrayListOf(first, second)
            }
            println("最小需要 ${mapIndexed.min()!!}")
        }
        3 -> {
            val transpose = charge.transpose()
            val mapIndexed = cost.transpose().reduceIndexed { index, acc, next ->
                val (a, b, c) = acc
                val (a_next, b_next, c_next) = next
                val (change_1_2,
                    change_1_3,
                    change_2_1,
                    change_2_3,
                    change_3_1,
                    change_3_2
                    ) = transpose[index]
                println("acc: $acc, next: $next, price: ${transpose[index]}")
                val first = a_next + if (index == 1) a else arrayOf(a, b + change_2_1, c + change_3_1).min()!!
                val second = b_next + if (index == 1) b else arrayOf(b, a + change_1_2, c + change_3_2).min()!!
                val third = c_next + if (index == 1) b else arrayOf(b, a + change_1_3, b + change_2_3).min()!!
                arrayListOf(first, second, third)
            }
            println("最小需要 ${mapIndexed.min()!!}")
        }
    }
}

private operator fun <E> List<E>.component6(): E = this[5]


