package karamel.utils

import dev.adokky.testEquality
import kotlin.test.Test
import kotlin.test.assertEquals

class PairsTest {
    @Test
    fun int_pair() {
        val ints = intArrayOf(Int.MIN_VALUE, -1, 0, 1, 255, Int.MAX_VALUE)
        for (i1 in ints)
        for (i2 in ints) {
            val pair = IntPair(i1, i2)
            assertEquals(i1, pair.int1)
            assertEquals(i2, pair.int2)

            assertEquals(IntPair(1, i2), pair.copy(int1 = 1))
            assertEquals(IntPair(i1, 1), pair.copy(int2 = 1))

            assertEquals(IntPair(-1, i2), pair.copy(int1 = -1))
            assertEquals(IntPair(i1, -1), pair.copy(int2 = -1))
            assertEquals(IntPair(-2, -3), pair.copy(int1 = -2, int2 = -3))

            assertEquals(i1, pair.component1())
            assertEquals(i2, pair.component2())
        }

        assertEquals("(456, -123)", IntPair(456, -123).toString())
    }

    @Test
    fun int_pair_auto_test() {
        testEquality(defaultGroupSize = 2) {
            group { IntPair(0, 0) }
            group { IntPair(0, 0xfff) }
            group { IntPair(12345, -12345) }
            group { IntPair(-12345, 12345) }
            group { IntPair(Int.MAX_VALUE, Int.MAX_VALUE) }
            group { IntPair(Int.MIN_VALUE, Int.MAX_VALUE) }
            group { IntPair(Int.MIN_VALUE, Int.MIN_VALUE) }
            checkToString = true
        }
    }

    @Test
    fun short_pair() {
        val shorts = shortArrayOf(Short.MIN_VALUE, -1, 0, 1, 255, Short.MAX_VALUE)
        for (i1 in shorts)
            for (i2 in shorts) {
                val pair = ShortPair(i1, i2)
                assertEquals(i1, pair.short1)
                assertEquals(i2, pair.short2)

                assertEquals(ShortPair(1, i2), pair.copy(short1 = 1))
                assertEquals(ShortPair(i1, 1), pair.copy(short2 = 1))

                assertEquals(ShortPair(-1, i2), pair.copy(short1 = -1))
                assertEquals(ShortPair(i1, -1), pair.copy(short2 = -1))
                assertEquals(ShortPair(-2, -3), pair.copy(short1 = -2, short2 = -3))

                assertEquals(i1, pair.component1())
                assertEquals(i2, pair.component2())
            }

        assertEquals("(456, -123)", ShortPair(456, -123).toString())
    }

    @Test
    fun short_pair_auto_test() {
        testEquality(defaultGroupSize = 2) {
            group { ShortPair(0, 0) }
            group { ShortPair(0, 0xfff) }
            group { ShortPair(12345, -12345) }
            group { ShortPair(-12345, 12345) }
            group { ShortPair(Short.MAX_VALUE, Short.MAX_VALUE) }
            group { ShortPair(Short.MIN_VALUE, Short.MAX_VALUE) }
            group { ShortPair(Short.MIN_VALUE, Short.MIN_VALUE) }
            checkToString = true
        }
    }

    @Suppress("OPT_IN_USAGE")
    @Test
    fun unsigned_short_pair() {
        val shorts = ushortArrayOf(UShort.MIN_VALUE, (-1).toUShort(), 0.toUShort(), 1.toUShort(), 255.toUShort(), UShort.MAX_VALUE)
        for (i1 in shorts)
            for (i2 in shorts) {
                val pair = ShortPair(i1, i2)
                assertEquals(i1, pair.ushort1)
                assertEquals(i2, pair.ushort2)
            }
    }
}