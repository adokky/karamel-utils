package karamel.utils

class MapEntry<out K, out V>(override val key: K, override val value: V): Map.Entry<K, V> {
    constructor(pair: Pair<K, V>): this(pair.first, pair.second)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false

        if (this::class == other::class) return fastEquals(other.unsafeCast())
        if (other is Map.Entry<*, *>) return slowEquals(other.unsafeCast())

        return false
    }

    private fun slowEquals(other: Map.Entry<K, V>): Boolean {
        return key == other.key && value == other.value
    }

    private fun fastEquals(other: MapEntry<K, V>): Boolean {
        return key == other.key && value == other.value
    }

    override fun hashCode(): Int {
        // matches the implementation of java.util.HashMap.Node (aka java Map.Entry)
        return (key?.hashCode() ?: 0) xor (value?.hashCode() ?: 0)
    }

    override fun toString(): String = "($key, $value)"
}