package karamel.utils

import kotlin.test.Test
import kotlin.test.assertEquals

class AppendTrimmedTest {
    @Test
    fun test() {
        assertEquals("", buildString { appendTrimmed("") })
        assertEquals("", buildString { appendTrimmed(" ") })
        assertEquals("", buildString { appendTrimmed("  ") })
        assertEquals("1", buildString { appendTrimmed(" 1 ") })
        assertEquals("1 2", buildString { appendTrimmed(" 1 2 ") })
        assertEquals("1 2", buildString { appendTrimmed("     1 2    ") })

        assertEquals("hello", buildString { appendTrimmed(" hello") })
        assertEquals("world", buildString { appendTrimmed("world ") })

        assertEquals("Hello,  world!", buildString { appendTrimmed(" Hello,  world!") })
        assertEquals("Hello,  world!", buildString { appendTrimmed("Hello,  world! ") })
    }
}