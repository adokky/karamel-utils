package karamel.utils

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.fail

class MiscExceptionUtilsTest {
    private class TestException(message: String? = null): Exception(message)

    @Test
    fun catching() {
        assertNull(catch<Throwable> { })

        assertEquals("zzz", catch<RuntimeException> { throw RuntimeException("zzz", RuntimeException("xxx")) }?.message)

        assertEquals("zzz", catch<Exception> { throw RuntimeException("zzz", RuntimeException("xxx")) }?.message)

        assertFailsWith<RuntimeException> {
            suppress<TestException> { throw RuntimeException("should not be caught") }
        }
    }

    @Test
    fun suppressing() {
        assertFailsWith<TestException> {
            suppress<RuntimeException> { throw TestException("should not be suppressed") }
        }

        suppress<Exception> { throw TestException() }
        suppress<TestException> { throw TestException() }
    }

    @Test
    fun initial_case_is_self() {
        try {
            throw RuntimeException("zzz")
        } catch (e: RuntimeException) {
            assertEquals(e, e.initialCause())
        }
    }

    @Test
    fun initial_cause_deep() {
        try {
            throw RuntimeException("xxx", RuntimeException("yyy", RuntimeException("zzz")))
        } catch (e: RuntimeException) {
            assertEquals("zzz", e.initialCause().message)
        }
    }

    @Test
    fun safe_iterate_empty() {
        listOf<Int>().safeIterate { fail() }
    }

    @Test
    fun safe_iterate_multi() {
        val iterated = ArrayList<Int>()
        listOf(1, 2, 3).safeIterate { iterated.add(it) }
        assertEquals(listOf(1, 2, 3), iterated)
    }

    @Test
    fun safe_iterate_single() {
        val iterated = ArrayList<Int>()
        listOf(1).safeIterate { iterated.add(it) }
        assertEquals(listOf(1), iterated)
    }

    @Test
    fun safe_iterate_with_errors() {
        val iterated = ArrayList<Int>()

        assertFails {
            listOf(1, 2, 3).safeIterate {
                iterated.add(it)
                if (it % 2 == 0) fail()
            }
        }

        assertEquals(listOf(1, 2, 3), iterated)
    }
}