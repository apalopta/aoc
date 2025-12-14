package aoc2025.day06

import utils.readInput
import utils.printlnPrefixed

fun main() {
    val testInput = readInput(2025, 6, "test")

    check(part1(testInput) == 4277556L)
    check(part2(testInput) == 0L)

    val input = readInput(2025, 6, "input")

    part1(input)
        .also { check(it == 5733696195703L) }
        .also { it.printlnPrefixed("part 1") }
    part2(input)
//        .also { check(it == ) }
        .also { it.printlnPrefixed("part 2") } // merged to 88 ranges / 0.2ms
}

fun part1(input: List<String>): Long {
    val lines = input.map { line -> line.split(" ").filterNot(String::isEmpty) }
    val elems = lines.transpose()
    val res = elems.map {
        println(it)
        val operation = it.last()
        val numbers = it.dropLast(1).map { n -> n.toLong() }
        val res = when (operation) {
            "*" -> numbers.fold(1L) { acc, number -> acc * number }
            "+" -> numbers.fold(0L) { acc, number -> acc + number }
            else -> TODO("not implemented")
        }
        res
    }

    return res.sum()
}

fun List<List<String>>.transpose() =
    first().indices.map { col ->
        map { it[col] }
    }

fun part2(input: List<String>): Long {
    return 0L
}
