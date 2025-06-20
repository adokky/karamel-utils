package karamel.utils

import dev.adokky.testEquality
import kotlin.test.Test
import kotlin.test.assertEquals

class BoxTest {
    @Test
    fun equality_auto_test() = testEquality {
        group { Box(1) }
        group { Box("1") }
        group { Box(1.01) }
    }

    @Test
    fun to_string() = assertEquals("hello", Box("hello").toString())
}