package karamel.utils

actual open class NoStackTraceThrowable
actual constructor(message: String?, cause: Throwable?) : Throwable(message, cause)

actual open class NoStackTraceIllegalArgumentException actual constructor(
    message: String?,
    cause: Throwable?
) : IllegalArgumentException(message, cause)