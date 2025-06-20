@file:JvmName("ArrayUtils")

package karamel.utils

import kotlin.jvm.JvmName

@Suppress("UNCHECKED_CAST")
inline fun <T, reified R> Collection<T>.mapToArray(body: (T) -> R): Array<R> {
    val result = arrayOfNulls<R>(size)
    val i = iterator()
    for (idx in indices)
        result[idx] = body(i.next())
    return result as Array<R>
}

@Suppress("UNCHECKED_CAST")
inline fun <T, reified R> Collection<T>.mapToArrayIndexed(body: (index: Int, T) -> R): Array<R> {
    val result = arrayOfNulls<R>(size)
    var i = 0
    val iter = iterator()
    for (idx in indices) {
        result[idx] = body(i, iter.next())
        i++
    }
    return result as Array<R>
}