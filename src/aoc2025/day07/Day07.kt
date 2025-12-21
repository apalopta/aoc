package aoc2025.day07

import utils.printlnPrefixed
import utils.readInput
import utils.toPosArray
import kotlin.collections.toList

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
        .also { it.printlnPrefixed("part 2") } // ~2.5 ms
}

fun part1(input: List<String>): Long {
    val arr = input.toPosArray()
    val start = arr[0].find { it.c == 'S' } ?: error("Can't find 'S' in input")
    var beams = listOf(start.y)
    var count = 0L

    for (i in (1..arr.lastIndex)) {
        val beamedSplitters = arr[i].filter { it.c == '^' }
            .map { it.y }
            .filter { it in beams }
        val nextBeams = beams.map { it }.toMutableList()

        if (beamedSplitters.isEmpty()) {
            beams = nextBeams
        } else {
            count += beamedSplitters.size

            beamedSplitters.forEach { y ->
                nextBeams.add(y - 1)
                nextBeams.add(y + 1)
            }

            beams = nextBeams
                .filterNot { it in beamedSplitters }
                .filter { it >= 0 && it <= arr[0].lastIndex }

            for (beam in beams) {
                arr[i][beam] = arr[i][beam].copy(c = '|')
            }
        }
    }

    return count
}


// Pascal!
fun part2(input: List<String>): Long {
    val arr = input.toPosArray()
    val start = arr[0].find { it.c == 'S' } ?: error("Can't find 'S' in input")
    var beams = listOf(start.y)
    var timelineCounters = Array(arr[0].size) { 0L }.toMutableList().apply {
        this[start.y] = 1
    }.toList()

    for (i in (1..arr.lastIndex)) {
        val beamedSplitters = arr[i].filter { it.c == '^' }
            .map { it.y }
            .filter { it in beams }
        val nextBeams = beams.map { it }.toMutableList()
        val nextTimelineCounters = timelineCounters.map { it }.toMutableList()

        if (beamedSplitters.isEmpty()) {
            beams = nextBeams
            timelineCounters = nextTimelineCounters
            continue
        } else {
            nextBeams
                .splitBeamsAt(beamedSplitters, timelineCounters.lastIndex)
                .forEach { beam ->
                    nextTimelineCounters[beam] = beam.countTimelines(beamedSplitters, timelineCounters)
                }

            timelineCounters = nextTimelineCounters
            // no timeline below a splitter
            beamedSplitters.forEach { y -> nextTimelineCounters[y] = 0 }
        }

        beams = nextBeams.filterNot { it in beamedSplitters }
        for (beam in beams) {
            arr[i][beam] = arr[i][beam].copy(c = '|')
        }
    }

    return timelineCounters.sum()
}

private fun MutableList<Int>.splitBeamsAt(
    beamedSplitters: List<Int>,
    lastIndex: Int,
): List<Int> {
    beamedSplitters.forEach { y ->
        add(y - 1)
        add(y + 1)
        remove(y)
    }

    return this.toSet().filter { it in 0..lastIndex }.sorted()
}

private fun Int.countTimelines(
    beamedSplitters: List<Int>,
    timelineCounters: List<Long>
): Long {
    val s1 = if ((this - 1) >= 0 && (this - 1 in beamedSplitters)) timelineCounters[this - 1] else 0
    val s2 = timelineCounters[this]
    val s3 = if ((this + 1) <= timelineCounters.lastIndex && (this + 1) in beamedSplitters) timelineCounters[this + 1] else 0

    return s1 + s2 + s3
}
