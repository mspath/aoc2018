package day11

fun main() {
    //breakfast()
    lunch()
}

data class Cell(val x: Int, val y: Int, val power: Int) {

    companion object {
        fun calculatePower(x: Int, y: Int, serial: Int): Int {
            val rackId = x + 10
            var powerLevel = rackId * y
            powerLevel += serial
            powerLevel *= rackId
            powerLevel /= 100
            powerLevel %= 10
            powerLevel -= 5
            return powerLevel
        }

        fun from(x: Int, y: Int, serial: Int = 8): Cell {
            return Cell(x, y, calculatePower(x, y, serial))
        }
    }
}

fun breakfast() {
    val empty = Cell(0, 0, 0)
    val serial = 9424
    val cells = (1..300).flatMap { x ->
        (1..300).map { y ->
            Cell.from(x, y, serial)
        }
    }
    val lookup = cells.associateBy { Pair(it.x, it.y) }
    val grids = (1..298).flatMap { x ->
        (1..298).map { y ->
            val p = lookup.getOrDefault(Pair(x, y), empty).power +
                    lookup.getOrDefault(Pair(x + 1, y), empty).power +
                    lookup.getOrDefault(Pair(x + 2, y), empty).power +
                    lookup.getOrDefault(Pair(x, y + 1), empty).power +
                    lookup.getOrDefault(Pair(x + 1, y + 1), empty).power +
                    lookup.getOrDefault(Pair(x + 2, y + 1), empty).power +
                    lookup.getOrDefault(Pair(x, y + 2), empty).power +
                    lookup.getOrDefault(Pair(x + 1, y + 2), empty).power +
                    lookup.getOrDefault(Pair(x + 2, y + 2), empty).power
            Pair(p, Pair(x, y))
        }
    }
    val result = grids.sortedBy { it.first }.last()
    println(result)
}

fun Map<Pair<Int, Int>, Cell>.maxForGridsize(gridsize: Int): Pair<Int, Int> {
    val empty = Cell(0, 0, 0)
    val grids = (1..298).flatMap { x ->
        (1..298).map { y ->
            val p = (0 until gridsize).flatMap { xi ->
                (0 until gridsize).map { yi ->
                    this.getOrDefault(Pair(x + xi, y + yi), empty).power
                }
            }.sum()
            Pair(p, Pair(x, y))
        }
    }
    println("--")
    println(grids.sortedBy { it.first }.last().first)
    return grids.sortedBy { it.first }.last().second
}

fun lunch() {
    val empty = Cell(0, 0, 0)
    val serial = 9424
    val cells = (1..300).flatMap { x ->
        (1..300).map { y ->
            Cell.from(x, y, serial)
        }
    }
    val lookup = cells.associateBy { Pair(it.x, it.y) }
    // will not scale to larger grids but they do average themselves out so this did the job
    repeat(20) {
        val max = lookup.maxForGridsize(it + 1)
        println("result: ${max.first},${max.second},${it + 1}")
    }
}