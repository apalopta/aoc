package aoc2025.day01

import utils.readInput
import utils.printlnPrefixed

fun main() {
    val testInput = readInput(2025, 1, "test")

    check(part1(testInput) == "n/a")
    //check(part2(testInput) == ...)

    val input = readInput(2025, 1, "input")

    part1(input).printlnPrefixed("part1")
    part2(input).printlnPrefixed("part2")
}

fun part1(input: List<String>): String {
    return "n/a"
}

fun part2(input: List<String>): String {
    return "n/a"
}
