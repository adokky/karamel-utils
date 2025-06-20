package karamel.utils

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class SwapRemoveTest {
    @Test
    fun swap_remove_at_index() {
        val list = arrayListOf(1, 2, 3)

        assertEquals(2, list.swapRemoveAt(1))
        assertEquals(listOf(1, 3), list)

        assertEquals(3, list.swapRemoveAt(1))
        assertEquals(listOf(1), list)

        assertEquals(1, list.swapRemoveAt(0))
        assertEquals(listOf(), list)

        assertFailsWith<IndexOutOfBoundsException> {
            list.swapRemoveAt(0)
        }
    }

    @Test
    fun swap_remove_all() {
        val list = (1..10).toMutableList()

        var expected = list.toSet()
        list.swapRemoveAll { false }
        assertEquals(expected, list.toSet())

        expected = list.filterToSet { it % 3 != 0 }
        list.swapRemoveAll { it % 3 == 0 }
        assertEquals(expected, list.toSet())

        expected = list.filterToSet { it % 2 != 0 }
        list.swapRemoveAll { it % 2 == 0 }
        assertEquals(expected, list.toSet())

        list.swapRemoveAll { true }
        assertEquals(emptyList(), list)

        // repeat with empty list
        list.swapRemoveAll { true }
        assertEquals(emptyList(), list)

        list.swapRemoveAll { false }
        assertEquals(emptyList(), list)
    }

    @Test
    fun swap_remove_by_value() {
        val list = (1..10).map { it * 10 }.toMutableList()

        fun remove(element: Int) {
            val expected = list.filterToSet { it != element }
            assertEquals(expected != list.toSet(), list.swapRemove(element))
            assertEquals(expected, list.toSet())
        }

        remove(30)
        remove(10)
        remove(100)
        remove(70)

        for (i in 1..10) {
            remove(i * 10)
        }

        remove(45454)
    }
}