package aoc2025.day06

import utils.readInput
import utils.printlnPrefixed

fun main() {
    val testInput = readInput(2025, 6, "test")

    check(part1(testInput) == 0L)
    check(part2(testInput) == 0L)

    val input = readInput(2025, 6, "input")

    part1(input)
//        .also { check(it == ) }
        .also { it.printlnPrefixed("part 1") }
    part2(input)
//        .also { check(it == ) }
        .also { it.printlnPrefixed("part 2") } // merged to 88 ranges / 0.2ms
}

fun part1(input: List<String>): Long {
    return 0L
}

fun part2(input: List<String>): Long {
    return 0L
}
