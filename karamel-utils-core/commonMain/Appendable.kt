@file:JvmName("AppendableUtils")

package karamel.utils

import kotlin.jvm.JvmName

fun Appendable.appendTrimmed(string: CharSequence) {
    val start = string.indexOfFirst { !it.isWhitespace() }
    if (start == -1) return

    var end = start + 1
    for (i in string.lastIndex downTo start + 1) {
        if (!string[i].isWhitespace()) {
            end = i + 1
            break
        }
    }

    appendRange(string, start, end)
}

fun Appendable.appendIndented(value: String, spaces: Int = 4) {
    var newLineFound: Boolean
    var start = 0

    do {
        appendSpaces(spaces)

        val nl = value.indexOf('\n', startIndex = start)
        newLineFound = nl >= 0
        val end = if (newLineFound) nl + 1 else value.length

        append(value, start, end)
        start = end
    }
    while (end < value.length)

    if (newLineFound) appendSpaces(spaces)
}

fun Appendable.appendSpaces(spaces: Int) {
    repeat(spaces) { append(' ') }
}