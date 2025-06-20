package karamel.utils

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class StringBuilderTest {
    @Test
    fun remove_last() {
        assertEquals("hell", buildString {
            append("hello")
            removeLast(1)
        })

        assertFails {
            buildString {
                removeLast(1)
            }
        }

        assertEquals("he", buildString {
            append("hello")
            removeLast(3)
        })

        assertEquals("", buildString {
            append("hello")
            removeLast(5)
        })
    }

    @Test
    fun build_string() {
        val sb = StringBuilder()
        assertEquals(
            "a short string",
            buildString(sb) { append("a short").append(" string") }
        )
        assertEquals(
            "a longgggggg ssssssssssssssssssssssssstring",
            buildString(sb) { append("a longgggggg").append(" ssssssssssssssssssssssssstring") }
        )
        assertEquals(
            "abcDEF",
            buildString(sb) { append("abc").append("DEF") }
        )
        assertEquals(6, sb.length)
    }
}