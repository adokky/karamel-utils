package karamel.utils

import kotlin.jvm.JvmInline
import kotlin.jvm.JvmName
import kotlin.jvm.JvmOverloads

@JvmInline
value class Bits64<T: Any> @JvmOverloads constructor(private val asLong: Long = 0): BitArray<T> {
    override fun get(index: Int): Boolean = (asLong and (1L shl index)).toBoolean()

    override fun plus(index: Int): Bits64<T> = Bits64(asLong or (1L shl index))
    override fun minus(index: Int): Bits64<T> = Bits64(asLong and ((1L shl index).inv()))

    operator fun plus(other: Bits64<T>): Bits64<T> = this or other
    operator fun minus(other: Bits64<T>): Bits64<T> = Bits64(asLong and other.asLong.inv())

    override fun toLong(): Long = asLong
    override fun toString(): String = bitsToString(64, ::get)
    override val bitCount: Int get() = asLong.countOneBits()
    override val capacity: Int get() = 64

    infix fun or( other: Bits64<T>): Bits64<T> = Bits64(asLong or other.asLong)
    infix fun and(other: Bits64<T>): Bits64<T> = Bits64(asLong and other.asLong)
    infix fun xor(other: Bits64<T>): Bits64<T> = Bits64(asLong xor other.asLong)

    fun inv(): Bits64<T> = Bits64(asLong.inv())

    operator fun contains(index: Int): Boolean = get(index)
    operator fun contains(other: Bits64<T>): Boolean =
        containsAll(other)

    fun containsAll(other: Bits64<T>): Boolean =
        (asLong and other.asLong) == other.asLong
    fun containsAll(other0: Bits64<T>, other1: Bits64<T>): Boolean =
        containsAll(other0 + other1)
    fun containsAll(other0: Bits64<T>, other1: Bits64<T>, other2: Bits64<T>): Boolean =
        containsAll(other0 + other1 + other2)

    fun containsAny(other: Bits64<T>): Boolean =
        (asLong and other.asLong) != 0L
    fun containsAny(other0: Bits64<T>, other1: Bits64<T>): Boolean =
        containsAny(other0 + other1)
    fun containsAny(other0: Bits64<T>, other1: Bits64<T>, other2: Bits64<T>): Boolean =
        containsAny(other0 + other1 + other2)

    fun containsNone(other: Bits64<T>): Boolean =
        (asLong and other.asLong) == 0L
    fun containsNone(other0: Bits64<T>, other1: Bits64<T>): Boolean =
        containsNone(other0 + other1)
    fun containsNone(other0: Bits64<T>, other1: Bits64<T>, other2: Bits64<T>): Boolean =
        containsNone(other0 + other1 + other2)

    fun containsAllExcept(all: Bits64<T>, none: Bits64<T>): Boolean =
        (((this and all) xor all) or (this and none)).asLong == 0L

    infix fun and(mask: Boolean): Bits64<T> = Bits64(asLong * mask.toLong())
}

@JvmName("BitArray64FromInt")
fun <T: Any> Bits64(ulong: ULong): Bits64<T> = Bits64<T>(ulong.toLong())