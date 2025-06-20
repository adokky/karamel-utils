package karamel.utils

actual typealias ThreadLocal<T> = java.lang.ThreadLocal<T>

actual inline fun <T : Any> ThreadLocal(crossinline initialValue: () -> T): ThreadLocal<T> =
    java.lang.ThreadLocal.withInitial<T> { initialValue() }