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

    fun size(): Int = (end.x - start.x + 1) * (end.y - start.y + 1)

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
    val resultLunch = lunch(claims) // 506
    println(resultLunch)
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

fun lunch(claims: List<Claim>): Int {

    // we can reuse the map counting the claimers from breakfast
    val pointsMap = claims.flatMap { it.getPoints() }
        .groupingBy { it }
        .eachCount()

    // if adding up the claimers of all points equals the area of a claim we've found our result
    claims.forEach { claim ->
        val claimerOfArea = claim.getPoints().sumOf {
            pointsMap.getOrDefault(it, 1)
        }
        if (claimerOfArea == claim.size()) return(claim.id)
    }

    return -1
}