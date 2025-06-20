package karamel.utils

import kotlin.test.Test
import kotlin.test.assertEquals

class CommonTest {
    @Test
    fun min_max() {
        assertEquals(1 to 2, minMax(1, 2))
        assertEquals(1 to 2, minMax(2, 1))
    }

    @Test
    fun range_sizs() {
        assertEquals(42, (5..46).size)
        assertEquals(42L, (Int.MAX_VALUE..(Int.MAX_VALUE.toLong() + 41)).size)
    }

    @Test
    fun unsafe_cast() {
        assertEquals("3434", ("3434" as Any).unsafeCast<String>())
    }

    @Test
    fun bool_to_number() {
        assertEquals(0, false.toByte())
        assertEquals(0, false.toShort())
        assertEquals(0, false.toInt())
        assertEquals(0, false.toLong())

        assertEquals(1, true.toByte())
        assertEquals(1, true.toShort())
        assertEquals(1, true.toInt())
        assertEquals(1, true.toLong())
    }

    @Test
    fun number_to_bool() {
        fun test(expected: Boolean, num: Long) {
            assertEquals(expected, num.toByte().toBoolean())
            assertEquals(expected, num.toShort().toBoolean())
            assertEquals(expected, num.toInt().toBoolean())
            assertEquals(expected, num.toBoolean())
        }

        test(true, 1)
        test(true, 0.inv())
        test(true, 0b10)
        test(false, 0)
    }

    @Test
    fun as_long() {
        assertEquals(0L, 0.toByte().asLong())
        assertEquals(0L, 0.toShort().asLong())
        assertEquals(0L, 0.asLong())

        assertEquals(0xf9L, 0xf9.toByte().asLong())
        assertEquals(0xf9ffL, 0xf9ff.toShort().asLong())
        assertEquals(0.inv().toLong() shl 32 ushr 32, 0.inv().asLong())
    }

    @Test
    fun as_int() {
        assertEquals(0, 0.toByte().asInt())
        assertEquals(0, 0.toShort().asInt())

        assertEquals(0xf9, 0xf9.toByte().asInt())
        assertEquals(0xf9ff, 0xf9ff.toShort().asInt())
    }

    @Test
    fun as_short() {
        assertEquals(0, 0.toByte().asShort())
        assertEquals(0xf9, 0xf9.toByte().asShort())
    }
}