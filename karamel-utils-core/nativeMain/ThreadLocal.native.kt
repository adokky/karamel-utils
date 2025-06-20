package karamel.utils

@kotlin.native.concurrent.ThreadLocal
private val threadLocalValues = HashMap<ThreadLocal<*>, Any>()

actual class ThreadLocal<T: Any> {
    actual fun get(): T =
        threadLocalValues[this]
            ?.unsafeCast<T>()
            ?: error("ThreadLocal is not initialized")

    actual fun set(value: T?) {
        if (value == null) {
            threadLocalValues.remove(this)
        } else {
            threadLocalValues[this] = value
        }
    }
}

actual inline fun <T : Any> ThreadLocal(initialValue: () -> T): ThreadLocal<T> =
    ThreadLocal<T>().apply { set(initialValue()) }