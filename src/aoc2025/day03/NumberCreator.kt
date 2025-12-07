package aoc2025.day03

class NumberCreator(val x: Int, val nums: String) {
    val arr = nums.toDigits()
    val res = Array(nums.length) { -1 }
    val occurrences = occurrences(arr.toList())

    fun max(): Long {
        require(arr.size >= x) { "not enough digits in input" }
        println(" ====  $nums")
        var startAtIndex = 0
        while (!res.enoughDigits(x)) {
            startAtIndex = fillInGap(startAtIndex)
        }
        return res.toValue()
    }

    fun fillInGap(startIndex: Int): Int {
        var newStart = startIndex

        for (n in (9 downTo 0)) {
            if (res.enoughDigits(x)) break

            val indicesOfN = occurrences[n]
                .indicesOfValues()
                .dropWhile { it < newStart }

            if (indicesOfN.isEmpty()) continue

            newStart = indicesOfN.first()

            for (i in indicesOfN.reversed()) {
                addToResult(n, i)
                if (res.enoughDigits(x)) break
            }
            if (res.enoughDigits(x)) break

            val indicesOfGapsAfterCurrentStart = res.indicesOfGapsAfter(newStart)
            newStart = res.toList()
                .dropLastWhile { it != -1 }
                .dropLastWhile { it == -1 }.lastIndex

            println("       " + displayGap(newStart))
            fillInGap(newStart)

//            if (indicesOfGapsAfterCurrentStart.isEmpty()) {
//                break
//            }
        }

        return newStart
    }

    fun displayGap(newStart: Int) = res.asString().toList().mapIndexed { index, ch -> if (index == newStart) "x" else ch }.joinToString("")

    private fun addToResult(n: Int, i: Int) {
        res[i] = occurrences[n][i]
        arr[i] = -1
        occurrences[n][i] = -1
        println(" res: '${res.asString()}'")

    }
}

fun occurrences(list: List<Int>): Array<Array<Int>> {
    val nums = Array(10) { i ->
        Array(list.size) { j -> if (list[j] == i) i else -1 }
    }
    nums.forEach { it.asString() }
    return nums
}


fun Array<Int>.toValue() = filter { it > -1 }.joinToString("").ifEmpty { "0" }.toLong()
fun Array<Int>.indicesOfValues() = indices.filter { this[it] != -1 }
fun Array<Int>.indicesOfGaps() = indices.filter { this[it] == -1 }
fun Array<Int>.enoughDigits(x: Int) = this.count { it != -1 } >= x
fun Array<Int>.indicesOfGapsAfter(i: Int) = this.indicesOfGaps().filter { it > i }

fun String.toDigits() = toList().map { it.digitToInt() }.toTypedArray()

fun Array<Int>.asString() = toList().joinToString(separator = "") { "$it".replace("-1", " ") }
