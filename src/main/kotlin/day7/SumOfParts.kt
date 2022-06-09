package day7

import java.io.File

fun main() {
    val input = File("data/day7/input.txt").readLines()
    //breakfast(input)
    lunch(input, 5, 60)
}

fun getNext(rules: List<Pair<Char, Char>>): Char {
    val froms = rules.map { it.first }
    val tos = rules.map { it.second }
    return froms.filter { !tos.contains(it) }.sorted().first()
}

fun getAllNext(rules: List<Pair<Char, Char>>): List<Char> {
    val froms = rules.map { it.first }
    val tos = rules.map { it.second }
    return froms.filter { !tos.contains(it) }.toSortedSet().toList()
}

fun getFreeWorker(workers: Int, jobs: List<Job>, timestamp: Int): Int? {
    val activeWorkers = jobs.filter { it.end >= timestamp }.map { it.worker }.toSortedSet()
    for (w in (0 until workers)) {
        if (w !in activeWorkers) return w
    }
    return null
}

fun breakfast(input: List<String>) {

    var rules = input.map {
        Pair(it[5], it[36])
    }
    println(rules)

    while (rules.size > 1) {
        val next = getNext(rules)
        print(next)
        rules = rules.filterNot { it.first == next }
        //println(rules)
    }
    // the last rule
    print(rules.first().first)
    print(rules.first().second)
}

data class Job(val name: Char, val worker: Int, val start: Int, val end: Int)

// does not pass yet
fun lunch(input: List<String>, workers: Int, cost: Int) {

    var rules = input.map {
        Pair(it[5], it[36])
    }
    val jobs: MutableList<Job> = mutableListOf()
    var clock = 0

    while (rules.size > 1) {
        val available = getAllNext(rules)
        for (a in available) {
            val w = getFreeWorker(workers, jobs, clock)
            w?.let {
                val job = Job(a, w, clock, clock + (a - 'A') + cost)
                jobs.add(job)
            }
        }
        for (job in jobs.filter { it.end < clock }) {
            rules = rules.filterNot { it.first == job.name }
        }
        clock++
    }
    val result = jobs.map { it.end }.sorted().last()
    println(result)
}