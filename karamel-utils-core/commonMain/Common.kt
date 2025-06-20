@file:JvmName("CommonUtils")

package karamel.utils

import kotlin.jvm.JvmName

@Suppress("UNCHECKED_CAST")
fun <T> Any?.unsafeCast(): T = this as T

fun <T: Comparable<T>> minMax(e1: T, e2: T): Pair<T, T> {
    var min = e1
    var max = e2

    if (e1 > e2) {
        min = e2
        max = e1
    }

    return Pair(min, max)
}

expect class Box<T>(value: T) {
    val value: T
}

fun Boolean.toByte(): Byte = if (this) 1 else 0
fun Boolean.toShort(): Short = if (this) 1 else 0
fun Boolean.toInt(): Int = if (this) 1 else 0
fun Boolean.toLong(): Long = if (this) 1 else 0

fun Byte.toBoolean(): Boolean = this.toInt() != 0
fun Short.toBoolean(): Boolean = this.toInt() != 0
fun Int.toBoolean(): Boolean = this != 0
fun Long.toBoolean(): Boolean = this != 0L

fun Byte.asLong(): Long = toLong() and 0xffL
fun Byte.asInt(): Int = toInt() and 0xff
fun Byte.asShort(): Short = asInt().toShort()

fun Short.asLong(): Long = toLong() and 0xff_ffL
fun Short.asInt(): Int = toInt() and 0xff_ff

fun Int.asLong(): Long = toLong() and 0xff_ff_ff_ffL
