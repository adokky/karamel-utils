package karamel.utils

import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

class BitsTest {
    @Test
    fun count_bits_64() {
        assertEquals(3, 0b10000000001000011000111L.countOneBits(start = 3, end = 14))
        assertEquals(0, 0L.countOneBits(start = 0, end = 64))
        assertEquals(1, (1L shl 63).countOneBits(start = 63, end = 64))
        assertEquals(0, (1L shl 63).countOneBits(start = 62, end = 63))
        assertEquals(7, ((1L shl 63) or 0b11100001101011).countOneBits(start = 1, end = 30))
    }

    @Test
    fun count_bits_32() {
        assertEquals(3, 0b10000000001000011000111.countOneBits(start = 3, end = 14))
        assertEquals(0, 0.countOneBits(start = 0, end = 32))
        assertEquals(1, (1 shl 63).countOneBits(start = 31, end = 32))
        assertEquals(7, ((1 shl 63) or 0b11100001101011).countOneBits(start = 1, end = 30))
    }

    @Test
    fun fixture_32() {
        assertEquals(listOf(), 0.bitList())
        assertEquals(listOf(0), 1.bitList())
        assertEquals(listOf(3), 0b1000.bitList())
        assertEquals(listOf(0, 2), 0b101.bitList())
        assertEquals(
            listOf(1, 2, 3, 6, 8, 10, 13),
            0b10010101001110.bitList()
        )
    }

    @Test
    fun fixture_64() {
        assertEquals(listOf(), 0L.bitList())
        assertEquals(listOf(0), 1L.bitList())
        assertEquals(listOf(3), 0b1000L.bitList())
        assertEquals(listOf(0, 2), 0b101L.bitList())
        assertEquals(
            listOf(1, 2, 3, 6, 8, 10, 13, 31, 33, 63),
            (0b10010101001110L or (1L shl 31) or (1L shl 33) or (1L shl 63)).bitList()
        )
    }

    @Test
    fun random_bits_32() {
        iteratingBitsTest(
            bits = 0,
            size = 32,
            setBit = { bits, index, value -> if (value) bits or (1 shl index) else bits and (1 shl index).inv() },
            getBits = { it.bitList() }
        )
    }

    @Test
    fun random_bits_64() {
        iteratingBitsTest(
            bits = 0L,
            size = 64,
            setBit = { bits, index, value -> if (value) bits or (1L shl index) else bits and (1L shl index).inv() },
            getBits = { it.bitList() }
        )
    }

    private inline fun <T> iteratingBitsTest(
        bits: T,
        size: Int,
        setBit: (bits: T, index: Int, value: Boolean) -> T,
        getBits: (T) -> List<Int>
    ) {
        val expectedIndices = HashSet<Int>(size)
        var bits = bits

        repeat(1000) {
            val index = Random.nextInt(size)

            val bit = Random.nextBoolean()
            bits = setBit(bits, index, bit)
            if (bit) {
                expectedIndices += index
            } else {
                expectedIndices -= index
            }

            assertEquals(expectedIndices, getBits(bits).toSet())
        }
    }

    private fun Int.bitList(): List<Int> {
        val l1 = buildList {
            forEachBit { add(it) }
        }
        val l2 = buildList {
            forEachBit(start = 0, end = 11) { add(it) }
            forEachBit(start = 11, end = 27) { add(it) }
            forEachBit(start = 27, end = 32) { add(it) }
        }
        assertEquals(l1, l2)
        return l2
    }

    private fun Long.bitList(): List<Int> {
        val l1 = buildList {
            forEachBit { add(it) }
        }
        val l2 = buildList {
            forEachBit(start = 0, end = 11) { add(it) }
            forEachBit(start = 11, end = 49) { add(it) }
            forEachBit(start = 49, end = 64) { add(it) }
        }
        assertEquals(l1, l2)
        return l2
    }

    @Test
    fun keep_32() {
        assertEquals(
            0b10010101001110,
            0b10010101001110.keepBits(start = 0)
        )
        assertEquals(
            0b10010101001100,
            0b10010101001110.keepBits(start = 2)
        )
        assertEquals(
            0b10010100000000,
            0b10010101001110.keepBits(start = 8, end = 14)
        )
        assertEquals(
            0b00000000000100,
            0b10010101001110.keepBits(start = 2, end = 3)
        )
        assertEquals(
            (1 shl 30) or 0b10,
            ((0b11 shl 30) or 0b11).keepBits(start = 1, end = 31)
        )
    }

    @Test
    fun keep_64() {
        assertEquals(
            0b10010101001110L,
            0b10010101001110L.keepBits(start = 0)
        )
        assertEquals(
            0b10010101001100L,
            0b10010101001110L.keepBits(start = 2)
        )
        assertEquals(
            0b10010100000000L,
            (0b10010101001110L or (1L shl 63)).keepBits(start = 8, end = 14)
        )
        assertEquals(
            0b00000000000100L,
            0b10010101001110L.keepBits(start = 2, end = 3)
        )
        assertEquals(
            (1L shl 62) or 0b10L,
            ((0b11L shl 62) or 0b11L).keepBits(start = 1, end = 63)
        )
    }
}