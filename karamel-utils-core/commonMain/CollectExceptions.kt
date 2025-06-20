@file:JvmMultifileClass
@file:JvmName("ExceptionUtils")

package karamel.utils

import kotlin.jvm.JvmMultifileClass
import kotlin.jvm.JvmName

class ExceptionCollector @PublishedApi internal constructor() {
    private var _caught: ArrayList<Throwable>? = null
    @PublishedApi
    internal val caught: List<Throwable> get() = _caught ?: emptyList()

    fun addException(e: Throwable) {
        (_caught ?: (ArrayList<Throwable>().also { _caught = it })).add(e)
    }

    inline fun <R> catch(body: () -> R): R? = try {
        body()
    } catch (e: Throwable) {
        e.throwIfUncatchable()
        addException(e)
        null
    }
}

class CatchResult @PublishedApi internal constructor(val exceptions: List<Throwable>) {
    fun throwCombined(): Unit = exceptions.throwCombined()
    override fun equals(other: Any?): Boolean = (other as? CatchResult)?.exceptions == exceptions
    override fun hashCode(): Int = exceptions.hashCode()
}

fun Collection<Throwable>.throwCombined() {
    when (size) {
        0 -> return
        1 -> throw first()
        else -> throw RuntimeException(joinToString("\n") { it.stackTraceToString() })
    }
}

/**
 * Utility to catch exception in multiple places and throw them all at once.
 *
 * For example, the code below:
 *
 *     val result = collectExceptions {
 *         catch { "not a number".toInt() }
 *         catch { error("catch me") }
 *     }
 *     result.throwCombined()
 *
 * will throw [RuntimeException] containing stacktraces from
 * both `NumberFormatException` and `IllegalStateException`.
 *
 * @see [throwCombined]
 */
inline fun collectExceptions(body: ExceptionCollector.() -> Unit): CatchResult =
    CatchResult(ExceptionCollector().apply(body).caught)

/**
 * A shortcut for:
 *
 *      collectExceptions { ... }.throwCombined()
 *
 * @see [throwCombined]
 * @see [collectExceptions]
 */
inline fun collectAndThrowExceptions(body: ExceptionCollector.() -> Unit): Unit =
    collectExceptions(body).throwCombined()