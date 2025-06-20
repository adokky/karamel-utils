package karamel.utils

import kotlin.test.Test
import kotlin.test.assertEquals

class SplitIterateTest {
    private fun String.fragments(separator: Char, start: Int = 0) = buildList<String> {
        splitIterate(separator, start) { start, end ->
            add(substring(start, end))
        }
    }

    @Test
    fun empty() {
        assertEquals(listOf(""), "".fragments('/'))
        assertEquals(listOf(""), " ".fragments('/', start = 1))
    }

    @Test
    fun single_separator() {
        assertEquals(listOf("", ""), "/".fragments('/'))
        assertEquals(listOf("", ""), " //".fragments('/', start = 2))
    }

    @Test
    fun separator_sequence() {
        assertEquals(listOf("", "", ""), "//".fragments('/'))
        assertEquals(listOf("", "", "", ""), "///".fragments('/'))
    }

    @Test
    fun pattern_with_leading_separator() {
        assertEquals(listOf("", "hello"), "Xhello".fragments('X'))
    }

    @Test
    fun pattern_with_tail_separator() {
        assertEquals(listOf("hello", ""), "helloZ".fragments('Z'))
    }

    @Test
    fun random_patterns() {
        assertEquals(
            listOf("", "Just", "an", "example", "string"),
            " Just an example string".fragments(' ')
        )
    }
}