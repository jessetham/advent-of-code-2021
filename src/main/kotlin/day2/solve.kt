package day2

import java.io.File

data class Command(val direction: String, val units: Int) {
    companion object {
        fun of(line: String) = line.split(" ").let { Command(it[0], it[1].toInt()) }
    }
}

data class Submarine(val x: Int = 0, val y: Int = 0, val aim: Int = 0) {
    fun res() = x * y

    fun update1(command: Command) = when (command.direction) {
        "forward" -> copy(x = x + command.units)
        "down" -> copy(y = y + command.units)
        "up" -> copy(y = y - command.units)
        else -> error("unknown direction")
    }

    fun update2(command: Command) = when (command.direction) {
        "forward" -> copy(x = x + command.units, y = y + command.units * aim)
        "down" -> copy(aim = aim + command.units)
        "up" -> copy(aim = aim - command.units)
        else -> error("unknown direction")
    }
}

fun main() {
    val input = File("input.txt").readLines().map(Command::of)
    // Part 1
    println(input.fold(Submarine()) { sub, c -> sub.update1(c) }.res())
    // Part 2
    println(input.fold(Submarine()) { sub, c -> sub.update2(c) }.res())
}