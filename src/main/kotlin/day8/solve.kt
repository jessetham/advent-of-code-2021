package day8

import java.io.File

fun solveFives(fives: List<Set<Char>>, zones: Map<Int, Set<Char>>) =
    fives.associateWith { set ->
        val mask = zones[4]!! - set
        when {
            (zones[1]!! + zones[2]!!).containsAll(mask) -> "2"
            (zones[2]!! + zones[3]!!).containsAll(mask) -> "3"
            (zones[1]!! + zones[3]!!).containsAll(mask) -> "5"
            else -> error("unknown mask")
        }
    }

fun solveSixes(sixes: List<Set<Char>>, zones: Map<Int, Set<Char>>) =
    sixes.associateWith { set ->
        val mask = zones[4]!! - set
        when {
            zones[2]!!.containsAll(mask) -> "0"
            zones[1]!!.containsAll(mask) -> "6"
            zones[3]!!.containsAll(mask) -> "9"
            else -> error("unknown mask")
        }
    }

fun decodeSignals(signals: List<Set<Char>>): Map<Set<Char>, String> {
    val one = signals.first { it.size == 2 }
    val four = signals.first { it.size == 4 }
    val seven = signals.first { it.size == 3 }
    val eight = signals.first { it.size == 7 }
    // Zone 1 maps to segments c and f, zone 2 maps to segments b and d, zone 3 maps to segments e and g, zone 4 is all segments
    val zones = mapOf(1 to one, 2 to four - one, 3 to eight - seven - four, 4 to eight)

    return mapOf(
        one to "1", four to "4", seven to "7", eight to "8"
    ) + solveFives(signals.filter { it.size == 5 }, zones) + solveSixes(signals.filter { it.size == 6 }, zones)
}

fun decodeOutputs(outputs: List<Set<Char>>, decodedSignals: Map<Set<Char>, String>) =
    outputs.joinToString("") { o -> decodedSignals[o]!! }.toInt()

fun main() {
    val lines = File("input.txt").readLines()
    val entries = lines.map { line ->
        line.split(" | ")
            .let {
                it[0].split(" ").map(String::toSet) to it[1].split(" ").map(String::toSet)
            }
    }

    // Part 1
    val uniqueCount = entries.sumOf { (_, outputs) ->
        outputs.count { o -> o.size == 2 || o.size == 4 || o.size == 3 || o.size == 7 }
    }
    println(uniqueCount)

    // Part 2
    println(entries.sumOf { (signals, outputs) -> decodeOutputs(outputs, decodeSignals(signals)) })
}