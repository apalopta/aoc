package aoc2025.day03

import utils.readInput
import utils.printlnPrefixed

fun main() {
    val testInput = readInput(2025, 3, "test")

    check(part1(testInput) == 357)
    check(part2(testInput) == 3121910778619L)

    val input = readInput(2025, 3, "input")

    part1(input).printlnPrefixed("part 1")
    part2(input).printlnPrefixed("part 2")
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
            .also(::println)
    }
}

fun part2(input: List<String>): Long {
    println("input: $input")
    val res = input.maxOf { line ->
        println("line: $line")
        val list = line.map { char -> char.digitToInt() }
        println("list: $list")
        (9 downTo 0).maxOf { d ->
            print("d: $d ")
            val i = list.indexOf(d)
            print("i: $i ")
            if (i != -1 && i < list.lastIndex) {
                val m = list.subList(i + 1, list.size).max()
                print("m: $m ")
                "$d$m".toLong().also(::println)
            } else {
                "0".toLong().also(::println)
            }
        }
    }

    return res
}

