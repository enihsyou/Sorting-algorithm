import java.util.*

fun main(args: Array<String>) {
    val s = Scanner(System.`in`)
    with(s) {
        val n = nextInt()
        val r = nextDouble()
        val xs = mutableListOf<Double>()
        val ys = mutableListOf<Double>()
        for (e in 1..n) {
            xs.add(nextDouble())
        }
        val balls = mutableListOf<Pair<Double, Double>>()
        fun new_y(x1: Double, y1: Double, x2: Double): Double {
            return y1 + Math.sqrt(4* r*r - (x1 - x2) * (x1 - x2))
        }
        for (ball in xs) {
            var test_place = Pair(ball, r)
            for (old_ball in balls) {
                if (old_ball.first - ball in -2*r..2*r) {
                    val new_y = new_y(old_ball.first, old_ball.second, ball)
                    if (new_y - old_ball.second in -2*r..2*r)
                        test_place = Pair(ball, new_y)
                }
            }
            if (balls.notC(test_place, r))
                balls += test_place
        }
        println(balls.map { it.second }.joinToString(separator = " "))
    }
}

private fun MutableList<Pair<Double,Double>>.notC(test_place: Pair<Double,Double>, r:Double): Boolean {
    for (ball in this) {
        if (Math.hypot(ball.first-test_place.first, ball.second -test_place.second) < 2 * r - 10e-6)
            return false
    }
    return true
}
