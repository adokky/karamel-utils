package karamel.utils

import kotlin.jvm.JvmInline
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Multiplatform thread-local storage.
 *
 * ## Platform Implementation Details:
 *
 * ### JVM:
 * - Uses `java.lang.ThreadLocal` under the hood
 * - Each thread has its own copy of the variable
 * - Values are strongly referenced and may cause memory leaks if not cleaned up
 *
 * ### Native:
 * - Uses `kotlin.native.concurrent.ThreadLocal`
 * - Each worker (thread) has its own copy of the variable
 * - Memory management follows Kotlin/Native rules
 *
 * ### JavaScript:
 * - Since JS is single-threaded, behaves like a simple variable holder
 * - No actual thread-local behavior since there are no threads
 *
 * ## Usage Example:
 * ```
 * val threadLocalValue by threadLocal { mutableListOf<String>() }
 *
 * // Each thread will have its own MutableList instance
 * threadLocalValue.add("thread-specific value")
 * ```
 */
expect class ThreadLocal<T: Any> {
    /**
     * Returns the current thread's value of this thread-local variable.
     * If the variable has no value for the current thread, it is initialized with
     * the result of calling the [ThreadLocal] factory function.
     */
    fun get(): T

    /**
     * Sets the current thread's value of this thread-local variable.
     */
    fun set(value: T?)
}

/**
 * Creates a new [ThreadLocal] instance with the specified initial value provider.
 *
 * @param T The type of the thread-local value (must be non-nullable)
 * @param initialValue A function that provides the initial value for each thread
 */
expect fun <T: Any> ThreadLocal(initialValue: () -> T): ThreadLocal<T>

@JvmInline
private value class ThreadLocalDelegate<T: Any>(val tl: ThreadLocal<T>): ReadOnlyProperty<Any?, T> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): T = tl.get()
}

/**
 * Creates a property delegate that uses [ThreadLocal] under the hood. Example:
 * ```
 * val list by threadLocal { mutableListOf<String>() }
 * ```
 * @param initialValue A function that provides the initial value for each thread
 */
fun <T: Any> threadLocal(initialValue: () -> T): ReadOnlyProperty<Any?, T> =
    ThreadLocalDelegate(ThreadLocal(initialValue))