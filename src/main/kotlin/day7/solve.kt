package day7

import java.io.File
import kotlin.math.absoluteValue
import kotlin.math.sign

typealias DistanceToFuel = (distance: Int) -> Int

fun List<Int>.calculateFuelRequired(meetingPoint: Int, f: DistanceToFuel) =
    this.map { (it - meetingPoint).absoluteValue }.map(f).sum()

fun List<Int>.findMeetingPoint(f: DistanceToFuel): Int {
    tailrec fun go(l: Int, r: Int): Int {
        val m = (l + r) / 2
        val center = this.calculateFuelRequired(m, f)
        val onRight = this.calculateFuelRequired(m + 1, f)
        val onLeft = this.calculateFuelRequired(m - 1, f)
        val ccr = (onRight - center).sign
        val ccl = (onLeft - center).sign
        return if (ccr == 1 && ccl == 1) {
            center
        } else if (ccr == -1) {
            go(m + 1, r)
        } else {
            go(l, m - 1)
        }
    }
    return go(this.minOrNull()!!, this.maxOrNull()!!)
}

fun main() {
    val crabs = File("input.txt").readText().split(",").map(String::toInt)
    // Part 1
    println(crabs.findMeetingPoint { it })
    // Part 2
    println(crabs.findMeetingPoint { n -> (n * (n + 1)) / 2 })
}