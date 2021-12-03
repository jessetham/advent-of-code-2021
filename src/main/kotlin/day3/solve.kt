package day3

import java.io.File

fun getPowerConsumption(input: List<String>): Int {
    val numBits = input.first().length
    val columns = (0 until numBits).map { i ->
        input.joinToString(separator = "") { it[i].toString() }
    }
    val counts = columns.map { it.groupingBy { b -> b }.eachCount() }
    val gamma = counts.map { c -> c.maxByOrNull { it.value }!!.key }.joinToString("").toInt(2)
    val epsilon = counts.map { c -> c.minByOrNull { it.value }!!.key }.joinToString("").toInt(2)
    return gamma * epsilon
}

fun getLifeSupportRating(input: List<String>): Int {
    tailrec fun go(i: Int, remaining: List<String>, f: (Int, Int, Char) -> Boolean): Int =
        if (remaining.size == 1) {
            // Assuming there's always a valid answer
            remaining.first().toInt(2)
        } else {
            val count = remaining.groupingBy { it[i] }.eachCount()
            go(i + 1, remaining.filter { f(count['1']!!, count['0']!!, it[i]) }, f)
        }

    val ogr = go(0, input) { onesCount, zerosCount, c -> if (onesCount >= zerosCount) c == '1' else c == '0' }
    val csr = go(0, input) { onesCount, zerosCount, c -> if (zerosCount <= onesCount) c == '0' else c == '1' }
    return ogr * csr
}

fun main() {
    val input = File("input.txt").readLines()
    // Part 1
    println(getPowerConsumption(input))
    // Part 2
    println(getLifeSupportRating(input))
}