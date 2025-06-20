package karamel.utils

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class FilterTest {
    @Test
    fun filter_map() {
        fun test(iterable: Iterable<Int>) {
            assertEquals(iterable.toList(), iterable.filterMap { it })

            assertEquals(
                iterable.filter { it % 3 == 0 }.map { it.toString() },
                iterable.filterMap { if (it % 3 != 0) null else it.toString() }
            )

            assertEquals(emptyList(), iterable.filterMap { null })
        }

        val source = (1..10).toList()
        test(source)
        test(source.asSequence().asIterable())
    }

    @Test
    fun filter_to_set() {
        fun Iterable<Int>.test(filterToSet: Iterable<Int>.((Int) -> Boolean) -> Set<Int>) {
            assertEquals(
                toSet(),
                this.filterToSet { true }
            )

            assertEquals(
                filter { it % 3 == 0 }.toSet(),
                this.filterToSet { it % 3 == 0 }
            )

            assertEquals(
                emptySet(),
                this.filterToSet { false }
            )
        }

        for (iterable in arrayOf<Iterable<Int>>(
            (1..10).toList(),
            (1..10).toList().asSequence().asIterable(),
        )) {
            iterable.test { filterToSet(it) }
            iterable.test { filterToHashSet(it) }
            iterable.test { filterToLinkedHashSet(it) }
        }
    }

    @Test
    fun filter_to_array_list() {
        val list: Iterable<Int> = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9)

        val filtered = list.filterToArrayList { it % 3 == 0 }

        assertFalse(filtered === list)
        assertEquals(
            list.filterTo(ArrayList()) { it % 3 == 0 },
            filtered
        )

        assertEquals(
            list,
            list.filterToArrayList { true }
        )

        assertEquals(
            emptyList(),
            list.filterToArrayList { false }
        )
    }
}