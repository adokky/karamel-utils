package karamel.utils

inline fun <reified E: Throwable> enrichMessageOf(
    message: () -> String,
    body: () -> Unit
) {
    try {
        body()
    } catch (err: Throwable) {
        throw when (err) {
            is E -> AssertionError(message(), err)
            else -> err
        }
    }
}

inline fun <reified E: Throwable> enrichMessageOf(
    message: String,
    body: () -> Unit
) {
    enrichMessageOf<E>({ message }, body)
}