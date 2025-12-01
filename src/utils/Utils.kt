package utils

import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.text.format

fun Int.format02(): String = "%02d".format(this)

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/${name.lowercase()}/$name.txt").readText().trim().lines()
fun readInputAsString(name: String) = Path("src/${name.lowercase()}/$name.txt").readText().trim()

fun readInput(year: Int, day: Int, name: String) = Path("src/aoc$year/day${day.format02()}/$name.txt").readText().trim().lines()
fun readInputAsString(year: Int, day: Int, name: String) = Path("src/aoc$year/day${day.format02()}/$name.txt").readText().trim()

fun readTestInput(path: String) = Path("src/$path.txt").readText().trim().lines()
fun readTestInputAsString(path: String) = Path("src/$path.txt").readText().trim()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)
