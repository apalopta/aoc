package aoc2025.day05

import utils.readInput
import utils.printlnPrefixed

fun main() {
    val testInput = readInput(2025, 5, "test")

    check(part1(testInput) == 3)
    check(part2(testInput) == 14L)

    val input = readInput(2025, 5, "input")

    part1(input)
        .also { check(it == 563) }
        .also { it.printlnPrefixed("part 1") }
    part2(input)
        .also { check(it == 338693411431456L) }
        .also { it.printlnPrefixed("part 2") } // merged to 88 ranges
}

fun part1(input: List<String>): Int {
    val ranges = ranges(input)
    val items = input.dropWhile { it.contains("-") }.drop(1)
        .map { it.toLong() }

    return items.count { item -> ranges.any { it.contains(item) } }
}

fun part2(input: List<String>): Long {
    val ranges = ranges(input)
    val sortedRanges = ranges.sortedBy { it.first }
    val newRanges = mutableListOf<LongRange>()

    var myRange: LongRange = sortedRanges.first()
    sortedRanges.forEach { r ->
        print("[$myRange] [$r]")
        if (myRange.overlaps(r)) {
            print(" -> merge ranges")
            myRange += r
            println(" : $myRange")
        } else {
            print(" -> store range")
            newRanges.add(myRange)
            myRange = r
            println(" : start new $myRange")
        }
    }
    println(" -> store last range : $myRange")
    newRanges.add(myRange)
    newRanges.forEach { println(it) }

    return newRanges.map { it.last - it.first + 1 }
        .also { println("ranges: ${it.size} -> $it") }
        .sum()  // takes too long!
}

private fun ranges(input: List<String>): List<LongRange> {
    val ranges = input.takeWhile { it.contains("-") }
        .map { LongRange(it.substringBefore("-").toLong(), it.substringAfter("-").toLong()) }
    return ranges
}

fun LongRange.overlaps(other: LongRange): Boolean {
    return if (start <= other.first)
        last >= other.first
    else
        start <= other.last

}

operator fun LongRange.plus(other: LongRange): LongRange {
    val start = if (this.first < other.first) first else other.first
    val end = if (this.last < other.last) other.last else last
    return LongRange(start, end)
}