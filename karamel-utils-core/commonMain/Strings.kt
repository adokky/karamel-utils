@file:JvmName("StringUtils")

package karamel.utils

import kotlin.jvm.JvmName

/**
 * Returns this string if it ends with [suffix], otherwise concatenates it with specified [suffix].
 */
fun String.withSuffix(suffix: String): String = if (endsWith(suffix)) this else (this + suffix)

/**
 * Returns this string if it starts with [prefix], otherwise concatenates specified [prefix] with this string.
 */
fun String.withPrefix(prefix: String): String = if (startsWith(prefix)) this else (prefix + this)

/**
 * Returns `true` if this char sequence contains at least one of the specified characters.
 *
 * @param ignoreCase `true` to ignore character case when comparing characters. By default `false`.
 */
fun String.containsAny(
    char0: Char,
    vararg chars: Char,
    startIndex: Int = 0,
    ignoreCase: Boolean = false
): Boolean {
    return indexOfAny(charArrayOf(char0) + chars, startIndex, ignoreCase) >= 0
}

/**
 * Replaces character at [index] with [newChar].
 */
fun String.replaceCharAt(index: Int, newChar: Char): String {
    if (this[0] == newChar) return this
    return buildString {
        val source = this@replaceCharAt
        append(source, 0, index)
        append(newChar)
        append(source, index + 1, source.length)
    }
}

/**
 * Same as [kotlin.text.indexOf] but able to set [endIndex].
 */
fun CharSequence.indexOf(char: Char, startIndex: Int, endIndex: Int): Int {
    for (i in startIndex until endIndex) {
        if (get(i) == char) return i
    }

    return -1
}

/**
 * Similar to [takeIf] but returns empty string instead of null.
 */
inline fun String.orEmptyIf   (body: () -> Boolean): String = if (body()) ""   else this

/**
 * Similar to [takeUnless] but returns empty string instead of null.
 */
inline fun String.orEmptyUnless(body: () -> Boolean): String = if (body()) this else ""

/**
 * Iterates over all substring position pairs (`start..<end`) around occurrences of the
 * specified [delimiter] in a string starting from [startIndex].
 *
 * Substring indices is a range from `start` to `end` (exclusive).
 */
inline fun CharSequence.splitIterate(
    delimiter: Char,
    startIndex: Int = 0,
    body: (start: Int, end: Int) -> Unit
) {
    var index = startIndex - 1
    while (index < length) {
        val start = index + 1
        index = indexOf(delimiter, start)
        if (index < 0) index = length
        body(start, index)
    }
}

operator fun String.times(count: Int): String {
    if (count == 0) return ""
    return buildString(count * length) {
        repeat(count) {
            append(this@times)
        }
    }
}