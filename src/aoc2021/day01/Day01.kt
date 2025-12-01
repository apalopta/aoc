package aoc2021.day01

import utils.readInput
import utils.println

fun main() {
    val testInput = readInput(2021, 1, "test")
        .map { it.toInt() }

    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput(2021, 1, "input")
        .map { it.toInt() }

    part1(input).println()
    part2(input).println()
}

fun part1(input: List<Int>): Int {
    var result = 0

    input
        .windowed(2, 1)
        .forEach { if (it[1] > it[0]) result++ }

    return result
}

fun part2(input: List<Int>): Int {
    var result = 0

    input
        .windowed(3, 1, partialWindows = false)
        .map { it.sum() } //.also { println(it) }
        .windowed(2, 1)
        .forEach { if (it[1] > it[0]) result++ }

    return result
}
