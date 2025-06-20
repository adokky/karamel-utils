package karamel.utils

import kotlin.test.Test
import kotlin.test.assertEquals

class UnsignedConversionTest {
    @Test
    fun as_long() {
        assertEquals(1L, 1L.toByte().asLong())
        assertEquals(0x7fL, 0x7fL.toByte().asLong())
        assertEquals(0xffL, 0xffL.toByte().asLong())

        assertEquals(1L, 1L.toShort().asLong())
        assertEquals(0x7f_ffL, 0x7f_ffL.toShort().asLong())
        assertEquals(0xff_ffL, 0xff_ffL.toShort().asLong())

        assertEquals(1L, 1L.toInt().asLong())
        assertEquals(0x7f_ff_ff_ffL, 0x7f_ff_ff_ffL.toInt().asLong())
        assertEquals(0xff_ff_ff_ffL, 0xff_ff_ff_ffL.toInt().asLong())
    }

    @Test
    fun as_int() {
        assertEquals(1, 1.toByte().asInt())
        assertEquals(0x7f, 0x7f.toByte().asInt())
        assertEquals(0xff, 0xff.toByte().asInt())

        assertEquals(1, 1.toShort().asInt())
        assertEquals(0x7f_ff, 0x7f_ff.toShort().asInt())
        assertEquals(0xff_ff, 0xff_ff.toShort().asInt())
    }

    @Test
    fun as_short() {
        assertEquals(1.toShort(), 1.toByte().asShort())
        assertEquals(0x7f.toShort(), 0x7f.toByte().asShort())
        assertEquals(0xff.toShort(), 0xff.toByte().asShort())
    }
}