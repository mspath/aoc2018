package day9

import java.util.*
import kotlin.collections.ArrayDeque
import kotlin.math.absoluteValue

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
    //breakfast(players, points)
    lunch(players, points * 100)
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

// see todd.ginsberg.com/post/advent-of-code/2018/day9/
// since the marbles just shift within a small range this is a great data structure here
fun <T> ArrayDeque<T>.shift(n: Int) =
    when {
        n < 0 -> repeat(n.absoluteValue) {
            addLast(removeFirst())
        }
        else -> repeat(n) {
            addFirst(removeLast())
        }
    }

fun lunch(players: Int, points: Int) {
    val scoreBoard = LongArray(players)
    val marbles = ArrayDeque<Int>().also { it.add(0) }

    (1..points).forEach { marble ->
        when {
            marble % 23 == 0 -> {
                marbles.shift(-7)
                val bounty = marbles.removeFirst().toLong()
                scoreBoard[marble % players] += bounty + marble
                marbles.shift(1)
            }
            else -> {
                marbles.shift(1)
                marbles.addFirst(marble)
            }
        }
    }
    println(scoreBoard.maxOf { it })
}