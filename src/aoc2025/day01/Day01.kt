package aoc2025.day01

import utils.readInput
import utils.printlnPrefixed

fun main() {
    val testInput = readInput(2025, 1, "test")

    check(part1(testInput) == 3)
    check(part2(testInput) == 6)

    val input = readInput(2025, 1, "input")

    part1(input).printlnPrefixed("part1") // 1048
    part2(input).printlnPrefixed("part2") // 6528 too high, 6469
}

fun part1(input: List<String>): Int {
    val rotator = Rotator()
    return input.map { s ->
        val dir = s.take(1)
        val n = s.substring(1).toInt()
        if (dir == "L") {
            rotator.left(n)
        } else {
            rotator.right(n)
        }
    }.count { it == 0 }
}

fun part2(input: List<String>): Int {
    val rotator = XRotator()
    input.map { s ->
        val dir = s.take(1)
        val n = s.substring(1).toInt()
        if (dir == "L") {
            rotator.left(n)
        } else {
            rotator.right(n)
        }
    }
    return rotator.totalZeros
}

class Rotator(val size: Int = 100, var pos: Int = 50) {
    fun right(n: Int): Int {
        pos = (pos + n) % size
        return pos
    }

    fun left(n: Int): Int {
        pos = ((pos - n) + size) % size
        return pos
    }
}

class XRotator(val size: Int = 100, var pos: Int = 50) {
    var totalZeros = 0

    fun right(n: Int): XRotator {
        var i = n
        while (i > 0) {
            pos += 1
            pos %= size
            if (pos == 0) {
                totalZeros++
            }
            i--
        }

        return this
    }

    fun left(n: Int) : XRotator {
        var i = n
        while (i > 0) {
            pos -= 1
            if (pos == 0) {
                totalZeros++
            }
            if (pos < 0) pos += size
            i--
        }

        return this
    }
}