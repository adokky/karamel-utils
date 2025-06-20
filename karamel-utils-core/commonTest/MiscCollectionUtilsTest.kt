package karamel.utils

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MiscCollectionUtilsTest {
    @Test
    fun add_if_not_exist() {
        val list = arrayListOf<Int>()

        assertTrue(list.addIfNotExist(1))
        assertEquals(listOf(1), list)

        assertFalse(list.addIfNotExist(1))
        assertEquals(listOf(1), list)

        assertTrue(list.addIfNotExist(2))
        assertEquals(listOf(1, 2), list)

        assertTrue(list.addIfNotExist(3))
        assertEquals(listOf(1, 2, 3), list)

        assertFalse(list.addIfNotExist(2))
        assertEquals(listOf(1, 2, 3), list)

        assertFalse(list.addIfNotExist(3))
        assertEquals(listOf(1, 2, 3), list)
    }

    @Test
    fun remove_nulls() {
        val list = arrayListOf<Int?>()

        assertFalse(list.removeNulls())

        list.add(1)
        assertFalse(list.removeNulls())

        list.addAll(listOf(null, 2, null, null, 3, null))
        assertTrue(list.removeNulls())
        assertEquals(listOf<Int?>(1, 2, 3), list)
    }
}