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
//    check(part2(testInput) == 43)

    val input = readInput(2025, 4, "input")

    val res1 = part1(input)
    check(res1 == 1320)
    res1.printlnPrefixed("part 1")
//    part2(input).printlnPrefixed("part 2")
}

fun part1(input: List<String>): Int {
    val arr = Array(input.size) { x ->
        Array(input[x].length) { y -> Point(x, y, input[x][y]) }
    }

    replaceByXForAccessible(arr)
    val accessible = arr.toList().flatMap { it.toList() }.count { it.c == 'x' }

//    println(arr.draw())
    return accessible
        .also { println(it) }
}

private fun replaceByXForAccessible(
    arr: Array<Array<Point>>
) {
    val points: List<Point> = arr.toList().flatMap { it.toList() }
    points.filter { point ->
        point.countRollsAround(arr) < 4
    }.filter { p -> p.c == '@' }
        .also { it.forEach { p -> arr[p.x][p.y] = p.copy(c = 'x') } }
}

fun part2(input: List<String>): Int {
    return 0
}

fun Array<Array<Point>>.draw() =
    this.joinToString(separator = "\n") { row ->
        row.joinToString(" ") { it.c.toString() }
    }


fun Point.countRollsAround(input: Array<Array<Point>>): Int {
    var rolls = 0
    val minI = (x - 1).coerceAtLeast(0)
    val maxI = (x + 1).coerceAtMost(input.lastIndex)
    val minJ = (y - 1).coerceAtLeast(0)
    val maxJ = (y + 1).coerceAtMost(input[x].lastIndex)

    print("=> [${x}:${y}] (i:$minI->$maxI, j:$minJ->$maxJ) : ")
    for (i in minI..maxI) {
        for (j in minJ..maxJ) {
            val point = input[i][j]
            if ((!(point.x == this.x && point.y == this.y))
                && (point.c == '@')
            ) {
                print(" @ [$i:$j]")
                rolls++
            }
        }
    }
    println(" -> $rolls")
    return rolls
}