package karamel.utils

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertIs

class BitDescriptorsTest {
    @Suppress("unused")
    private object RegularSchema: BitDescriptors() {
        val b1 = uniqueBit(1)
        val b2 = uniqueBit(3)
        val b3 = uniqueBit(5)
    }

    @Suppress("unused")
    private object SchemaWithConflict: BitDescriptors() {
        val b1 = uniqueBit(0)
        val b2 = uniqueBit(1)
        val b3 = uniqueBit(2)
        val b4 = uniqueBit(1)
    }

    @Suppress("unused")
    private object AutoSchema: AutoBitDescriptors() {
        val b1 = uniqueBit()
        val b2 = uniqueBit()
        val b3 = uniqueBit(4)
        val b4 = uniqueBit()
    }

    private object InvalidBitIndex1: BitDescriptors() {
        val b1 = uniqueBit(-1)
    }

    private object InvalidBitIndex2: BitDescriptors() {
        val b1 = uniqueBit(33)
    }

    @Test
    fun conflict() {
        val ex = assertFails { SchemaWithConflict.b4 }.initialCause()
        assertIs<IllegalArgumentException>(ex)
        assertEquals("bit index=1 already set", ex.message)
    }

    @Test
    fun list_of_all_bits() {
        assertEquals(listOf(1, 3, 5), RegularSchema.getAll().toList())
    }

    @Test
    fun auto_schema() {
        assertEquals(listOf(0, 1, 4, 5), AutoSchema.getAll().toList())
    }

    @Test
    fun bit_index_not_in_range() {
        assertIs<IllegalArgumentException>(assertFails { InvalidBitIndex1.b1 }.initialCause())
        assertIs<IllegalArgumentException>(assertFails { InvalidBitIndex2.b1 }.initialCause())
    }
}