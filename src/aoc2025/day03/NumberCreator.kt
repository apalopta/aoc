package aoc2025.day03

class NumberCreator(val lengthOfSearchedNumber: Int, nums: String) {
    val source = nums.toDigits()
    val result = Array(nums.length) { -1 }

    fun maxOutOfNDigits(): Long {
        fillTheGap(0, source.max())
        return result.toValue()
    }

    fun fillTheGap(startIndex: Int, startWithDigit: Int) {
        outer@ for (d in (startWithDigit downTo 0)) {
            source.indicesOfDigitAfterIndex(startIndex, d).run {
                if (isEmpty()) continue
                for (i in reversed()) {
                    result[i] = source[i]
                    source[i] = -1
                    if (done()) break@outer
                }
            }
            result.indexOfLastGap().run {
                fillTheGap(this, source.toList().subList(this, source.lastIndex + 1).max())
                if (done()) break@outer
            }
        }
    }

    fun done() = result.enoughDigits(lengthOfSearchedNumber) || !source.hasValues()
}

fun Array<Int>.indexOfLastGap(): Int = toList()
    .dropLastWhile { it != -1 }
    .dropLastWhile { it == -1 }
    .lastIndex + 1 // if -1 it's 0 which is the fallback!

fun Array<Int>.toValue() = filter { it > -1 }.joinToString("").ifEmpty { "0" }.toLong()
fun Array<Int>.hasValues() = any { it != -1 }
fun Array<Int>.indicesOfValues(d: Int) = indices.filter { this[it] == d }
fun Array<Int>.indicesOfDigitAfterIndex(i: Int, d: Int) = indicesOfValues(d).filter { it >= i }
fun Array<Int>.enoughDigits(x: Int) = this.count { it != -1 } >= x

fun String.toDigits() = toList().map { it.digitToInt() }.toTypedArray()
//fun Array<Int>.asString() = toList().joinToString(separator = "") { "$it".replace("-1", " ") }