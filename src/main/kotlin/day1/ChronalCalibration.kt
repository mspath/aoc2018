package day1

import java.io.File

fun main() {
    val input = File("data/day1/input.txt").readLines().map {
        it.toInt()
    }
    val resultBreakfast = breakfast(input)
    println(resultBreakfast) // 479
    val resultLunch = lunch(input)
    println(resultLunch) // 66105
}

fun breakfast(frequencyChanges: List<Int>) = frequencyChanges.sum()

fun List<Int>.loop(): Sequence<Int> = sequence {
    while (true) {
        yieldAll(this@loop)
    }
}

fun lunch(frequencyChanges: List<Int>): Int {
    val loop = frequencyChanges.loop().iterator()
    val frequencies: MutableSet<Int> = mutableSetOf()
    var frequency = 0
    while (true) {
        frequency += loop.next()
        if (frequencies.contains(frequency)) break
        frequencies.add(frequency)
    }
    return frequency
}