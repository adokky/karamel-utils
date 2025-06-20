package karamel.utils

import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class BitsTest {
    private fun test(maxIndex: Int, zero: BitArray<*>) {
        assertEquals(maxIndex, zero.capacity)

        var flags = zero
        for (i in 0 ..< maxIndex) {
            assertFalse(flags.get(i))
        }

        for (i in 0 ..< maxIndex) {
            flags = flags.plus(i)
            for (j in 0 ..< maxIndex) {
                assertEquals(j <= i, flags.get(j))
            }
        }

        flags = zero
        val expected = BooleanArray(maxIndex)
        repeat(100) {
            val index = Random.nextInt(maxIndex)

            val bit = Random.nextBoolean()
            flags = flags.set(index, bit)
            expected[index] = bit

            for (i in 0 ..< maxIndex) {
                assertEquals(expected[i], flags.get(i))
            }
        }
    }

    @Test
    fun bitFlags32() = test(32, Bits32<Unit>())

    @Test
    fun bitFlags64() = test(64, Bits64<Unit>())

    @Test
    fun string_representation_32() {
        assertEquals(
            "10001000000000000001001101011101",
            Bits32<Unit>(0b10001000000000000001001101011101u).toString()
        )
        assertEquals(
            "00000000000000000000000000000000",
            Bits32<Unit>().toString()
        )
    }

    @Test
    fun string_representation_64() {
        assertEquals(
            "1000000000000000000000000001000000000000001000000000001101011101",
            Bits64<Unit>(0b1000000000000000000000000001000000000000001000000000001101011101u).toString()
        )
        assertEquals(
            "0000000000000000000000000000000000000000000000000000000000000000",
            Bits64<Unit>().toString()
        )
    }

    @Test
    fun logical_or() {
        assertEquals(
            Bits64<Unit>(0b1000000000010000001000001000000011100000000011000000101101100L),
            Bits64<Unit>(0b0000000000010000001000000000000001000000000011000000100101000L) +
                    Bits64<Unit>(0b1000000000000000001000001000000010100000000001000000001000100L)
        )

        assertEquals(
            Bits32<Unit>(0b1011100000000011000000101101100),
            Bits32<Unit>(0b0001000000000011000000100101000) +
                    Bits32<Unit>(0b1010100000000001000000001000100)
        )
    }

    @Test
    fun logical_and() {
        assertEquals(
            Bits64<Unit>(0b1000000000000000001000001000000000000000000001000000001001000L),
            Bits64<Unit>(0b1100000100010000001000001000000001000000000011000000101101000L) and
                    Bits64<Unit>(0b1000100000000100001000001001000010100001110001000000001001100L)
        )

        assertEquals(
            Bits32<Unit>(0b1000000000001000000001001000),
            Bits32<Unit>(0b1000000000011000000101101000) and
                    Bits32<Unit>(0b1100001110001000000001001100)
        )
    }

    @Test
    fun add_bits_32() {
        var bits = Bits32<Unit>()

        val set = intArrayOf(4, 31)
        for (b in set) bits += b

        for(i in 0..31) {
            val bitPresent = i in set
            assertEquals(bitPresent, bits[i])
            assertEquals(bitPresent, i in bits)
        }
    }

    @Test
    fun add_bits_64() {
        var bits = Bits64<Unit>()

        val set = intArrayOf(4, 32, 63)
        for (b in set) bits += b

        for(i in 0..63) {
            val bitPresent = i in set
            assertEquals(bitPresent, bits[i])
            assertEquals(bitPresent, i in bits)
        }
    }

    @Test
    fun remove_bits_32() {
        var bits = Bits32<Unit>(0.inv())

        val set = intArrayOf(4, 31)
        for (b in set) bits -= b

        for(i in 0..31) {
            val bitPresent = i !in set
            assertEquals(bitPresent, bits[i])
            assertEquals(bitPresent, i in bits)
        }
    }

    @Test
    fun remove_bits_64() {
        var bits = Bits64<Unit>(0L.inv())

        val set = intArrayOf(4, 32, 63)
        for (b in set) bits -= b

        for(i in 0..31) {
            val bitPresent = i !in set
            assertEquals(bitPresent, bits[i])
            assertEquals(bitPresent, i in bits)
        }
    }

    @Test
    fun bitCounting64() {
        val bits = 0b10001L
        assertEquals(2, bits.countOneBits(0))
        assertEquals(1, bits.countOneBits(0, 1))
        assertEquals(1, bits.countOneBits(0, 2))
        assertEquals(1, bits.countOneBits(0, 3))
        assertEquals(1, bits.countOneBits(1))
        assertEquals(1, bits.countOneBits(1, 10))
        assertEquals(1, bits.countOneBits(4))
        assertEquals(0, bits.countOneBits(5))
        assertEquals(0, bits.countOneBits(1, 4))
    }

    @Test
    fun bitCounting32() {
        val bits = 0b10001
        assertEquals(2, bits.countOneBits(0))
        assertEquals(1, bits.countOneBits(0, 1))
        assertEquals(1, bits.countOneBits(0, 2))
        assertEquals(1, bits.countOneBits(0, 3))
        assertEquals(1, bits.countOneBits(1))
        assertEquals(1, bits.countOneBits(1, 10))
        assertEquals(1, bits.countOneBits(4))
        assertEquals(0, bits.countOneBits(5))
        assertEquals(0, bits.countOneBits(1, 4))
    }

    @Test
    fun bits_and_bool_32() {
        for (bits in intArrayOf(0, 1, 0b10001, 0b10100110101011100000, 1 shl 30, 0.inv())) {
            assertEquals(0,    Bits32<Unit>(bits).and(false).toInt())
            assertEquals(bits, Bits32<Unit>(bits).and(true ).toInt())
        }
    }

    @Test
    fun bits_and_bool_64() {
        for (bits in longArrayOf(0, 1, 0b10001, 0b10100110101011100000, 1L shl 62, 0L.inv())) {
            assertEquals(0,    Bits64<Unit>(bits).and(false).toLong())
            assertEquals(bits, Bits64<Unit>(bits).and(true ).toLong())
        }
    }
}