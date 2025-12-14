package aoc2025.day05

import utils.readInput
import utils.printlnPrefixed

fun main() {
    val testInput = readInput(2025, 5, "test")

    check(part1(testInput) == 3)
    check(part2(testInput) == 14)

    val input = readInput(2025, 5, "input")

    part1(input)
//        .also { check(it == ) }
        .also { it.printlnPrefixed("part 1") }
    part2(input)
//        .also { check(it == ) }
        .also { it.printlnPrefixed("part 2") }
}

fun part1(input: List<String>): Int {
    val ranges = input.takeWhile { it.contains("-") }
        .map { LongRange(it.substringBefore("-").toLong(), it.substringAfter("-").toLong()) }
    val items = input.dropWhile { it.contains("-") }.drop(1)
        .map { it.toLong() }

    return items.count { item -> ranges.any { it.contains(item) } }
}

fun part2(input: List<String>): Int {
    val ranges = input.takeWhile { it.contains("-") }
        .map { LongRange(it.substringBefore("-").toLong(), it.substringAfter("-").toLong()) }


    return ranges.flatMap { it.toList() }.toSet().size  // takes too long!
}
