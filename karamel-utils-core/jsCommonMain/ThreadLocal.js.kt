package karamel.utils

actual class ThreadLocal<T: Any> {
    private var value: T? = null

    actual fun get(): T = value ?: error("ThreadLocal is not initialized")

    actual fun set(value: T?) {
        this.value = value
    }
}

actual inline fun <T : Any> ThreadLocal(initialValue: () -> T): ThreadLocal<T> =
    ThreadLocal<T>().apply { set(initialValue()) }