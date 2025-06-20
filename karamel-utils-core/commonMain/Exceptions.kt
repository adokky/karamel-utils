@file:JvmMultifileClass
@file:JvmName("ExceptionUtils")

package karamel.utils

import kotlin.contracts.ExperimentalContracts
import kotlin.coroutines.cancellation.CancellationException
import kotlin.jvm.JvmMultifileClass
import kotlin.jvm.JvmName

@OptIn(ExperimentalContracts::class)
inline fun <reified E: Throwable> suppress(body: () -> Unit) {
    catch<E>(body)
}

@OptIn(ExperimentalContracts::class)
inline fun <reified E: Throwable> catch(body: () -> Unit): Throwable? {
    try {
        body()
        return null
    } catch (e: Throwable) {
        if (e !is E) throw e
        return e
    }
}

fun Throwable.initialCause(): Throwable {
    var ex = this
    while (ex.cause != null && ex.cause != ex)
        ex = ex.cause!!
    return ex
}

expect fun Throwable.isInterruptedException(): Boolean

fun Throwable.shouldAlwaysPropagate(): Boolean =
    this is CancellationException || isInterruptedException()

fun Throwable.throwIfUncatchable() {
    if (shouldAlwaysPropagate()) throw this
}

fun IndexOutOfBoundsException(index: Int, size: Int): IndexOutOfBoundsException =
    IndexOutOfBoundsException("index=$index, size=$size")

fun <T> Iterable<T>.safeIterate(body: (T) -> Unit) {
    val collected = collectExceptions {
        for (item in this@safeIterate) {
            try {
                body(item)
            } catch (e: Throwable) {
                e.throwIfUncatchable()
                addException(e)
            }
        }
    }

    collected.throwCombined()
}