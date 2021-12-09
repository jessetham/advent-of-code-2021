package day9

import java.io.File

fun List<List<Int>>.getOrMax(r: Int, c: Int) = this.getOrNull(r)?.getOrNull(c) ?: Int.MAX_VALUE

fun calculateBasinSize(heightmap: List<List<Int>>, start: Pair<Int, Int>): Int {
    fun go(r: Int, c: Int, seen: MutableSet<Pair<Int, Int>>): Int =
        if (heightmap[r][c] == 9) {
            0
        } else {
            var res = 0
            seen.add(r to c)
            listOf(r + 1 to c, r - 1 to c, r to c + 1, r to c - 1).forEach { (nr, nc) ->
                if (nr in heightmap.indices && nc in heightmap.first().indices && nr to nc !in seen) {
                    res += go(nr, nc, seen)
                }
            }
            res + 1
        }
    return go(start.first, start.second, mutableSetOf())
}

fun main() {
    val heightmap = File("input.txt").readLines().map { line -> line.map { Character.getNumericValue(it) } }
    val lowPoints = heightmap.flatMapIndexed { r, row ->
        row.indices.filter { c ->
            listOf(
                heightmap.getOrMax(r - 1, c),
                heightmap.getOrMax(r + 1, c),
                heightmap.getOrMax(r, c - 1),
                heightmap.getOrMax(r, c + 1)
            ).all { neighbor -> neighbor > heightmap[r][c] }
        }.map { r to it }
    }

    // Part 1
    println(lowPoints.sumOf { (r, c) -> heightmap[r][c] + 1 })
    // Part 2
    val basinSizes = lowPoints.map { lp -> calculateBasinSize(heightmap, lp) }
    println(basinSizes.sorted().takeLast(3).let { it[0] * it[1] * it[2] })
}