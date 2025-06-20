@file:JvmName("RangeUtils")

package karamel.utils

import kotlin.jvm.JvmName

val IntRange.size: Int get() = endInclusive - start + 1

val LongRange.size: Long get() = endInclusive - start + 1