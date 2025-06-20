package karamel.utils

import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

class ThreadLocalTest {
    @Test
    fun simple() {
        val num = Random.nextInt(0, 100)
        val tl = ThreadLocal { num }
        assertEquals(num, tl.get())
        assertEquals(num, tl.get())
        tl.set(-42)
        assertEquals(-42, tl.get())
    }

    @Test
    fun delegate() {
        val b by threadLocal { "hello " + "world" }
        assertEquals("hello world", b)
        assertEquals("hello world", b)
    }
}