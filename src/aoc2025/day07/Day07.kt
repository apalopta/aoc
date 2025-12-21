package aoc2025.day07

import utils.draw
import utils.printlnPrefixed
import utils.readInput
import utils.toPosArray
import java.lang.Thread.sleep

fun main() {
    val testInput = readInput(2025, 7, "test")

    check(part1(testInput) == 21L)
    check(part2(testInput) == 0L)

    val input = readInput(2025, 7, "input")

    part1(input)
        .also { check(it == 1539L) }
        .also { it.printlnPrefixed("part 1") }
//    part2(input)
//        .also { check(it == ) }
//        .also { it.printlnPrefixed("part 2") } // merged to 88 ranges / 0.2ms
}

fun part1(input: List<String>): Long {
    val arr = input.toPosArray()
    val start = arr[0].find { it.c == 'S' } ?: error("Can't find 'S' in input")
    var beams = listOf(start.y)
    var count = 0L
//    println("------")
//    println(beams)

    for (i in (1..arr.lastIndex)) {
        val nextLinesBeams = beams.map { it }.toMutableList()
        val splitters = arr[i].filter { it.c == '^' }.map { it .y }

//        println("splitters: $splitters")
        val beamedSplitters = splitters.filter { it in beams }
        if (beamedSplitters.isEmpty()) {
            beams = nextLinesBeams
        } else {
            beamedSplitters.forEach { y ->
                nextLinesBeams.add(y - 1)
                nextLinesBeams.add(y + 1)
                count++
            }
            beams = nextLinesBeams
                .filterNot { it in beamedSplitters }
                .filter { it >= 0 && it <= arr[0].lastIndex }
        }
        for (beam in beams) {
            arr[i][beam] = arr[i][beam].copy(c = '|')
        }

//        println(arr.draw())
//        println()
//        sleep(300)
    }


    return count
}

fun part2(input: List<String>): Long {
    return 0L
}
