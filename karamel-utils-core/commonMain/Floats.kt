@file:JvmName("FloatUtils")

package karamel.utils

import kotlin.jvm.JvmName
import kotlin.math.absoluteValue

fun Float.nearlyEquals(other: Float, eps: Float = 0.000_01f): Boolean {
    return (other - this).absoluteValue <= eps
}

fun Double.nearlyEquals(other: Double, eps: Double = 0.000_001): Boolean {
    return (other - this).absoluteValue <= eps
}