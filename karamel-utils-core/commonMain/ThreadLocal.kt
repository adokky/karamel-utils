package karamel.utils

import kotlin.jvm.JvmInline
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

expect class ThreadLocal<T: Any> {
    fun get(): T
    fun set(value: T?)
}

expect fun <T: Any> ThreadLocal(initialValue: () -> T): ThreadLocal<T>

@JvmInline
private value class ThreadLocalDelegate<T: Any>(val tl: ThreadLocal<T>): ReadOnlyProperty<Any?, T> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): T = tl.get()
}

fun <T: Any> threadLocal(initialValue: () -> T): ReadOnlyProperty<Any?, T> =
    ThreadLocalDelegate(ThreadLocal(initialValue))