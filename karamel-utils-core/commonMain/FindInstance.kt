@file:JvmMultifileClass
@file:JvmName("CollectionUtils")

package karamel.utils

import kotlin.jvm.JvmMultifileClass
import kotlin.jvm.JvmName
import kotlin.reflect.KClass

@Throws(NoSuchElementException::class)
inline fun <reified T: Any> Iterable<*>.firstInstanceOf(): T =
    firstInstanceOfOrNull(T::class) ?: instanceNotFound(T::class)

@Throws(NoSuchElementException::class)
inline fun <reified T: Any> List<*>.lastInstanceOf(): T =
    lastInstanceOfOrNull(T::class) ?: instanceNotFound(T::class)

@PublishedApi
internal fun instanceNotFound(clazz: KClass<*>): Nothing {
    throw NoSuchElementException("element of type ${clazz.readableName()} not found")
}

inline fun <reified T: Any> Iterable<*>.firstInstanceOfOrNull(): T? = firstInstanceOfOrNull(T::class)

inline fun <reified T: Any> List<*>.lastInstanceOfOrNull(): T? = lastInstanceOfOrNull(T::class)

fun <T: Any> Iterable<*>.firstInstanceOfOrNull(clazz: KClass<T>): T? {
    for (element in this) {
        @Suppress("UNCHECKED_CAST")
        if (clazz.isInstance(element)) return element as T
    }
    return null
}

fun <T: Any> List<*>.lastInstanceOfOrNull(clazz: KClass<T>): T? {
    for (i in indices.reversed()) {
        val element = this[i]
        @Suppress("UNCHECKED_CAST")
        if (clazz.isInstance(element)) return element as T
    }
    return null
}