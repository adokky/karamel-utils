package karamel.utils

actual open class NoStackTraceThrowable
actual constructor(message: String?, cause: Throwable?) : Throwable(message, cause) {
    override fun fillInStackTrace(): Throwable? =
        if (assertionsEnabled) super.fillInStackTrace() else null
}

actual open class NoStackTraceIllegalArgumentException actual constructor(
    message: String?,
    cause: Throwable?
) : IllegalArgumentException(message, cause) {
    override fun fillInStackTrace(): Throwable? =
        if (assertionsEnabled) super.fillInStackTrace() else null
}