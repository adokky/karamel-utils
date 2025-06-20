package karamel.utils

@JvmField
actual val assertionsEnabled: Boolean = NoStackTraceIllegalArgumentException::class.java.desiredAssertionStatus()