package day10

import java.io.File

import java.awt.Color
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

fun main() {
    val input = File("data/day10/input.txt").readLines()
    breakfast(input)
}

data class Point(var x: Int, var y: Int, val vx: Int, val vy: Int) {
    companion object {
        fun from(input: String): Point {
            val t = input.split("<", ",", ">").map { it.trim() }
            return Point(t[1].toInt(), t[2].toInt(), t[4].toInt(), t[5].toInt())
        }
    }
}

fun List<Point>.print() {
    val minX = this.minOf { it.x }
    val maxX = this.maxOf { it.x }
    val minY = this.minOf { it.y }
    val maxY = this.maxOf { it.y }
    val visible = this.map { Pair(it.x, it.y) }.toSet()
    val s = (minY..maxY).joinToString(separator = "\n") { y ->
        (minX..maxX).map { x ->
            if (Pair(x, y) in visible) '#' else '.'
        }.joinToString(separator = "")
    }
    println(s)
}

fun List<Point>.getSize(): Int {
    val rangeX = this.maxOf { it.x } - this.minOf { it.x }
    val rangeY = this.maxOf { it.y } - this.minOf { it.y }
    return maxOf(rangeX, rangeY)
}

// just scales for the sample, not the 100k * 100k frame
fun breakfast(input: List<String>) {
    val threshold = 70
    val points = input.map { Point.from(it) }
    var currentMin = Int.MAX_VALUE
    var currentIndex = 0
    repeat(11000) {
        points.forEach {
            it.x += it.vx
            it.y += it.vy
        }
        val size = points.getSize()
        if (size < currentMin) {
             currentMin = size
            currentIndex = it
        }
        // this will be our message
        if (size < threshold) points.print()
    }
    // this will be the number of seconds the elves have to wait
    println(currentIndex + 1)
}