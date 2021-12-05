package day4

import java.io.File


data class BingoCard(val values: List<Int>, val n: Int, val called: Set<Int> = emptySet()) {
    companion object {
        private val regex by lazy { """\s+""".toRegex() }

        fun of(s: String): BingoCard {
            val nums = s.split(regex).filter(String::isNotEmpty).map(String::toInt)
            val n = s.split("\n").size
            return BingoCard(nums, n)
        }
    }

    fun update(num: Int) = copy(called = called + num)

    fun isWinner(): Boolean {
        val rows = values.chunked(n)
        val cols = (0 until n).map { c -> rows.fold(emptyList<Int>()) { acc, row -> acc + row[c] } }
        return rows.any(::allCalled) || cols.any(::allCalled)
    }

    private fun allCalled(nums: List<Int>) = nums.all { it in called }

    fun unmarkedSum() = values.filter { it !in called }.sum()
}

typealias StopCondition = (winners: List<BingoCard>, stillPlaying: List<BingoCard>) -> Boolean

fun solve(drawn: List<Int>, cards: List<BingoCard>, f: StopCondition): Int {
    tailrec fun go(i: Int, currentCards: List<BingoCard>): Int {
        val (winners, stillPlaying) = currentCards.map { it.update(drawn[i]) }.partition { it.isWinner() }
        return if (f(winners, stillPlaying)) {
            winners.first().unmarkedSum() * drawn[i]
        } else go(i + 1, stillPlaying)
    }
    return go(0, cards)
}

fun main() {
    val input = File("input.txt").readText().split("\n\n")
    val drawn = input.first().split(",").map(String::toInt)
    val cards = input.drop(1).map(BingoCard::of)

    // Part 1
    println(solve(drawn, cards) { winners, _ -> winners.isNotEmpty() })
    // Part 2
    println(solve(drawn, cards) { _, stillPlaying -> stillPlaying.isEmpty() })
}