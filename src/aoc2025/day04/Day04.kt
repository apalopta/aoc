package aoc2025.day04

import utils.printlnPrefixed
import utils.readInput

data class Point(val x: Int, val y: Int, val c: Char) {
    override fun toString(): String {
        return "($x, $y) $c"
    }
}

fun main() {
    val testInput = readInput(2025, 4, "test")

    check(part1(testInput) == 13)
    check(part2(testInput) == 43)

    val input = readInput(2025, 4, "input")

    part1(input)
        .also { check(it == 1320) }
        .also { it.printlnPrefixed("part 1") }
    part2(input)
        .also { check(it == 8354) }
        .also { it.printlnPrefixed("part 2") } // ~50-60ms
}

fun part1(input: List<String>): Int {
    return input.toPointArray().replaceAccessibleRollsByX().size
}

fun part2(input: List<String>): Int {
    val arr = input.toPointArray()
    var count = 0

    do {
        val list = arr.replaceAccessibleRollsByX()
        count += arr.asPointList().count { it.c == 'x' }
        list.forEach { p -> arr[p.x][p.y] = p.copy(c = '.') }
    } while (list.isNotEmpty())

    return count
}

private fun List<String>.toPointArray(): Array<Array<Point>> =
    Array(size) { x ->
        Array(this[x].length) { y -> Point(x, y, this[x][y]) }
    }

fun Array<Array<Point>>.asPointList() = toList().flatMap { it.toList() }

private fun Array<Array<Point>>.replaceAccessibleRollsByX(): List<Point> {
    return toList().flatMap { it.toList() }
        .filter { point -> point.countRollsAround(this) < 4 }
        .filter { p -> p.c == '@' }
        .also { it.forEach { p -> this[p.x][p.y] = p.copy(c = 'x') } }
}

fun Array<Array<Point>>.draw() =
    this.joinToString(separator = "\n") { row ->
        row.joinToString("") { it.c.toString() }
    }

fun Point.countRollsAround(input: Array<Array<Point>>): Int {
    val minI = (x - 1).coerceAtLeast(0)
    val maxI = (x + 1).coerceAtMost(input.lastIndex)
    val minJ = (y - 1).coerceAtLeast(0)
    val maxJ = (y + 1).coerceAtMost(input[x].lastIndex)

    var result = 0
    for (i in minI..maxI) {
        for (j in minJ..maxJ) {
            val point = input[i][j]
            if ((!(point.x == this.x && point.y == this.y))
                && (point.c == '@')
            ) {
                result++
            }
        }
    }

    return result
}