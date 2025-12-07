package aoc2025.day03

import utils.readInput
import utils.printlnPrefixed
import java.math.BigInteger

fun main() {
    val testInput = readInput(2025, 3, "test")

    check(part1(testInput) == 357)
    check(part2(testInput) == BigInteger.valueOf(3121910778619L))

    val input = readInput(2025, 3, "input")

    part1(input).printlnPrefixed("part 1") // 17278
    part2(input).printlnPrefixed("part 2") // 171528556468625
}

fun part1(input: List<String>): Int {
    return input.sumOf { line ->
        val list = line.map { char -> char.digitToInt() }
        (9 downTo 0).maxOf { d ->
            val i = list.indexOf(d)
            if (i != -1 && i < list.lastIndex) {
                val m = list.subList(i + 1, list.size).max()
                "$d$m".toInt()
            } else {
                "0".toInt()
            }
        }
    }
}

fun part2(input: List<String>): BigInteger {
    val values = input.map { line ->
        NumberCreator(12, line).max()
            .let { BigInteger.valueOf(it) }
    }
    return values.sumOf { it }
}