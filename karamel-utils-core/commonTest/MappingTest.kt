package karamel.utils

import kotlin.test.Test
import kotlin.test.assertEquals

class MappingTest {
    @Test
    fun map_to_list() {
        assertEquals(listOf(10, 20, 30), listOf(1, 2, 3).mapToArrayList { it * 10 })
        assertEquals(listOf(), listOf<Int>().mapToArrayList { it })
    }

    @Test
    fun map_to_set() {
        assertEquals(setOf(0, 1), listOf(1, 2, 3).mapToSet { it % 2 })
        assertEquals(emptySet(), listOf<Int>().mapToSet { it })
    }

    @Test
    fun map_to_hash_set() {
        assertEquals(setOf(0, 1), listOf(1, 2, 3).mapToHashSet { it % 2 })
        assertEquals(emptySet(), listOf<Int>().mapToHashSet { it })
    }

    @Test
    fun map_to_linked_set() {
        assertEquals(linkedSetOf(0, 1), listOf(1, 2, 3).mapToLinkedHashSet { it % 2 })
        assertEquals(emptySet(), listOf<Int>().mapToLinkedHashSet { it })
    }
}