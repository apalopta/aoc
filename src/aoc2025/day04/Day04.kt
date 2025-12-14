package aoc2025.day04

import utils.readInput
import utils.printlnPrefixed

fun main() {
    val testInput = readInput(2025, 4, "test")

    check(part1(testInput) == 13)
//    check(part2(testInput) == 0)

    val input = readInput(2025, 4, "input")

    part1(input).printlnPrefixed("part 1")
//    part2(input).printlnPrefixed("part 2")
}

fun part1(input: List<String>): Int {
    val arr = Array(input.size) { x ->
        Array(input[x].length) { y -> Point(x, y, input[x][y]) }
    }

    val points = arr.flatten().filter { (x, y, _) ->
        x == 0 || x == input.lastIndex || y == 1 || y == input[0].lastIndex
    }

    var count = 0
    for (point in points) {
        if (countRollsAround(arr, point) < 4) {
            count++
        }
    }
    println("count: $count")
    return count
}

fun part2(input: List<String>): Int {
    return 0
}

data class Point(val x: Int, val y: Int, val c: Char)

fun countRollsAround(input: Array<Array<Point>>, p: Point): Int {
    var rolls = 0
    val minI = (p.x - 1).coerceAtLeast(0)
    val maxI = (p.x + 1).coerceAtMost(input.lastIndex)
    val minJ = (p.y - 1).coerceAtLeast(0)
    val maxJ = (p.y + 1).coerceAtMost(input[p.x].lastIndex)

    print("=> [${p.x}:${p.y}] : ")
    for (i in minI..maxI) {
        for (j in minJ..maxJ) {
            if ((!(i == p.x && j == p.y))
                && (p.c == '@')
            ) {
                print(" @ [$i:$j]")
                rolls++
            }
        }
    }
    println(" -> $rolls")
    return rolls
}