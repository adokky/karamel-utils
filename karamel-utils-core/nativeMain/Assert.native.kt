package karamel.utils

actual val assertionsEnabled: Boolean = run {
    var enabled = false
    assert { enabled = true; true }
    enabled
}