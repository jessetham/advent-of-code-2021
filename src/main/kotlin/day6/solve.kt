package day6

import java.io.File

fun List<Long>.step() = this.indices.map { i -> this[(i + 1) % 9] + (if (i == 6) this[0] else 0) }

fun List<Int>.simulate(numberOfDays: Int): List<Long> {
    val initial = List(9) { i -> this.count { it == i }.toLong() }
    return (0 until numberOfDays).fold(initial) { acc, _ -> acc.step() }
}

fun main() {
    val fishes = File("input.txt").readText().split(",").map(String::toInt)
    // Part 1
    println(fishes.simulate(80).sum())
    // Part 2
    println(fishes.simulate(256).sum())
}