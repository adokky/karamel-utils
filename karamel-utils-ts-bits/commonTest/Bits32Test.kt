package karamel.utils

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TypeSafeBitsTest {
    private object TestSchema: AutoBitDescriptors() {
        val b1 = uniqueBit()
        val b2 = uniqueBit()
        val b3 = uniqueBit()
        val b4 = uniqueBit()
        val b5 = uniqueBit()
    }

    @Test
    fun combining_bits() {
        var bits = TestSchema.b1 or TestSchema.b4

        assertTrue(TestSchema.b1 in bits)
        assertFalse(TestSchema.b2 in bits)
        assertFalse(TestSchema.b3 in bits)
        assertTrue(TestSchema.b4 in bits)
        assertFalse(TestSchema.b5 in bits)

        bits = bits or TestSchema.b5

        assertTrue(TestSchema.b1 in bits)
        assertFalse(TestSchema.b2 in bits)
        assertFalse(TestSchema.b3 in bits)
        assertTrue(TestSchema.b4 in bits)
        assertTrue(TestSchema.b5 in bits)
    }

    @Test
    fun removing_bits() {
        var bits = Bits32<TestSchema>(0b11111)
        for (index in TestSchema.getAll()) {
            assertTrue(bits[index], index.toString())
        }

        bits -= TestSchema.b3
        assertEquals(0b11011, bits.toInt())

        bits -= TestSchema.b1
        assertEquals(0b11010, bits.toInt())
    }

    @Test
    fun contains_multiple() {
        val bits = TestSchema.b1 or TestSchema.b4 or TestSchema.b5

        assertTrue(bits.containsAll(TestSchema.b1, TestSchema.b4, TestSchema.b5))
        assertTrue(bits.containsAll(TestSchema.b1, TestSchema.b5))
        assertTrue(bits.contains(TestSchema.b5))

        assertFalse(bits.containsAll(TestSchema.b2, TestSchema.b4, TestSchema.b5))
        assertFalse(bits.containsAll(TestSchema.b2, TestSchema.b5))
        assertFalse(bits.contains(TestSchema.b2))
    }

    @Test
    fun contains_any() {
        val bits = TestSchema.b1 or TestSchema.b4 or TestSchema.b5

        assertTrue(bits.containsAny(TestSchema.b1, TestSchema.b4, TestSchema.b5))
        assertTrue(bits.containsAny(TestSchema.b2, TestSchema.b4, TestSchema.b5))
        assertTrue(bits.containsAny(TestSchema.b1, TestSchema.b5))
        assertTrue(bits.containsAny(TestSchema.b2, TestSchema.b5))

        assertFalse(bits.containsAny(TestSchema.b2, TestSchema.b3))
    }
}