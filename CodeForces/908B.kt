import java.util.*

fun main(args: Array<String>) {
    val s = Scanner(System.`in`)
    with(s) {
        val n = nextInt()
        val m = nextInt()
        val maze = Array(n) { IntArray(m) }
        var start: Pair<Int, Int> = Pair(0, 0)
        var end: Pair<Int, Int> = Pair(0, 0)
        nextLine()
        for (i in 0 until n) {
            val row = nextLine()
            val a = maze[i]
            for ((index, c) in row.withIndex()) {
                when (c) {
                    '.' -> a[index] = 0

                    '#' -> a[index] = 1

                    'S' -> {
                        a[index] = 2
                        start = Pair(i, index)
                    }

                    'E' -> {
                        a[index] = 3
                        end = Pair(i, index)
                    }
                }
            }
        }
        val inst = nextLine()
        val direcs = listOf(
            "0123",
            "0132",
            "0213",
            "0231",
            "0312",
            "0321",
            "1023",
            "1032",
            "1203",
            "1230",
            "1302",
            "1320",
            "2013",
            "2031",
            "2103",
            "2130",
            "2301",
            "2310",
            "3012",
            "3021",
            "3102",
            "3120",
            "3201",
            "3210")
        var count = 0
        loop@ for (direc in direcs) {
            var place = start.copy()
            fun nextP(c: Char)
                = when (direc.indexOf(c)) {
                0    -> {
                    Pair(place.first + 1, place.second)
                }

                1    -> {
                    Pair(place.first - 1, place.second)
                }

                2    -> {
                    Pair(place.first, place.second + 1)
                }

                3    -> {
                    Pair(place.first, place.second - 1)
                }

                else -> place
            }
            for (c in inst) {
                when (c) {
                    '0' -> {
                        place = nextP('0')
                    }

                    '1' -> {
                        place = nextP('1')
                    }

                    '2' -> {
                        place = nextP('2')
                    }

                    '3' -> {
                        place = nextP('3')
                    }
                }
                if (place.first !in 0 until n
                    || place.second !in 0 until m
                    || maze[place.first][place.second] == 1)
                    continue@loop
                if (place == end) {
                    count++
                    continue@loop
                }
            }
        }
        println(count)
    }
}
