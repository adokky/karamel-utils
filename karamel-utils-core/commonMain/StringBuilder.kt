@file:JvmName("StringBuilderUtils")

package karamel.utils

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.jvm.JvmName

@OptIn(ExperimentalContracts::class)
inline fun buildString(sb: StringBuilder, body: StringBuilder.() -> Unit): String {
    contract { callsInPlace(body, InvocationKind.EXACTLY_ONCE) }
    sb.setLength(0)
    return sb.apply(body).toString()
}

fun StringBuilder.removeLast(count: Int) {
    setLength(length - count)
}