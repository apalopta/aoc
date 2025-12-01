package aoc2025.day02

import utils.readInput
import utils.println

fun main() {
    // Test if implementation meets criteria from the description, like:
    //check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/aoc2025/dayxx/testx.txt` file:
    val testInput = readInput(2025, 2, "test")
    check(part1(testInput) == "n/a")

    // Read the input from the `src/Day01.txt` file.
    val input = readInput(2025, 2, "input")
    part1(input).println()
    part2(input).println()
}

fun part1(input: List<String>): String {
    return "n/a"
}

fun part2(input: List<String>): String {
    return "n/a"
}

