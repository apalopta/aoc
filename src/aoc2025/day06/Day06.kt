package aoc2025.day06

import utils.readInput
import utils.printlnPrefixed

fun main() {
    val testInput = readInput(2025, 6, "test")

    check(part1(testInput) == 4277556L)
    check(part2(testInput) == 3263827L)

    val input = readInput(2025, 6, "input")

    part1(input)
        .also { it.printlnPrefixed("part 1") }
    part2(input)
        .also { it.printlnPrefixed("part 2") }
}

fun part1(input: List<String>): Long {
    val linesOfNumbers = input.map { line -> line.split(" ").filterNot(String::isEmpty) }
    val elems = linesOfNumbers.transpose()

    return runCalculation(elems).sum()
}

fun List<List<String>>.transpose() =
    first().indices.map { col ->
        map { it[col] }
    }

fun part2(input: List<String>): Long {
    val maxLength = input.maxOf { it.length }
    val numberLines = input.map { line -> line.padEnd(maxLength, padChar = ' ') }

    val transposedNumberBlocks = verticalTranspose(numberLines.dropLast(1))
    val operations = input.takeLast(1)[0].split(" ").filterNot(String::isEmpty)

    val instructions = transposedNumberBlocks
        .zip(operations)
        .map { (a, b) -> a.map { it } + b }

    return runCalculation(instructions).sum()
}

private fun verticalTranspose(lines: List<String>): List<List<String>> {
    val numberOfDigits = lines.indices

    val rangesWithNumberInside = lines[0].indices.filter { i ->
        numberOfDigits.all { lines[it][i] == ' ' } // separating column
    }.toMutableList().apply {
        addFirst(-1)
        addLast(lines[0].length)
    }.zipWithNext { a, b -> (a + 1..<b) }

    return rangesWithNumberInside
        .map { range -> lines.map { it.substring(range) } }
        .map { lines -> lines.transposeLines().map { it.joinToString("").trim() } }
}

private fun runCalculation(instructions: List<List<String>>): List<Long> {
    return instructions.map {
        val operation = it.last()
        val numbers = it.dropLast(1)
            .map { n -> n.toLong() }
        when (operation) {
            "*" -> numbers.product()
            "+" -> numbers.sum()
            else -> TODO("operation not implemented")
        }
    }
}

fun List<Long>.product() = fold(1L) { acc, number -> acc * number }

fun List<String>.transposeLines() =
    first().indices.map { col ->
        map { it[col] }
    }

