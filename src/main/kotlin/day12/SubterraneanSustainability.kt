package day12

import java.io.File

fun main() {
    val input = File("data/day12/input.txt").readLines()
    //breakfast(input)
    lunch(input)
}

fun Set<Int>.toState(): String {
    val left = this.minOf { it } - 2
    val right = this.maxOf { it } + 2
    return (left..right).map { if (this.contains(it)) '#' else '.' }.joinToString("")
}

fun Set<Int>.toStateAt(position: Int): String {
    val left = position - 2
    val right = position + 2
    return (left..right).map { if (this.contains(it)) '#' else '.' }.joinToString("")
}

fun Set<Int>.nextGeneration(rules: Set<String>): Set<Int> {
    val left = this.minOf { it } - 2
    val right = this.maxOf { it } + 2
    return (left..right).mapNotNull { if (rules.contains(this.toStateAt(it))) it else null }.toSet()
}

fun breakfast(input: List<String>) {
    val initialState = input.first().substringAfter("initial state: ")
    val pots = initialState.mapIndexedNotNull { index, c ->
        if (c == '#') index else null
    }.toSet()
    val rulesGrowth = input.drop(2).filter { it.contains(" => #") }
        .map { it.substringBefore(" => #") }
        .toSet()

    var next: Set<Int> = pots.map { it }.toSortedSet()
    repeat(20) {
        next = next.nextGeneration(rulesGrowth)
    }
    println(next)
    println(next.toState())
    val result = next.sumOf { it }
    println(result)
}

fun lunch(input: List<String>) {
    val magicStable = 120
    val initialState = input.first().substringAfter("initial state: ")
    val pots = initialState.mapIndexedNotNull { index, c ->
        if (c == '#') index else null
    }.toSet()
    val rulesGrowth = input.drop(2).filter { it.contains(" => #") }
        .map { it.substringBefore(" => #") }
        .toSet()

    var next: Set<Int> = pots.map { it }.toSortedSet()
    repeat(magicStable) {
        next = next.nextGeneration(rulesGrowth)
        println("${it}: ${next.sumOf { it }}: ${next.toState()}")
    }
    // ^ by watching the output here we know that after 120 iterations the form stays the same
    // it just shifts, so we can calculate it 'manually':
    val result = (50000000000L - magicStable) * 22 + next.sumOf { it }
    println(result)
}