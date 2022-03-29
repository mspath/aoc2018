package day4

import java.io.File

fun main() {
    val input = File("data/day4/input_sorted.txt").readLines()
    breakfast(input) // 50558
}

data class Night(val id: Int, val guard: Int, val minutes: IntArray = IntArray(60) { 0 })

fun parseInput(input: List<String>): List<Night> {
    var currentNight = -1
    var currentGuard = -1
    var minuteStart = -1

    val nights: MutableList<Night> = mutableListOf()

    input.forEach {
        if (it.contains("Guard")) {
            currentNight++
            val guard = it.substringAfter("#").substringBefore(" begins").toInt()
            currentGuard = guard
            minuteStart = -1
            nights.add(currentNight, Night(currentNight, currentGuard))
        } else if (it.contains("falls asleep")) {
            minuteStart = it.substringAfter("00:").substringBefore("]").toInt()
        } else if (it.contains("wakes up")) {
            val minuteEnd = it.substringAfter("00:").substringBefore("]").toInt()
            (minuteStart until minuteEnd).forEach { minute ->
                nights[currentNight].minutes[minute] = 1
            }
        }
    }
    return nights
}

fun breakfast(input: List<String>) {

    val nights = parseInput(input)
    val nightsGrouped = nights.groupingBy{it.guard}.fold(0) { acc, element ->
        acc + element.minutes.sum()
    }
    val sleepy = nightsGrouped.maxByOrNull { it.value }?.key
    sleepy?.let { guard ->
        val guardNights = nights.filter { it.guard == guard }.map { it.minutes }
        var max = 0
        var sleeper = 0
        (0 until 60).forEach { index ->
            val sleeps = guardNights.sumOf { it[index] }
            if (sleeps > max) {
                sleeper = index
                max = sleeps
            }
        }
        println("result: ${sleepy * sleeper}")
    }
}

