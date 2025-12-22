package aoc2025.day07

import utils.Pos
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
        .also { it.printlnPrefixed("part 1") }
    part2(input)
        .also { it.printlnPrefixed("part 2") } // ~2.5 ms
}

fun part1(input: List<String>): Long {
    val arr = input.toPosArray()
    val start = arr[0].find { it.c == 'S' } ?: error("Can't find 'S' in input")
    var beams = listOf(start.y)
    var count = 0L

    for (i in (1..arr.lastIndex)) {
        val beamedSplitters = arr[i].findBeamedSplitters(beams)

        if (beamedSplitters.isEmpty()) continue

        count += beamedSplitters.size

        beams = beams.toMutableList()
            .splitBeamsAt(beamedSplitters, arr[0].lastIndex)
            // next iteration's beams
            .filterNot { it in beamedSplitters }
            .also { beams -> arr[i].setBeams(beams) }
    }

    return count
}

fun part2(input: List<String>): Long {
    val arr = input.toPosArray()
    val start = arr[0].find { it.c == 'S' } ?: error("Can't find 'S' in input")
    var beams = listOf(start.y)
    // let's start with one timeline for the very first beam
    var timelineCounters = Array(arr[0].size) { 0L }.toMutableList().apply {
        this[start.y] = 1
    }.toList()

    for (i in (1..arr.lastIndex)) {
        val beamedSplitters = arr[i].findBeamedSplitters(beams)

        if (beamedSplitters.isEmpty()) continue

        beams = beams.toMutableList()
            .splitBeamsAt(beamedSplitters, timelineCounters.lastIndex)
            .also { beams ->
                timelineCounters = timelineCounters
                    .nextTimelineCounters(beams, beamedSplitters)
                    // no timeline below a splitter
                    .also { counters -> counters.resetAt(beamedSplitters) }
            }
            // next iteration's beams
            .filterNot { it in beamedSplitters }
            .also { beams -> arr[i].setBeams(beams) }
    }

    return timelineCounters.sum()
}

private fun List<Long>.nextTimelineCounters(
    beams: List<Int>,
    beamedSplitters: List<Int>
): MutableList<Long> = this.toMutableList().apply {
    beams.forEach { beam ->
        this[beam] = beam.countTimelines(beamedSplitters, this)
    }
}

private fun Array<Pos>.setBeams(beams: List<Int>) =
    beams.forEach { beam ->
        this[beam] = this[beam].copy(c = '|')
    }

private fun MutableList<Long>.resetAt(beamedSplitters: List<Int>) =
    beamedSplitters.forEach { y -> this[y] = 0 }

private fun Array<Pos>.findBeamedSplitters(beams: List<Int>): List<Int> =
    filter { it.c == '^' }
        .map { it.y }
        .filter { it in beams }

private fun MutableList<Int>.splitBeamsAt(
    beamedSplitters: List<Int>,
    lastPossibleBeam: Int
): List<Int> =
    this.apply {
        beamedSplitters.forEach { y ->
            add(y - 1)
            add(y + 1)
            remove(y)
        }
    }.toSet().filter { it in 0..lastPossibleBeam }.sorted()

private fun Int.countTimelines(
    beamedSplitters: List<Int>,
    counters: List<Long>
): Long {
    val left = if ((this - 1) >= 0 && (this - 1 in beamedSplitters)) counters[this - 1] else 0
    val middle = counters[this]
    val right = if ((this + 1) <= counters.lastIndex && (this + 1) in beamedSplitters) counters[this + 1] else 0

    return left + middle + right
}
