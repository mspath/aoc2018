package day3

import java.io.File

data class Point(val x: Int, val y: Int)

data class Claim(val id: Int, val start: Point, val end: Point) {

    fun getPoints(): List<Point> {
        val points = (start.x..end.x).flatMap { x ->
            (start.y..end.y).map { y ->
                Point(x, y)
            }
        }
        return points
    }

}

fun String.toClaim(): Claim? {
    val pattern = """^#(\d+) @ (\d+),(\d+): (\d+)x(\d+)$""".toRegex()
    val claim = pattern.find(this)?.let {
        val (id, x, y, width, height) = it.destructured
        Claim(id.toInt(),
            Point(x.toInt(), y.toInt()),
            Point(x.toInt() + width.toInt() - 1, y.toInt() + height.toInt() - 1))
    }
    return claim
}

fun main() {
    val input = File("data/day3/input.txt").readLines()
    val claims = parseInput(input)
    val resultBreakfast = breakfast(claims) // 109143
    println(resultBreakfast)
}

fun parseInput(input: List<String>): List<Claim> {
    return input.mapNotNull {
        it.toClaim()
    }
}

fun breakfast(claims: List<Claim>): Int {

    val result = claims.flatMap { it.getPoints() }
        .groupingBy { it }
        .eachCount()
        .count { it.value > 1 }

    return result

}