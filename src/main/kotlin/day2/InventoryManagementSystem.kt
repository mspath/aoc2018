package day2

import java.io.File
import kotlin.streams.toList

fun main() {
    val input = File("data/day2/input.txt").readLines()
    val resultBreakfast = breakfast(input)
    println(resultBreakfast) // 6000
}

fun containsTwos(s: String): Boolean {
    val map = s.chars().toList().groupingBy { it }.eachCount()
    return map.any { it.value == 2 }
}

fun containsThrees(s: String): Boolean {
    val map = s.chars().toList().groupingBy { it }.eachCount()
    return map.any { it.value == 3 }
}

fun breakfast(input: List<String>): Int {
    val twos = input.count { containsTwos(it) }
    val threes = input.count { containsThrees(it) }
    return twos * threes
}