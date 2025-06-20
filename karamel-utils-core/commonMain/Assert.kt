@file:JvmName("AssertUtils")

package karamel.utils

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.jvm.JvmName

expect val assertionsEnabled: Boolean

@OptIn(ExperimentalContracts::class)
inline fun assert(cond: () -> Boolean) {
    contract {
        callsInPlace(cond, InvocationKind.AT_MOST_ONCE)
    }

    if (assertionsEnabled) {
        if (!cond()) error("failed condition")
    }
}