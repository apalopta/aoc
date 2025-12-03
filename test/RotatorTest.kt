import aoc2025.day01.XRotator
import kotlin.test.Test
import kotlin.test.assertEquals

class RotatorTest {
    @Test
    fun testRotator() {
        val rotator = XRotator(10, 5)

        assertEquals(1, XRotator(10, 5).left(5).totalZeros)
        assertEquals(1, XRotator(10, 0).left(10).totalZeros)
        assertEquals(2, XRotator(10, 0).left(20).totalZeros)
        assertEquals(1, XRotator(10, 1).left(10).totalZeros)
        assertEquals(2, XRotator(10, 1).left(11).totalZeros)
    }
}