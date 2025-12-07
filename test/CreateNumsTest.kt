import aoc2025.day03.NumberCreator
import utils.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

class CreateNumsTest {

    @Test
    fun createNums() {
        assertEquals(100L, NumberCreator(3, "1000").max())
        assertEquals(101L, NumberCreator(3, "1001").max())
        assertEquals(111L, NumberCreator(3, "1011").max())
        assertEquals(111L, NumberCreator(3, "10011").max())
        assertEquals(121L, NumberCreator(3, "10021").max())
        assertEquals(231L, NumberCreator(3, "12031").max())
        assertEquals(443L, NumberCreator(3, "340343").max())
        assertEquals(987654321111L, NumberCreator(12, "987654321111111").max())
        assertEquals(811111111119L, NumberCreator(12, "811111111111119").max())
        assertEquals(434234234278L, NumberCreator(12, "234234234234278").max())
        assertEquals(888911112111L, NumberCreator(12, "818181911112111").max())
        assertEquals(923171228333L, NumberCreator(12, "7462676262233227844662416692223171228333").max())
        assertEquals(877313272223L, NumberCreator(12, "4222222222246232512522472234136222233232233242221251223222231322425321322223228227322237122313272223").max())

    }

    @Test
    fun part2() {
        val input = readInput(2025, 3, "input")
        assertEquals(171528556468625L, aoc2025.day03.part2(input).toLong())
    }
}