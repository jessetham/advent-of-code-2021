package day2

import java.io.File

fun main() {
    val path = File("input.txt").readLines().map { parseInput(it) }
    // Just part 2
    println(steer(path))
}

fun parseInput(line: String) = line.split(" ").let { it[0] to it[1].toInt() }

fun steer(path: List<Pair<String, Int>>): Int {
    var x = 0
    var y = 0
    var aim = 0
    for ((instruction, units) in path) {
        when (instruction) {
            "forward" -> {
                x += units
                y += units * aim
            }
            "down" -> aim += units
            "up" -> aim -= units
        }
    }
    return x * y
}