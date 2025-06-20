package karamel.utils

import kotlin.jvm.JvmInline
import kotlin.jvm.JvmName
import kotlin.jvm.JvmOverloads

@JvmInline
value class Bits32<T: Any> @JvmOverloads constructor(private val asInt: Int = 0): BitArray<T> {
    override fun get(index: Int): Boolean = (asInt and (1 shl index)).toBoolean()

    override fun plus(index: Int): Bits32<T> = Bits32(asInt or (1 shl index))
    override fun minus(index: Int): Bits32<T> = Bits32(asInt and ((1 shl index).inv()))

    operator fun plus(other: Bits32<T>): Bits32<T> = this or other
    operator fun minus(other: Bits32<T>): Bits32<T> = Bits32(asInt and other.asInt.inv())

    fun toInt(): Int = asInt
    fun toBoolean(): Boolean = asInt != 0
    override fun toLong(): Long = asInt.toLong() and 0xFF_FF_FF_FFL
    override fun toString(): String = bitsToString(32, ::get)
    override val bitCount: Int get() = asInt.countOneBits()
    override val capacity: Int get() = 32

    infix fun or( other: Bits32<T>): Bits32<T> = Bits32(asInt or other.asInt)
    infix fun and(other: Bits32<T>): Bits32<T> = Bits32(asInt and other.asInt)
    infix fun xor(other: Bits32<T>): Bits32<T> = Bits32(asInt xor other.asInt)

    fun inv(): Bits32<T> = Bits32(asInt.inv())

    operator fun contains(index: Int): Boolean = get(index)
    operator fun contains(bits: Bits32<T>): Boolean =
        containsAll(bits)

    fun containsAll(other: Bits32<T>): Boolean =
        (asInt and other.asInt) == other.asInt
    fun containsAll(other0: Bits32<T>, other1: Bits32<T>): Boolean =
        containsAll(other0 + other1)
    fun containsAll(other0: Bits32<T>, other1: Bits32<T>, other2: Bits32<T>): Boolean =
        containsAll(other0 + other1 + other2)

    fun containsAny(other: Bits32<T>): Boolean =
        (asInt and other.asInt) != 0
    fun containsAny(other0: Bits32<T>, other1: Bits32<T>): Boolean =
        containsAny(other0 + other1)
    fun containsAny(other0: Bits32<T>, other1: Bits32<T>, other2: Bits32<T>): Boolean =
        containsAny(other0 + other1 + other2)

    fun containsNone(other: Bits32<T>): Boolean =
        (asInt and other.asInt) == 0
    fun containsNone(other0: Bits32<T>, other1: Bits32<T>): Boolean =
        containsNone(other0 + other1)
    fun containsNone(other0: Bits32<T>, other1: Bits32<T>, other2: Bits32<T>): Boolean =
        containsNone(other0 + other1 + other2)

    fun containsAllExcept(all: Bits32<T>, none: Bits32<T>): Boolean =
        (((this and all) xor all) or (this and none)).asInt == 0

    infix fun and(mask: Boolean): Bits32<T> = Bits32(asInt * mask.toInt())
}

@JvmName("BitArray32FromInt")
fun <T: Any> Bits32(ulong: UInt): Bits32<T> = Bits32<T>(ulong.toInt())

fun <T : Any> Iterable<Bits32<T>>.logicalOr(): Bits32<T> {
    var result = Bits32<T>(0)
    for (bits in this) { result += bits }
    return result
}

fun <T : Any> Iterable<Bits32<T>>.logicalAnd(): Bits32<T> {
    var result = Bits32<T>(0)
    for (bits in this) { result = result and bits }
    return result
}