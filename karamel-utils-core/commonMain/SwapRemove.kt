@file:JvmMultifileClass
@file:JvmName("CollectionUtils")

package karamel.utils

import kotlin.jvm.JvmMultifileClass
import kotlin.jvm.JvmName

/**
 * Removes element at specified [index] by moving the last element at its place.
 * @return removed element
 */
fun <T> MutableList<T>.swapRemoveAt(index: Int): T {
    val removed = this[index]
    val last = removeLast()
    if (index < size) this[index] = last
    return removed
}

/** Removes every element satisfying [predicate] by moving the current last element at its place */
inline fun <T> MutableList<T>.swapRemoveAll(predicate: (T) -> Boolean) {
    var i = 0
    while (i < size) {
        if (predicate(get(i))) swapRemoveAt(i) else i++
    }
}

/**
 * Removes a single instance of the specified [element] (if it is present)
 * from this collection by moving the last element at its place,
 * @return
 * `true` if the element has been successfully removed;
 * `false` if it was not present in the collection
 */
fun <T> MutableList<T>.swapRemove(element: T): Boolean {
    val idx = indexOf(element)
    if (idx == -1) return false
    swapRemoveAt(idx)
    return true
}