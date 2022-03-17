package day2

import java.io.File
import kotlin.streams.toList

fun main() {
    val input = File("data/day2/input.txt").readLines()
    val resultBreakfast = breakfast(input)
    println(resultBreakfast) // 6000
    val resultLunch = lunch(input)
    println(resultLunch)
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

fun countDifferences(s1: String, s2: String): Int {
    assert(s1.length == s2.length)
    return s1.zip(s2).count { it.first != it.second }
}

fun collectCommonChars(s1: String, s2: String): String {
    assert(s1.length == s2.length)
    val s = s1.zip(s2).filter { it.first == it.second }.map { it.first }
    return s.joinToString("")
}

fun lunch(input: List<String>): String {
    for (i in 0 until input.size) {
        for (j in i + 1 until input.size) {
            val diff = countDifferences(input[i], input[j])
            if (diff == 1) {
                return collectCommonChars(input[i], input[j])
            }
        }
    }
    return "no match found"
}