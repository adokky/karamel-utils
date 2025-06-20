@file:JvmName("BitUtils")

package karamel.utils

import kotlin.jvm.JvmName

/**
 * Sets all bits outside specified range to zero.
 *
 * Range starts from *least* significant bit [start] and ends at *most* significant bit [end] (exclusive).
 */
fun Int.keepBits(start: Int, end: Int = 32): Int {
    val leftCut = 32 - end
    val rightCut = leftCut + start
    return ((this shl leftCut) ushr rightCut) shl start
}

/**
 * Sets all bits outside specified range to zero.
 *
 * Range starts from *least* significant bit [start] and ends at *most* significant bit [end] (exclusive).
 */
fun Long.keepBits(start: Int, end: Int = 64): Long {
    val leftCut = 64 - end
    val rightCut = leftCut + start
    return ((this shl leftCut) ushr rightCut) shl start
}

/**
 * Iterates all one bit positions in range starting from
 * *least* significant [start] to *most* significant [end] (exclusive).
 */
inline fun Int.forEachBit(
    start: Int = 0,
    end: Int = 32,
    body: (Int) -> Unit
) {
    var shift = start
    while (true) {
        shift += (this ushr shift).countTrailingZeroBits() + 1
        if (shift > end) break
        body(shift - 1)
    }
}

/**
 * Iterates all one bit positions in range starting from
 * *least* significant [start] to *most* significant [end] (exclusive).
 */
inline fun Long.forEachBit(
    start: Int = 0,
    end: Int = 64,
    body: (Int) -> Unit
) {
    var shift = start
    while (true) {
        shift += (this ushr shift).countTrailingZeroBits() + 1
        if (shift > end) break
        body(shift - 1)
    }
}

/**
 * Counts the number of set bits in the binary representation of this [Int] number
 * starting from less significant bit at [start] (inclusive) until more significant bit at [end] (exclusive).
 */
fun Int.countOneBits(start: Int, end: Int = 32): Int {
    val highMask = 0.inv() ushr (32 - end + start)
    return ((this ushr start) and highMask).countOneBits()
}

/**
 * Counts the number of set bits in the binary representation of this [Long] number
 * starting from the least significant bit at [start] (inclusive)
 * until the most significant bit at [end] (exclusive).
 */
fun Long.countOneBits(start: Int, end: Int = 64): Int {
    val highMask = 0L.inv() ushr (64 - end + start)
    return ((this ushr start) and highMask).countOneBits()
}

