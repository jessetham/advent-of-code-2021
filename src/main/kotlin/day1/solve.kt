package day1

import java.io.File

fun main() {
    val depths = File("input.txt").readLines().map(String::toInt)

    // Part 1
    val count = depths.windowed(2).count { it[0] < it[1] }
    println(count)

    // Part 2
    val slidingWindow = depths.windowed(3).map(List<Int>::sum)
    println(slidingWindow.windowed(2).count { it[0] < it[1] })
}