package aoc2025.day03

const val IS_DEBUG = false

fun debugPrintln(txt: String) {
    if (IS_DEBUG) {
        println(txt)
    }
}

class NumberCreator(val x: Int, val nums: String) {
    val source = nums.toDigits()
    val result = Array(nums.length) { -1 }
    // keep n in dedicated arrays - don't want to filter source each time
    val occurrences = occurrences(source.toList())

    fun done() = result.enoughDigits(x)

    fun maxOfNDigits(): Long {
        require(source.size >= x) { "not enough digits in input" }
        debugPrintln(" ====  $nums")
        fillTheGap(0, source.max())
        return result.toValue()
    }

    // fill the gap with x numbers starting with higher numbers
    // fill the most right gap before adding lower numbers in front of the
    //      (higher) number(s) before that gap
    fun fillTheGap(startIndex: Int, startWithNumber: Int): Int {
        var indexOfLastGap = startIndex

        // startWithNumber could be always 9, but we can save time here
        outer@for (d in (startWithNumber downTo 0)) {
            debugPrintln("\nn: $d ")
            val indicesOfN = occurrences[d]
                .indicesOfValues()
                .filter { it >= indexOfLastGap }

            if (indicesOfN.isEmpty()) continue

            for (i in indicesOfN.reversed()) {
                addToResult(d, i)
                if (done()) break@outer
            }

            indexOfLastGap = result.indexOfLastGap()
                .also { debugPrintln(" newStart: @index $it with number ${source.max()}") }
            fillTheGap(indexOfLastGap, source.max())

            if (done()) break@outer
        }

        return indexOfLastGap
    }

    private fun addToResult(n: Int, i: Int) {
        debugPrintln(" i: $i ".padEnd(7))
        check(occurrences[n][i] == source[i]) { "ooops" }
        result[i] = occurrences[n][i]
        source[i] = -1
        occurrences[n][i] = -1
        debugPrintln(" res: '${result.asString()}'")
    }
}

fun occurrences(list: List<Int>): Array<Array<Int>> {
    return Array(10) { i ->
        Array(list.size) { j -> if (list[j] == i) i else -1 }
    }
}


fun Array<Int>.toValue() = filter { it > -1 }.joinToString("").ifEmpty { "0" }.toLong()
fun Array<Int>.indicesOfValues() = indices.filter { this[it] != -1 }
fun Array<Int>.enoughDigits(x: Int) = this.count { it != -1 } >= x
fun Array<Int>.displayGap(newStart: Int) = asString().toList().mapIndexed { index, ch -> if (index == newStart) "x" else ch }.joinToString("")

fun String.toDigits() = toList().map { it.digitToInt() }.toTypedArray()

fun Array<Int>.asString() = toList().joinToString(separator = "") { "$it".replace("-1", " ") }

fun Array<Int>.indexOfLastGap(): Int {
    return toList()
        .dropLastWhile { it != -1 }
        .dropLastWhile { it == -1 }
        .lastIndex
        .let { if (it == -1) 0 else it + 1 }
}