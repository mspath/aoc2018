package day8

import java.io.File

fun main() {
    val input = File("data/day8/input.txt").readText().trim().split(" ").map { it.toInt() }
    breakfast(input)
}

data class Tree(val children: List<Tree>, val meta: List<Int>) {

    fun sumMeta(): Int = meta.sum() + children.sumOf { it.sumMeta() }

    fun value(): Int {
        if (children.isEmpty()) return meta.sum()
        else {
            return meta.sumOf { children.getOrNull(it - 1)?.value() ?: 0 }
        }
    }

    companion object {
        fun fromIterator(input: Iterator<Int>): Tree {
            val kids: Int = input.next()
            val metas: Int = input.next()
            val children = (0 until kids).map { fromIterator(input) }
            val metadata = (0 until metas).map { input.next() }
            return Tree(children, metadata)
        }
    }
}

fun breakfast(input: List<Int>) {
    val tree = Tree.fromIterator(input.iterator())
    println(tree)
    val meta = tree.sumMeta()
    println(meta)
    val value = tree.value()
    println(value)
}