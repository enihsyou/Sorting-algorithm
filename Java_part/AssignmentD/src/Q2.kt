import java.util.*

class MazeGenerator(private val x: Int, private val y: Int) {
    private val maze = Array(x) { IntArray(y) }

    fun generate(cx: Int, cy: Int) {
        Direction.values().shuffle().forEach {
            val nx = cx + it.dx
            val ny = cy + it.dy
            if (between(nx, x) && between(ny, y)
                && maze[nx][ny] == 0) { // 未填充
                maze[cx][cy] = maze[cx][cy] or it.bit
                maze[nx][ny] = maze[nx][ny] or it.opposite.bit
                generate(nx, ny)
            }
        }
    }

    fun display() {
        for (i in 0 until y) {
            // 上面的线
            (0 until x)
                .joinToString("+", "+", "+")
                { if (maze[it][i] and 1 == 0) "---" else "   " }.also { println(it) }

            // 左右的线
            (0 until x)
                .joinToString("", postfix = "|")
                { if (maze[it][i] and 8 == 0) "|   " else "    " }.also { println(it) }
        }

        // 画底边框
        println((0..x).joinToString("---") { "+" })
    }

    fun search(startX: Int, startY: Int, goalX: Int, goalY: Int): Boolean {
        fun display2(visited: Queue<Pair<Int, Int>>) {
            fun has(i: Int, j: Int): String = if (Pair(i, j) in visited) "×" else " "
            for (i in 0 until y) {
                // 上面的线
                (0 until x)
                    .joinToString("+", "+", "+")
                    { if (maze[it][i] and 1 == 0) "---" else "   " }.also { println(it) }

                // 左右的线
                (0 until x)
                    .joinToString("", postfix = "|")
                    { if (maze[it][i] and 8 == 0) "| ${has(it, i)} " else "  ${has(it, i)} " }.also { println(it) }
            }

            // 画底边框
            println((0..x).joinToString("---") { "+" })
        }

        val visited: Deque<Pair<Int, Int>> = LinkedList<Pair<Int, Int>>()

        fun search_(startX: Int, startY: Int, goalX: Int, goalY: Int, comeFrom: Direction?): Boolean {
            visited += Pair(startX, startY)
            println()
            println("访问：${Pair(startX, startY)}")
            display2(visited)
            /*抵达目标 退出*/
            if (startX == goalX && startY == goalY) {
                println("抵达目标：${Pair(startX, startY)}")
                return true
            }
            Direction.values()
                /*过滤掉来的方向，防止搜索走回头路*/
                .filterNot { it == comeFrom?.opposite }
                /*过滤出有效的前进方向*/
                .filter { maze[startX][startY] and it.bit == it.bit }
                .forEach {
                    val nx = startX + it.dx
                    val ny = startY + it.dy
                    if (search_(nx, ny, goalX, goalY, it))
                        return true
                    else {
                        visited.pollLast()
                        println()
                        println("退回：${Pair(startX, startY)}")
                        display2(visited)
                    }
                }
            return false
        }
        println("从${Pair(startX, startY)}开始寻找目标${Pair(goalX, goalY)}")
        return search_(startX, startY, goalX, goalY, null)
    }

    inline private fun <reified T> Array<T>.shuffle(): Array<T> {
        val list = toMutableList()
        Collections.shuffle(list)
        return list.toTypedArray()
    }

    private enum class Direction(val bit: Int, val dx: Int, val dy: Int) {
        N(1, 0, -1), S(2, 0, 1), E(4, 1, 0), W(8, -1, 0);

        lateinit var opposite: Direction

        companion object {
            init {
                N.opposite = S
                S.opposite = N
                E.opposite = W
                W.opposite = E
            }
        }
    }

    private fun between(v: Int, upper: Int) = v in 0 until upper
}

fun main(args: Array<String>) {
    var x = 8
    var y = 8
    if (args.size == 2) {
        x = args[0].toInt()
        y = args[1].toInt()
    } else {
        val s = Scanner(System.`in`)
        with(s) {
            println("输入迷宫的长和宽")
            x = nextInt()
            y = nextInt()
        }
    }

    with(MazeGenerator(x, y)) {
        generate(0, 0)
        display()
        search(0, 0, x - 1, y - 1)
    }
}

