package karamel.utils

import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicReference
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class ThreadLocalJvmTest {
    private val tl by threadLocal { Thread.currentThread().name }
    private val mutableThreadLocal = ThreadLocal { -1 }

    @Test
    fun test() {
        val THREADS = 5

        val threadNames = CopyOnWriteArrayList<String>()
        val countDownLatch = CountDownLatch(THREADS)
        val error = AtomicReference<Throwable>()

        val threads = (1..THREADS).map { index ->
            Thread( {
                threadNames.add(tl)

                var prev = -1
                repeat(100_000) {
                    assertEquals(prev, mutableThreadLocal.get())

                    mutableThreadLocal.set(index)
                    assertEquals(index, mutableThreadLocal.get())

                    prev = Random.nextInt(-100, -1)
                    mutableThreadLocal.set(prev)
                }
                mutableThreadLocal.set(index)

                countDownLatch.countDown()
            }, "TEST_THREAD_$index").also {
                it.uncaughtExceptionHandler = Thread.UncaughtExceptionHandler { _, ex ->
                    error.getAndSet(ex)
                    countDownLatch.countDown()
                }
                it.start()
            }
        }

        countDownLatch.await(1, TimeUnit.SECONDS)

        error.get()?.let { fail(it.stackTraceToString()) }

        assertEquals(threads.mapToSet { it.name }, threadNames.toSet())
        assertEquals(-1, mutableThreadLocal.get())
    }
}