import java.util.*

fun main(args: Array<String>) {
  var cards = 54
  var computerTurn = true
  val s = Scanner(System.`in`)
  var lastTake = 0
  loop@ while (cards > 0) {
    if (!computerTurn) {
      println("Your turn")
      val choose: Int = s.nextInt()
      when (choose) {
        in 1..4 -> {
          if (cards < choose)
            continue@loop
          lastTake = choose
          cards -= choose
        }
        else -> continue@loop
      }
    } else {
      lastTake = (cards % 5 + 4) % 5
      cards -= lastTake
    }
    println("%s, take %s, remain %s".format(if (computerTurn) "Computer" else "Human", lastTake, cards))
    if (cards == 1) {
      println("%s win".format(if (computerTurn) "Computer" else "Human", lastTake))
      break@loop
    }
    if (cards == 0) {
      println("%s win".format(if (!computerTurn) "Computer" else "Human", lastTake))
      break@loop
    }
    computerTurn = !computerTurn
  }

}
