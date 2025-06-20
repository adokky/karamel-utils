package karamel.utils

import kotlin.test.Test
import kotlin.test.assertEquals

class IndexOfTest {
    @Test
    fun combinations() {
        for (list: List<Int> in listOf(
            emptyList(),
            listOf(2),
            listOf(2, 1, 3, 2, 1)
        ))
        for (n in 1..3)
        for (start in list.indices)
        for (end in (start+1)..list.size) {
            assertEquals(
                list.subList(start, end).indexOf(n).let { if (it >= 0) it + start else -1 },
                list.indexOfFirst(start = start, end = end) { it == n }
            )

            assertEquals(-1, list.indexOfFirst(start = start, end = end) { it == 123456 })

            assertEquals(
                list.subList(start, end).lastIndexOf(n).let { if (it >= 0) it + start else -1 },
                list.indexOfLast(start = start, end = end) { it == n }
            )

            assertEquals(-1, list.indexOfLast(start = start, end = end) { it == 123456 })
        }
    }
}