package karamel.utils

@JvmRecord
actual data class Box<T> actual constructor(actual val value: T) {
    override fun toString() = value.toString()
}