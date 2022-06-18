package day14

fun main() {
    val input = 846601
    val seed = "37"
    breakfast(input, seed)
}

fun breakfast(input: Int, seed: String) {
    val recipes = seed.map { it.toString().toInt() }.toMutableList()
    var elf1 = 0
    var elf2 = 1
    while (recipes.size < input.toInt() + 10) {
        val current1 = recipes.get(elf1)
        val current2 = recipes.get(elf2)
        val step = current1 + current2
        if (step / 10 > 0) recipes.add(step / 10)
        recipes.add(step % 10)
        elf1 = (elf1 + current1 + 1) % recipes.size
        elf2 = (elf2 + current2 + 1) % recipes.size
    }
    val result = recipes.drop(input.toInt()).take(10)
    println(result)
}