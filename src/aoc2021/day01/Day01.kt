package aoc2021.day01

import utils.readInput
import utils.printlnPrefixed

fun main() {
    val testInput = readInput(2021, 1, "test")
        .map { it.toInt() }

    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput(2021, 1, "input")
        .map { it.toInt() }

    part1(input).printlnPrefixed("part 1")
    part2(input).printlnPrefixed("part 2")
}

fun part1(input: List<Int>): Int {
    return input
        .windowed(2, 1)
        .map { if (it[1] > it[0]) 1 else 0 }//.also { println(it) }
        .sum()
}

fun part2(input: List<Int>): Int {
    return input
        .windowed(3, 1, partialWindows = false)
        .map { it.sum() } //.also { println(it) }
        .windowed(2, 1)//.also { println(it) }
        .map { if (it[1] > it[0]) 1 else 0 }//.also { println(it) }
        .sum()
}
