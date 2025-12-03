package aoc2025.day02

import utils.printlnPrefixed
import utils.readInputAsString

fun main() {
    val testInput = readInputAsString(2025, 2, "test")
        .split(",")

    check(part1(testInput) == 1227775554L)
    check(part2(testInput) == 4174379265L)

    val input = readInputAsString(2025, 2, "input")
        .split(",")

    part1(input).printlnPrefixed("part 1")
    part2(input).printlnPrefixed("part 2")
}

fun part2(input: List<String>): Long {
    val result = input.sumOf { line ->
        val first = line.substringBefore("-")
        val second = line.substringAfter("-")

        (first.toLong()..second.toLong())
            .filterNot { i -> i.toString().isValidStr2() }
            .sum()
    }

    return result
}


fun String.isValidStr2(): Boolean {
    val mid = length / 2
    val len = this.length

    val possiblyInvalids = (1..mid)
        .map { i -> this.substring(0, i) }
        .map { it.repeat(len / it.length) }

    return !possiblyInvalids.contains(this)
}

fun part1(input: List<String>): Long {
    val result = input.sumOf { line ->
        val first = line.substringBefore("-")
        val second = line.substringAfter("-")

        (first.toLong()..second.toLong())
            .filterNot { i -> i.toString().isValidStr() }
            .sum()
    }

    return result
}


fun String.isValidStr(): Boolean {
    val mid = length / 2

    val first = this.take(mid)
    val second = this.substring(mid)

    return (first != second)
}