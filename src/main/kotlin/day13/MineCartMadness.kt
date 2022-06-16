package day13

import java.io.File

fun main() {
    val input = File("data/day13/sample.txt").readLines()
    breakfast(input)
}

enum class TurnMode {
    LEFT,
    STRAIGHT,
    RIGHT
}

data class Cart(var x: Int,
                var y: Int,
                var orientation: Char,
                var mode: TurnMode = TurnMode.LEFT)

fun getCarts(input: List<String>): List<Cart> {
    return input.flatMapIndexed() { y, line ->
        line.mapIndexedNotNull() { x, c ->
            if (c in "<v>^") Cart(x, y, c) else null
        }
    }
}

fun move(track: List<String>, cart: Cart) {
    when (cart.orientation) {
        '>' -> {
            when (track.get(cart.y)[cart.x + 1]) {
                '-' -> {
                    cart.x += 1
                }
                '/' -> {
                    cart.x += 1
                    cart.orientation = '^'
                }
                '\\' -> {
                    cart.x += 1
                    cart.orientation = 'v'
                }
                '+' -> {
                    cart.x += 1
                    when (cart.mode) {
                        TurnMode.LEFT -> {
                            cart.orientation = '^'
                            cart.mode = TurnMode.STRAIGHT
                        }
                        TurnMode.STRAIGHT -> {
                            cart.mode = TurnMode.RIGHT
                        }
                        TurnMode.RIGHT -> {
                            cart.orientation = 'v'
                            cart.mode = TurnMode.LEFT
                        }
                    }
                }
            }
        }
        '<' -> {
            when (track.get(cart.y)[cart.x - 1]) {
                '-' -> {
                    cart.x -= 1
                }
                '/' -> {
                    cart.x -= 1
                    cart.orientation = 'v'
                }
                '\\' -> {
                    cart.x -= 1
                    cart.orientation = '^'
                }
                '+' -> {
                    cart.x -= 1
                    when (cart.mode) {
                        TurnMode.LEFT -> {
                            cart.orientation = 'v'
                            cart.mode = TurnMode.STRAIGHT
                        }
                        TurnMode.STRAIGHT -> {
                            cart.mode = TurnMode.RIGHT
                        }
                        TurnMode.RIGHT -> {
                            cart.orientation = '^'
                            cart.mode = TurnMode.LEFT
                        }
                    }
                }
            }
        }
        'v' -> {
            when (track.get(cart.y + 1)[cart.x]) {
                '|' -> {
                    cart.y += 1
                }
                '/' -> {
                    cart.y += 1
                    cart.orientation = '<'
                }
                '\\' -> {
                    cart.y += 1
                    cart.orientation = '>'
                }
                '+' -> {
                    cart.y += 1
                    when (cart.mode) {
                        TurnMode.LEFT -> {
                            cart.orientation = '>'
                            cart.mode = TurnMode.STRAIGHT
                        }
                        TurnMode.STRAIGHT -> {
                            cart.mode = TurnMode.RIGHT
                        }
                        TurnMode.RIGHT -> {
                            cart.orientation = '<'
                            cart.mode = TurnMode.LEFT
                        }
                    }
                }
            }
        }
        '^' -> {
            when (track.get(cart.y - 1)[cart.x]) {
                '|' -> {
                    cart.y -= 1
                }
                '/' -> {
                    cart.y -= 1
                    cart.orientation = '>'
                }
                '\\' -> {
                    cart.y -= 1
                    cart.orientation = '<'
                }
                '+' -> {
                    cart.y -= 1
                    when (cart.mode) {
                        TurnMode.LEFT -> {
                            cart.orientation = '<'
                            cart.mode = TurnMode.STRAIGHT
                        }
                        TurnMode.STRAIGHT -> {
                            cart.mode = TurnMode.RIGHT
                        }
                        TurnMode.RIGHT -> {
                            cart.orientation = '>'
                            cart.mode = TurnMode.LEFT
                        }
                    }
                }
            }
        }
        else -> {}
    }
}

fun checkClear(carts: List<Cart>, cart: Cart): Boolean {
    return when (cart.orientation) {
        '>' -> carts.none { it.y == cart.y && it.x == cart.x + 1 }
        '<' -> carts.none { it.y == cart.y && it.x == cart.x - 1 }
        '^' -> carts.none { it.y == cart.y + 1 && it.x == cart.x }
        'v' -> carts.none { it.y == cart.y - 1 && it.x == cart.x }
        else -> true
    }
}

fun tick(track: List<String>, carts: List<Cart>) {
    carts.forEach {
        val clear = checkClear(carts, it)
        if (clear) {
            move(track, it)
        }
        else {
            println(it)
            // here we are done
            System.exit(0)
        }
    }
}

// builds the original track by factoring out the carts from the input
fun getTrack(input: List<String>): List<String> {
    return input.map { it.replace('<', '-')
        .replace('>', '-')
        .replace('^', '|')
        .replace('v', '|') }
}

fun printTrack(track: List<String>, carts: List<Cart> = emptyList()) {
    val tmp = track.map {
        it.toCharArray()
    }
    carts.forEach { cart ->
        tmp.get(cart.y)[cart.x] = cart.orientation
    }
    val result = tmp.map { String(it) }.joinToString("\n")
    println(result)
}

fun breakfast(input: List<String>) {
    val carts = getCarts(input)
    val track = getTrack(input)
    repeat(165) {
        // first crash happens at 159
        tick(track, carts)
        //printTrack(track, carts)
    }
}