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
    println("passes: ${rotator.passes}")
    return rotator.passes
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
    var passes = 0

    fun right(n: Int) {
        println("=== pos: $pos right: $n")
        pos = (pos + n) % size
        println("new pos:                 $pos")

        val tmpPasses = (pos + n) / size
        val posAt0 = (if (pos == 0) 1 else 0)
        println("tmpPasses R:                  $tmpPasses + $posAt0")

        passes += (tmpPasses + posAt0)

    }

    fun left(n: Int) {
        println("=== pos: $pos left: $n")

//        val tmpPasses = (-pos + n) / (-size)
        val tmpPasses = if ((pos - n) >= 0) {
            println("---> --")
            0
        } else {
            if (pos != 0) {
                val tmp = (n - pos + size) / (size)
                println("----> $tmp")
                tmp
            } else {
                0
            }
        }

        pos = (pos - n + size) % size
        println("new pos:                 $pos")
        val posAt0 = (if (pos == 0) 1 else 0)
        println("tmpPasses L:                   $tmpPasses + $posAt0")

        passes += (tmpPasses + posAt0)
    }
}