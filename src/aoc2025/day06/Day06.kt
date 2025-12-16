package aoc2025.day06

import utils.readInput
import utils.printlnPrefixed

fun main() {
    val testInput = readInput(2025, 6, "test")

    check(part1(testInput) == 4277556L)
    check(part2(testInput) == 3263827L)
    println("=========")

    val input = readInput(2025, 6, "input")

    part1(input)
        .also { check(it == 5733696195703L) }
        .also { it.printlnPrefixed("part 1") }
    part2(input)
        .also { check(it == 10951882745757L) }
        .also { it.printlnPrefixed("part 2") }
}

fun part1(input: List<String>): Long {
    val lines = input.map { line -> line.split(" ").filterNot(String::isEmpty) }
    val elems = lines.transpose()
    val res = runCalculation(elems)
    return res.sum()
}

fun List<List<String>>.transpose() =
    first().indices.map { col ->
        map { it[col] }
    }

fun part2(input: List<String>): Long {
    val maxLength = input.maxOf { it.length }
    val myInput = input.map { line -> line.padEnd(maxLength, padChar = ' ') }

    val transposed = transposeXLines(myInput, input.size - 1)

    val operations = input.takeLast(1)[0].split(" ").filterNot(String::isEmpty)
    val instructions = transposed.zip(operations).map { (a, b) -> a.map { it } + b }

    println("instructions:")
    instructions.forEach { println(it) }

    val res = runCalculation(instructions)

    return res.sum()
        .also(::println)
}

private fun transposeXLines(myInput: List<String>, size: Int): List<List<String>> {
    val numbers = myInput.take(size).map { it.toCharArray().joinToString("") }
    println("-> numbers")
    println(numbers)
    println()
    val widthRange = (0..numbers.lastIndex)
    val sepIndices = numbers[0].indices.filter { i ->
        widthRange.all { numbers[it][i] == ' ' }
    }.toMutableList().apply {
        addFirst(-1)
        addLast(numbers[0].length)
    }
        .zipWithNext { a, b -> (a+1..b-1) }
        .also(::println)

    val transposed = sepIndices
        .map { range -> numbers.map { it.substring(range) } }
        .map { it.transposeLine().map { it.joinToString("") } }

    println("-> transposed:")
    transposed.forEach { println(it) }
//        .windowed(size, size + 1)
//        .map { it.joinToString("") }
//        .toList()
//        .also { println(it) }
//        .map { it.map { it.joinToString("").trim() } }
    return transposed
}

private fun runCalculation(instructions: List<List<String>>): List<Long> {
    val res = instructions.map {
        println(it)
        val operation = it.last()
        val numbers = it.dropLast(1).map { n -> n.ifEmpty { "0" }.trim().toLong() }
        val res = when (operation) {
            "*" -> numbers.fold(1L) { acc, number -> acc * number }
            "+" -> numbers.fold(0L) { acc, number -> acc + number }
            else -> TODO("not implemented")
        }
        res
    }
    return res
}

fun List<String>.transposeLine() =
    first().indices.map { col ->
        map { it[col] }
    }

