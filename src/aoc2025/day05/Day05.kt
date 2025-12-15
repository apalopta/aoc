package aoc2025.day05

import utils.readInput
import utils.printlnPrefixed
import kotlin.math.max
import kotlin.math.min

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
        .also { it.printlnPrefixed("part 2") } // merged to 88 ranges / 0.2ms
}

fun part1(input: List<String>): Int {
    val ranges = ranges(input)
    val items = input.dropWhile { it.contains("-") }
        .drop(1)
        .map { it.toLong() }

    return items.count { item -> ranges.any { it.contains(item) } }
}

fun part2(input: List<String>): Long {
    return mergeRanges(ranges(input))
        .sumOf { it.last - it.first + 1 }
}

private fun mergeRanges(ranges: List<LongRange>): List<LongRange> {
    val sortedRanges = ranges.sortedBy { it.first }
    var range: LongRange = sortedRanges.first()
    val mergedRanges = mutableListOf<LongRange>()

    sortedRanges.forEach { otherRange ->
        if (range.overlaps(otherRange)) {
            range += otherRange
        } else {
            mergedRanges.add(range)
            range = otherRange
        }
    }
        .also { mergedRanges.add(range) }

    return mergedRanges.toList()
}

private fun ranges(input: List<String>): List<LongRange> =
    input.takeWhile { it.contains("-") }.map {
        LongRange(it.substringBefore("-").toLong(), it.substringAfter("-").toLong())
    }

fun LongRange.overlaps(other: LongRange): Boolean =
    if (start <= other.first)
        last >= other.first
    else
        start <= other.last

operator fun LongRange.plus(other: LongRange): LongRange =
    LongRange(min(first, other.first), max(last, other.last))
