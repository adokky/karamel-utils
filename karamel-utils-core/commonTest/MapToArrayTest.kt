package karamel.utils

import kotlin.test.Test
import kotlin.test.assertContentEquals

class MapToArrayTest {
    @Test
    fun simple_map() {
        assertContentEquals(arrayOf("1", "2", "3"), (1..3).toList().mapToArray { it.toString() })
        assertContentEquals(arrayOf<String>(), emptyList<Int>().mapToArray { it.toString() })
    }

    @Test
    fun indexed_map() {
        assertContentEquals(arrayOf("0 1", "1 2", "2 3"), (1..3).toList().mapToArrayIndexed { index, it -> "$index $it" })
        assertContentEquals(arrayOf<String>(), emptyList<Int>().mapToArrayIndexed { _, it -> it.toString() })
    }
}