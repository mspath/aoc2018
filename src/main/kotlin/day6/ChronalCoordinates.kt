package day6

import java.io.File
import kotlin.math.abs

fun main() {
    val input = File("data/day6/input.txt").readLines()
    //breakfast(input)
    lunch(input, 10_000)
}

data class Point(val x: Int, val y: Int)

data class Checkpoint(val point: Point, var owner: Point, var distance: Int = Int.MAX_VALUE, var draw: Boolean = false)

fun Point.distance(other: Point) = abs(this.x - other.x) + abs(this.y - other.y)

fun breakfast(input: List<String>) {

    // the points with coordinates
    val points = input.map {
        Point(it.substringBefore(",").toInt(), it.substringAfter(" ").toInt())
    }

    // dimension of the field
    val maxX = points.maxOf { it.x }
    val maxY = points.maxOf { it.y }

    // keep the 'checkpoints' in a map to easily look them up
    val grid: MutableMap<Point, Checkpoint> = mutableMapOf()

    // fill the grid with the checkpoints
    (0..maxY).forEach { y ->
        (0 .. maxX).forEach { x ->
            if (Point(x, y) !in points) {
                val checkpoint = Checkpoint(point = Point(x, y), owner = Point(maxX + 10, maxY + 10))
                points.forEach { point ->
                    val distance = point.distance(checkpoint.point)
                    if (distance < checkpoint.distance && distance > 0) {
                        checkpoint.owner = point
                        checkpoint.distance = distance
                        checkpoint.draw = false
                    } else if (distance == checkpoint.distance) {
                        checkpoint.owner = point
                        checkpoint.draw = true
                    }
                }
                grid[Point(x, y)] = checkpoint
            }
        }
    }

    // now we have to turn off the areas which are open-ended
    // all owners of edge points are starting points and can be excluded
    // (1) collect the edge points
    val setEdgePoints: MutableSet<Point> = mutableSetOf()
    (0..maxX).forEach { x ->
        val point = Point(x, 0)
        if (point !in points) setEdgePoints.add(point)
        val point2 = Point(x, maxY)
        if (point2 !in points) setEdgePoints.add(point2)
    }
    (0..maxY).forEach { y ->
        val point = Point(0, y)
        if (point !in points) setEdgePoints.add(point)
        val point2 = Point(maxX, y)
        if (point2 !in points) setEdgePoints.add(point2)
    }
    // (2) collect their owners
    val setEdgePointsOwners = setEdgePoints.mapNotNull { point ->
        val checkpoint = grid[point]
        if (checkpoint == null) null
        else if (checkpoint.draw) null
        else checkpoint.owner
    }.toSet()
    // (3) set als checkpoints with those owners to false
    grid.values.forEach {
        if (it.owner in setEdgePointsOwners) it.draw = true
    }
    val grouped = grid.filter { !it.value.draw }.map { it.value }.groupBy { it.owner }
    val sized = grouped.map { it.value.size }
    val result = sized.maxOf { it }
    println(result + 1) // 3909
}

fun lunch(input: List<String>, total: Int) {

    // the points with coordinates
    val points = input.map {
        Point(it.substringBefore(",").toInt(), it.substringAfter(" ").toInt())
    }

    val xRange: IntRange = (points.minOf { it.x }..points.maxOf { it.x })
    val yRange: IntRange = (points.minOf { it.y }..points.maxOf { it.y })

    val result = xRange.asSequence().flatMap { x ->
        yRange.asSequence().map { y ->
            val p = Point(x, y)
            points.map { it.distance(p) }.sum()
        }
    }.filter { it < total }.toList().count()

    println(result)
}