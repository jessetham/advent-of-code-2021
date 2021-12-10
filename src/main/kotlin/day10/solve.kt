package day10

import java.io.File

val closeToOpen = mapOf(')' to '(', ']' to '[', '}' to '{', '>' to '<')
val openToClose = mapOf('(' to ')', '[' to ']', '{' to '}', '<' to '>')

fun String.calculateCorruptedScore(): Int {
    val scores = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)

    tailrec fun go(i: Int, openings: List<Char>): Int =
        if (i == this.length) {
            0
        } else if (this[i] in closeToOpen) {
            when {
                // Assuming that there are no dangling close brackets
                openings.last() != closeToOpen[this[i]] -> scores[this[i]]!!
                else -> go(i + 1, openings.dropLast(1))
            }
        } else {
            go(i + 1, openings + this[i])
        }
    return go(0, listOf())
}

fun String.calculateIncompleteScore(): Long {
    val scores = mapOf(')' to 1, ']' to 2, '}' to 3, '>' to 4)
    val unclosed = this.fold(listOf<Char>()) { openings, b ->
        if (b in closeToOpen) openings.dropLast(1) else openings + b
    }
    val fixes = unclosed.map { openToClose[it] }.reversed()
    return fixes.fold(0) { sumSoFar, fix -> sumSoFar * 5 + scores[fix]!! }
}

fun main() {
    val lines = File("input.txt").readLines()

    // Part 1
    println(lines.sumOf(String::calculateCorruptedScore))

    // Part 2
    val onlyIncomplete = lines.filter { line -> line.calculateCorruptedScore() == 0 }
    val incompleteScores = onlyIncomplete.map(String::calculateIncompleteScore)
    println(incompleteScores.sorted()[incompleteScores.size / 2])
}