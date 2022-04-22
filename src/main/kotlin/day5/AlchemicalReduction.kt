package day5

import java.io.File

fun main() {
    val input = File("data/day5/input.txt").readText()
    breakfast("dabAcCaCBAcCcaDA")
    // breakfast(input)
}

fun String.isPolymer() = (kotlin.math.abs(this[0] - this[1]) == 32)

fun List<Char>.isPolymer() = (kotlin.math.abs(this[0] - this[1]) == 32)

fun String.indexOfFirstPolymer() = this.windowed(2, 1).indexOfFirst { it.isPolymer() }

fun MutableList<Char>.indexOfFirstPolymer() = this.windowed(2, 1).indexOfFirst { it.isPolymer() }

fun String.react(): String {
    val index = this.indexOfFirstPolymer()
    return if (index == -1) this
    else (this.substring(0, index) + this.substring(index + 2)).react()
}

fun MutableList<Char>.react(): String {
    val index = this.indexOfFirstPolymer()
    return if (index == -1) this.joinToString("")
    else {
        this.removeAt(index + 1)
        this.removeAt(index + 1)
        this.react()
    }
}

fun breakfast(input: String) {
    println(input)
    val rest = input.react()
    println(rest)
    val rest2 = input.toMutableList().react()
    println(rest2)
}
