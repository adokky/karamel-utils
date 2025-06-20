package karamel.utils

import dev.adokky.EqualsTester
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class MapEntryTest {
    private val entryFactories = arrayOf<(Int, Int) -> Map.Entry<Int, Int>>(
        { k, v -> linkedMapOf(k to v).entries.first() },
        { k, v -> hashMapOf(k to v).entries.first() },
        { k, v -> MapEntry(k, v) },
    )

    @Test
    fun simple_equality_test() {
        for (platformEntry in entryFactories)
            for (i in arrayOf(1, 2))
                for (j in arrayOf(1, 2)) {
                    val e1 = MapEntry(i, j)
                    val e2 = platformEntry(j, i)
                    assertEquals(e1, e1)
                    assertEquals(i == j, e1 == e2)
                    assertEquals(i == j, e2 == e1)
                }

        for (createEntry in entryFactories) {
            assertEquals(MapEntry(0, 42), createEntry(0, 42))
            assertNotEquals(MapEntry(42, 0), createEntry(0, 42))
        }
    }

    @Test
    fun to_string() {
        assertEquals("(454, 787)", MapEntry(454, "787").toString())
        assertEquals("(, )", MapEntry("", "").toString())
        assertEquals("([1, 2, 3], 123)", MapEntry(listOf(1, 2, 3), 123).toString())
    }

    @Test
    fun entry_from_pair() {
        assertEquals(MapEntry(3547, 9881), MapEntry(3547 to 9881))
    }

    @Test
    fun auto_equality_test() {
        fun createGroup(id: Int): List<Map.Entry<Int, Int>> =
            (1..10).map { entryFactories.random().invoke(id, id * 3 + 11) }

        EqualsTester().testGroups(
            createGroup(0),
            createGroup(1),
            createGroup(2),
            createGroup(3),
            createGroup(4)
        )
    }
}