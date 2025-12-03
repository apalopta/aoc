package aoc2025.day01

import utils.readInput
import utils.printlnPrefixed

fun main() {
    val testInput = readInput(2025, 1, "test")

    check(part1(testInput) == 3)
    check(part2(testInput) == 6)

    val input = readInput(2025, 1, "input")

    part1(input).printlnPrefixed("part1") // 1048
    part2(input).printlnPrefixed("part2") // 6528 too high
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
    println("passes: ${rotator.totalZeros}")
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
        val p = pos

        totalZeros += zeros(p, n)
        pos = (p + n + size) % size

        return this
    }

    fun left(n: Int) : XRotator {
        val p = (size - pos) % size

        totalZeros += zeros(p, n)
        //pos = (size - (p + n % size)) % size
        pos = ((pos - n) + size) % size

        return this
    }

    fun zeros(p: Int, n: Int) : Int {
        return if ((p + n) < size)
            0
        else
            (p + n) / size
    }
}