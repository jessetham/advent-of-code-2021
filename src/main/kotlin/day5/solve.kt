package day5

import java.io.File
import kotlin.math.absoluteValue
import kotlin.math.sign


data class Segment(val x1: Int, val y1: Int, val x2: Int, val y2: Int) {
    companion object {
        private val regex by lazy { """(\d+),(\d+) -> (\d+),(\d+)""".toRegex() }

        fun of(s: String): Segment {
            val (x1, y1, x2, y2) = regex.matchEntire(s)!!.destructured
            return Segment(x1.toInt(), y1.toInt(), x2.toInt(), y2.toInt())
        }
    }

    fun getCoordinates(): List<Pair<Int, Int>> {
        val dx = (x2 - x1).sign
        val dy = (y2 - y1).sign
        val steps = if (y2 == y1) (x2 - x1).absoluteValue else (y2 - y1).absoluteValue
        return (0..steps).map { i -> (x1 + dx * i) to (y1 + dy * i) }
    }

    fun isNotDiagonal() = x1 == x2 || y1 == y2
}

fun countOverlaps(segments: List<Segment>): Int {
    val coordinates = segments.flatMap { it.getCoordinates() }
    val counter = coordinates.groupingBy { it }.eachCount()
    return counter.filterValues { it >= 2 }.count()
}

fun main() {
    val segments = File("input.txt").readLines().map(Segment::of)
    // Part 1
    val onlyHorizontalOrVertical = segments.filter { it.isNotDiagonal() }
    println(countOverlaps(onlyHorizontalOrVertical))
    // Part 2
    println(countOverlaps(segments))
}