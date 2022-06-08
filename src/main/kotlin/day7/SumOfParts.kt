package day7

import java.io.File

fun main() {
    val input = File("data/day7/input.txt").readLines()
    breakfast(input)
}

fun getNext(rules: List<Pair<Char, Char>>): Char {
    val froms = rules.map { it.first }
    val tos = rules.map { it.second }
    return froms.filter { !tos.contains(it) }.sorted().first()
}

fun breakfast(input: List<String>) {

    var rules = input.map {
        Pair(it[5], it[36])
    }
    println(rules)

    while (rules.size > 1) {
        val next = getNext(rules)
        print(next)
        rules = rules.filterNot { it.first == next }
        //println(rules)
    }
    // the last rule
    print(rules.first().first)
    print(rules.first().second)
}