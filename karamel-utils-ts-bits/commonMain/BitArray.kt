package karamel.utils

interface BitArray<T> {
    val capacity: Int
    val bitCount: Int

    operator fun get(index: Int): Boolean
    operator fun plus(index: Int): BitArray<T>
    operator fun minus(index: Int): BitArray<T>

    fun toLong(): Long
}

fun <T: BitArray<*>> T.set(index: Int, value: Boolean): T {
    @Suppress("UNCHECKED_CAST")
    return (if (value) plus(index) else minus(index)) as T
}

internal inline fun bitsToString(size: Int, getBit: (index: Int) -> Boolean): String = buildString(size) {
    repeat(size) { i ->
        this.append(if (getBit(size - i - 1)) '1' else '0')
    }
}