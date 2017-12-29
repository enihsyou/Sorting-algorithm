import kotlin.math.max
import kotlin.text.Typography.times

/*
某一印刷厂有6项加工任务，对印刷车间和装订车间所需时间如表所示。完成每项任务都要先去印刷车间印刷，
再去装订车间装订。问怎样安排这6项加工任务的加工工序，使得加工总工时最少。

任务         J1    J2    J3    J4    J5    J6
印刷车间     3     12    5     2     9     11
装订车间     8     10    9     6     3     1
*/

fun main(args: Array<String>) {
    val input = arrayListOf(
        3 to 8,
        12 to 10,
        5 to 9,
        2 to 6,
        9 to 3,
        11 to 1
    )
    val rearrange = mutableListOf<Triple<Int, Int, Int>>()

    input.mapIndexed { index: Int, pair: Pair<Int, Int> ->
        rearrange.add(Triple(index, 0, pair.first))
        rearrange.add(Triple(index, 1, pair.second))
    }

    val a = mutableListOf<Int>()
    val b = mutableListOf<Int>()
    while (a.size + b.size < input.size) {
        rearrange.sortBy { it.third }

        val (job, machine, _) = rearrange.first()
        rearrange.removeIf {
            it.first == job
        }
        if (machine == 0)
            a += job
        else if (machine == 1)
            b += job
    }
    val result = a + b
    println(result.map { "Job ${it + 1}" })
}
