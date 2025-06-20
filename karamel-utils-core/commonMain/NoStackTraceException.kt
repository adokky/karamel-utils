package karamel.utils

expect open class NoStackTraceThrowable(
    message: String? = null,
    cause: Throwable? = null
): Throwable

expect open class NoStackTraceIllegalArgumentException(
    message: String? = null,
    cause: Throwable? = null
): IllegalArgumentException