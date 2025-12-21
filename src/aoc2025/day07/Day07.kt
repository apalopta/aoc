package aoc2025.day07

import utils.printlnPrefixed
import utils.readInput
import utils.toPosArray

fun main() {
    val testInput = readInput(2025, 7, "test")

    check(part1(testInput) == 21L)
    check(part2(testInput) == 40L)

    val input = readInput(2025, 7, "input")

    part1(input)
        .also { check(it == 1539L) }
        .also { it.printlnPrefixed("part 1") }
    part2(input)
        .also { check(it == 6479180385864L) }
        .also { it.printlnPrefixed("part 2") } // merged to 88 ranges / 0.2ms
}

fun part1(input: List<String>): Long {
    val arr = input.toPosArray()
    val start = arr[0].find { it.c == 'S' } ?: error("Can't find 'S' in input")
    var beams = listOf(start.y)
    var count = 0L

    for (i in (1..arr.lastIndex)) {
        val nextLinesBeams = beams.map { it }.toMutableList()
        val splitters = arr[i].filter { it.c == '^' }.map { it.y }

        val beamedSplitters = splitters.filter { it in beams }
        if (beamedSplitters.isEmpty()) {
            beams = nextLinesBeams
        } else {
            count += beamedSplitters.size
            beamedSplitters.forEach { y ->
                nextLinesBeams.add(y - 1)
                nextLinesBeams.add(y + 1)
            }
            beams = nextLinesBeams
                .filterNot { it in beamedSplitters }
                .filter { it >= 0 && it <= arr[0].lastIndex }
        }
        for (beam in beams) {
            arr[i][beam] = arr[i][beam].copy(c = '|')
        }
    }

    return count
}

fun part2(input: List<String>): Long {
    val arr = input.toPosArray()
    val start = arr[0].find { it.c == 'S' } ?: error("Can't find 'S' in input")
    var beams = listOf(start.y)
    var pLine = Array(arr[0].size) { 0L }.toMutableList().apply {
        this[start.y] = 1
    }
    var count = 0L

    for (i in (1..arr.lastIndex)) {
//        println("--- i: $i")
        val nextLinesBeams = beams.map { it }.toMutableList()
        val nextPLine = pLine.map { it }.toMutableList()

        val splitters = arr[i].filter { it.c == '^' }.map { it.y }

        val beamedSplitters = splitters.filter { it in beams }
        if (beamedSplitters.isEmpty()) {
            beams = nextLinesBeams
            pLine = nextPLine
            continue
        } else {
            count += beamedSplitters.size

            beamedSplitters.forEach { y ->
                nextLinesBeams.add(y - 1)
                nextLinesBeams.add(y + 1)
                nextLinesBeams.remove(y)
            }
            val myBeams = nextLinesBeams
                .filter { it >= 0 && it <= arr[0].lastIndex }

//            println("pLine: ${pLine.joinToString(" ")}")
            for (beam in myBeams.toSet().sorted()) {
//                println("beam: $beam")
                val s1 = if ((beam - 1) >= 0 && (beam - 1 in beamedSplitters)) pLine[beam - 1] else 0
                val s2 = pLine[beam]
                val s3 = if ((beam + 1) <= arr[0].lastIndex && (beam + 1) in beamedSplitters) pLine[beam + 1] else 0
//                println("s1: $s1, s2: $s2, s3: $s3")

                nextPLine[beam] = s1 + s2 + s3
                pLine = nextPLine
            }
            beamedSplitters.forEach { y -> nextPLine[y] = 0 }
//            println("nextPLine: ${nextPLine.joinToString(" ")}")
//            println("pLine: ${pLine.joinToString(" ")}")
        }
        beams = nextLinesBeams.filterNot { it in beamedSplitters }
        for (beam in beams) {
            if (beam !in beamedSplitters)
                arr[i][beam] = arr[i][beam].copy(c = '|')
        }

        println(pLine.joinToString(" "))

    }
    return pLine.sum()
        .also(::println)
}
