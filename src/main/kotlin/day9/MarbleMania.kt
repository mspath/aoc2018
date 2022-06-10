package day9

// 9 players; last marble is worth 25 points: high score is 32
// 10 players; last marble is worth 1618 points: high score is 8317
// 13 players; last marble is worth 7999 points: high score is 146373
// 17 players; last marble is worth 1104 points: high score is 2764
// 21 players; last marble is worth 6111 points: high score is 54718
// 30 players; last marble is worth 5807 points: high score is 37305
// 470 players; last marble is worth 72170 points: high score is 388024

fun main() {

    val players = 470
    val points = 72170
    breakfast(players, points)
}

fun MutableList<Int>.addMarble(marble: Int, current: Int, scoreBoard: MutableList<Int>): Int {
    return if (marble % 23 == 0) {
        val player = marble % scoreBoard.size
        scoreBoard[player] += marble
        val next = (current + this.size - 7) % this.size
        scoreBoard[player] += this[next]
        this.removeAt(next)
        next
    } else {
        val next = if (current + 1 < this.size) current + 2 else (current + 2) % this.size
        this.add(next, marble)
        next
    }
}

fun breakfast(players: Int, points: Int) {
    val marbles: MutableList<Int> = mutableListOf(0, 2, 1, 3)
    val scoreBoard = MutableList(players) { 0 }
    var current = 3
    var marble = 3
    while (marble < points) {
        marble++
        current = marbles.addMarble(marble, current, scoreBoard)
    }
    println(scoreBoard.maxOf { it })
}