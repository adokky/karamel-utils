package karamel.utils

import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue
import dev.adokky.EqualsTester

class ExceptionCollectorTest {
    private class TestException(val id: Int): Exception(id.toString())

    @Test
    fun collect_no_exceptions() {
        val result = collectExceptions {
            assertEquals(10, catch { 5 + 5 })
        }
        assertEquals(emptyList(), result.exceptions)
    }

    @Test
    fun collect_10_exceptions() {
        val result = collectExceptions {
            repeat(10) { id ->
                assertNull(catch { throw TestException(id) })
            }
        }

        assertEquals(
            (0..9).toList(),
            result.exceptions.map { (it as TestException).id }
        )
    }

    @Test
    fun collector_ignores_exceptions_outside_of_catch() {
        assertEquals(42, assertFailsWith<TestException> {
            collectExceptions { throw TestException(42) }
        }.id)
    }

    @Test
    fun rethrow_single_exception() {
        val ex = TestException(11)
        val result = collectExceptions { catch { throw ex } }
        assertTrue(ex === assertFailsWith<TestException> {
            result.throwCombined()
        })
    }

    @Test
    fun rethrow_combined_exception() {
        val message = assertFailsWith<Exception> {
            collectAndThrowExceptions {
                catch { throw TestException(1234) }
                catch { throw TestException(5678) }
            }
        }.message
        assertNotNull(message)
        assertContains(message, "1234")
        assertContains(message, "5678")
    }

    @Test
    fun catch_result() {
        val r1 = collectExceptions {
            catch { throw TestException(1) }
            catch { throw TestException(2) }
        }

        val r2 = collectExceptions {}

        val r3 = collectExceptions {
            catch { throw TestException(3) }
        }

        EqualsTester().testGroups(
            listOf(r1, CatchResult(r1.exceptions), CatchResult(r1.exceptions)),
            listOf(CatchResult(r2.exceptions), r2, CatchResult(r2.exceptions)),
            listOf(r3, CatchResult(r3.exceptions)),
        )
    }
}