package aoc2025.day04

import utils.printlnPrefixed
import utils.readInput

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
    return input.toPosArray()
        .listAccessible()
        .size
}

fun part2(input: List<String>): Int {
    val positions = input.toPosArray()
    var count = 0

    do {
        val list = positions.listAccessible()
            .also { it.forEach { p -> positions[p.x][p.y] = p.copy(c = '.') } }
        count += list.size
    } while (list.isNotEmpty())

    return count
}

private fun List<String>.toPosArray(): Array<Array<Pos>> =
    Array(size) { x ->
        Array(this[x].length) { y ->
            Pos(x, y, this[x][y])
        }
    }

fun Array<Array<Pos>>.asPointList() = toList().flatMap { it.toList() }

private fun Array<Array<Pos>>.listAccessible(): List<Pos> =
    asPointList()
        .filter { p -> p.c == '@' }
        .filter { p -> this.nrOfAdjacentRolls(p) < 4 }

fun Array<Array<Pos>>.draw() =
    this.joinToString(separator = "\n") { row ->
        row.joinToString("") { it.c.toString() }
    }

fun Array<Array<Pos>>.nrOfAdjacentRolls(p: Pos): Int {
    val iMin = (p.x - 1).coerceAtLeast(0)
    val iMax = (p.x + 1).coerceAtMost(lastIndex)
    val jMin = (p.y - 1).coerceAtLeast(0)
    val jMax = (p.y + 1).coerceAtMost(this[p.x].lastIndex)

    return (iMin..iMax).flatMap { i ->
        (jMin..jMax).map { j ->
            val point = this[i][j]
            if (point != p && point.c == '@') 1 else 0
        }
    }.sum()
}

data class Pos(val x: Int, val y: Int, val c: Char) {
    override fun toString(): String {
        return "($x, $y) $c"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        return if (other is Pos)
            (this.x == other.x && this.y == other.y)
        else false
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        result = 31 * result + c.hashCode()
        return result
    }
}