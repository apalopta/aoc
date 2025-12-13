package aoc2025.day03

const val IS_DEBUG = false

fun debugPrintln(txt: String) {
    if (IS_DEBUG) {
        println(txt)
    }
}

class NumberCreator(val lengthOfSearchedNumber: Int, val nums: String) {
    val source = nums.toDigits()
    val result = Array(nums.length) { -1 }

    fun maxOutOfNDigits(): Long {
        require(source.size >= lengthOfSearchedNumber) { "not enough digits in input" }
        //debugPrintln(" ====  $nums")

        fillTheGap(0, source.max())

        return result.toValue()
    }

    // fill the gap with x numbers starting with higher numbers
    // fill the most right gap before adding lower numbers in front of the
    //      (higher) number(s) before that gap
    fun fillTheGap(startIndex: Int, startWithDigit: Int) {
        //debugPrintln(" next gap @index $startIndex, start with digit $startWithDigit")
        val indexOfLastGap = startIndex

        // startWithNumber could be always 9, but we can save time here
        outer@ for (d in (startWithDigit downTo 0)) {
            //debugPrintln("\nn: $d ")
            val indicesOfD = source.indicesOfDigitAfterIndex(d, indexOfLastGap)

            if (indicesOfD.isEmpty()) continue

            // fill the gap from the rear end
            for (i in indicesOfD.reversed()) {
                result[i] = source[i]
                source[i] = -1
                //debugPrintln("  res: '${result.asString()}'")
                if (done()) break@outer
            }

            // fill the new / next last gap
            result.indexOfLastGap().run {
                fillTheGap(this, source.toList().subList(this, source.lastIndex + 1).max())
            }

            if (done()) break@outer
        }
    }

    fun done() = result.enoughDigits(lengthOfSearchedNumber)
}

fun Array<Int>.indexOfLastGap(): Int {
    return toList()
        .dropLastWhile { it != -1 }
        .dropLastWhile { it == -1 }
        .lastIndex + 1 // if -1 it's 0 which is the fallback!
}

fun Array<Int>.toValue() = filter { it > -1 }.joinToString("").ifEmpty { "0" }.toLong()
fun Array<Int>.indicesOfValues(d: Int) = indices.filter { this[it] == d }
fun Array<Int>.indicesOfDigitAfterIndex(d: Int, i: Int) = indicesOfValues(d).filter { it >= i }
fun Array<Int>.enoughDigits(x: Int) = this.count { it != -1 } >= x

fun String.toDigits() = toList().map { it.digitToInt() }.toTypedArray()

fun Array<Int>.asString() = toList().joinToString(separator = "") { "$it".replace("-1", " ") }