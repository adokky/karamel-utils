package karamel.utils

import kotlin.jvm.JvmInline
import kotlin.jvm.JvmName
import kotlin.jvm.JvmOverloads

@JvmInline
value class IntPair(val asLong: Long) {
    constructor(int1: Int, int2: Int): this((int1.toLong() shl 32) or int2.asLong())

    val int1: Int get() = (asLong ushr 32).toInt()
    val int2: Int get() = asLong.toInt()

    @JvmOverloads
    fun copy(int1: Int = this.int1, int2: Int = this.int2): IntPair = IntPair(int1, int2)

    operator fun component1(): Int = int1
    operator fun component2(): Int = int2
    override fun toString(): String = "($int1, $int2)"
}

@JvmInline
value class ShortPair(val asInt: Int) {
    constructor(short1: Int,   short2: Int): this((short1 shl 16) or (short2 and 0xFF_FF))
    constructor(short1: Short, short2: Short): this(short1.toInt(), short2.asInt())

    val short1: Short get() = (asInt ushr 16).toShort()
    val short2: Short get() = asInt.toShort()

    val shortAsInt1: Int get() = asInt ushr 16
    val shortAsInt2: Int get() = asInt and 0xffff

    val ushort1: UShort get() = (asInt ushr 16).toUShort()
    val ushort2: UShort get() = asInt.toUShort()

    @JvmOverloads
    fun copy(short1: Short = this.short1, short2: Short = this.short2): ShortPair = ShortPair(short1, short2)

    operator fun component1(): Short = short1
    operator fun component2(): Short = short2
    override fun toString(): String = "($short1, $short2)"
}

@JvmName("UShortPair")
fun ShortPair(ushort1: UShort, ushort2: UShort): ShortPair = ShortPair(ushort1.toShort(), ushort2.toShort())

@JvmOverloads
@JvmName("unsignedCopy")
fun ShortPair.copy(short1: UShort = this.ushort1, short2: UShort = this.ushort2): ShortPair = ShortPair(short1, short2)