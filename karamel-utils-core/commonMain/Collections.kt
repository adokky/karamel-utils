@file:JvmMultifileClass
@file:JvmName("CollectionUtils")

package karamel.utils

import kotlin.jvm.JvmMultifileClass
import kotlin.jvm.JvmName

@PublishedApi
internal fun Iterable<*>.approxSize(): Int = if (this is Collection) size else 16

inline fun <T, R> Iterable<T>.mapToArrayList(body: (T) -> R): ArrayList<R> =
    mapTo(ArrayList<R>(approxSize()), body)

inline fun <T, R> Iterable<T>.mapToSet(body: (T) -> R): Set<R> = mapToLinkedHashSet(body)

inline fun <T, R> Iterable<T>.mapToHashSet(body: (T) -> R): HashSet<R> =
    mapTo(HashSet(approxSize()), body)

inline fun <T, R> Iterable<T>.mapToLinkedHashSet(body: (T) -> R): LinkedHashSet<R> =
    mapTo(LinkedHashSet<R>(approxSize()), body)

inline fun <T: Any, R: Any> Iterable<T>.filterMap(body: (T) -> R?): ArrayList<R> =
    ArrayList<R>(approxSize()).also { result ->
        for (e in this) {
            result.add(body(e) ?: continue)
        }
    }

inline fun <T> Iterable<T>.filterToArrayList(body: (T) -> Boolean): ArrayList<T> = filterTo(ArrayList(), body)

inline fun <T> Iterable<T>.filterToSet(body: (T) -> Boolean): Set<T> = filterToLinkedHashSet(body)

inline fun <T> Iterable<T>.filterToHashSet(body: (T) -> Boolean): HashSet<T> = filterTo(HashSet(), body)

inline fun <T> Iterable<T>.filterToLinkedHashSet(body: (T) -> Boolean): LinkedHashSet<T> = filterTo(LinkedHashSet(), body)


fun MutableList<*>.removeNulls(): Boolean = removeAll { it == null }

fun <T> MutableList<T>.addIfNotExist(e: T): Boolean {
    if (e in this) return false
    return add(e)
}

inline fun <T> List<T>.indexOfFirst(start: Int = 0, end: Int = size, predicate: (T) -> Boolean): Int {
    for (i in start until end) {
        if (predicate(this[i])) return i
    }
    return -1
}

inline fun <T> List<T>.indexOfLast(start: Int = 0, end: Int = size, predicate: (T) -> Boolean): Int {
    for (i in end - 1 downTo start) {
        if (predicate(this[i])) return i
    }
    return -1
}