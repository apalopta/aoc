import utils.readInput
import utils.readInputAsString
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.text.split

class ResultsTest {
    @Test
    fun `check 2025 Day01`() {
        val input = readInput(2025, 1, "input")

        assertEquals(1048, aoc2025.day01.part1(input))
        assertEquals(6498, aoc2025.day01.part2(input))
    }

    @Test
    fun `check 2025 Day02`() {
        val input = readInputAsString(2025, 2, "input")
            .split(",")

        assertEquals(28846518423, aoc2025.day02.part1(input))
        assertEquals(31578210022, aoc2025.day02.part2(input))
    }

    @Test
    fun `check 2025 Day03`() {
        val input = readInput(2025, 3, "input")

        assertEquals(17278, aoc2025.day03.part1(input))
        assertEquals(171528556468625L, aoc2025.day03.part2(input))
    }

    @Test
    fun `check 2025 Day04`() {
        val input = readInput(2025, 4, "input")

        assertEquals(1320, aoc2025.day04.part1(input))
        assertEquals(8354, aoc2025.day04.part2(input))
    }

    @Test
    fun `check 2025 Day05`() {
        val input = readInput(2025, 5, "input")

        assertEquals(563, aoc2025.day05.part1(input))
        assertEquals(338693411431456L, aoc2025.day05.part2(input))
    }

    @Test
    fun `check 2025 Day06`() {
        val input = readInput(2025, 6, "input")

        assertEquals(5733696195703L, aoc2025.day06.part1(input))
        assertEquals(10951882745757L, aoc2025.day06.part2(input))
    }

    @Test
    fun `check 2025 Day07`() {
        val input = readInput(2025, 7, "input")

        assertEquals(1539L, aoc2025.day07.part1(input))
        assertEquals(6479180385864L, aoc2025.day07.part2(input))
    }
}