package karamel.utils

actual fun Throwable.isInterruptedException() = this is InterruptedException

