package day5

import java.io.File

fun main() {
    val sample = "dabAcCaCBAcCcaDA"
    val input = File("data/day5/input.txt").readText()
    breakfast(sample)
    breakfast(input)
    lunch(sample)
    lunch(input)
}

fun String.isPolymer() = (kotlin.math.abs(this[0] - this[1]) == 32)

fun String.indexOfFirstPolymer() = this.windowed(2, 1).indexOfFirst { it.isPolymer() }

// recursive solution. I actually prefer this, but it can't cope with the input size
fun String.react(): String {
    val index = this.indexOfFirstPolymer()
    return if (index == -1) this
    else (this.substring(0, index) + this.substring(index + 2)).react()
}

fun breakfast(input: String) {
    var rest = input
    while (true) {
        val index = rest.indexOfFirstPolymer()
        if (index < 0) break
        rest = rest.substring(0, index) + rest.substring(index + 2)
    }
    val result = rest.length
    println(result)
}

fun lunch(input: String) {
    val map: MutableMap<Char, Int> = mutableMapOf()
    ('A'..'Z').forEach { c ->
        var rest = input.filter { !(it == c || it == c + 32) }
        while (true) {
            val index = rest.indexOfFirstPolymer()
            if (index < 0) break
            rest = rest.substring(0, index) + rest.substring(index + 2)
        }
        map[c] = rest.length
    }
    val result = map.minOf { it.value }
    println(result) // 5534
}