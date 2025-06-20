@file:JvmName("KaramelLazy")

package karamel.utils

import kotlin.jvm.JvmName
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * A shortcut for `lazy(LazyThreadSafetyMode.NONE) { ... }`.
 *
 * [initializer] function freed as soon value initialized,
 * so it is safe to capture memory consuming structures inside.
 */
fun <T: Any> unsafeLazy(initializer: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE, initializer)

@PublishedApi
internal class MutableLazy<R, T>(initializer: R.() -> T) : ReadWriteProperty<R, T> {
    private var initializer: (R.() -> T)? = initializer
    private var _value: Any? = UNINITIALIZED_VALUE

    override fun getValue(thisRef: R, property: KProperty<*>): T {
        if (_value === UNINITIALIZED_VALUE) {
            _value = initializer!!(thisRef)
            initializer = null
        }
        @Suppress("UNCHECKED_CAST")
        return _value as T
    }

    override fun setValue(thisRef: R, property: KProperty<*>, value: T) {
        this._value = value
    }
}

private object UNINITIALIZED_VALUE

/**
 * Mutable version of `lazy(LazyThreadSafetyMode.NONE) { ... }`.
 *
 * [initializer] function freed as soon value initialized,
 * so it is safe to capture memory consuming structures inside.
 */
inline fun <T> unsafeMutableLazy(crossinline initializer: () -> T): ReadWriteProperty<Any?, T> =
    MutableLazy { initializer() }

/**
 * Passes the receiver of [T] to initialize a [lazy] value with [LazyThreadSafetyMode.NONE].
 *
 * Example:
 * ```
 * val Context.database by receivedLazy { DatabaseBuilder.build(context = this, ...) }
 * ```
 */
fun <T: Any, V> unsafeReceivedLazy(initializer: T.() -> V): ReadOnlyProperty<T, V> =
    MutableLazy(initializer)

/**
 * Passes the receiver of [T] to initialize a [lazy] value with [mode].
 *
 * Example:
 * ```
 * val Context.database by receivedLazy { DatabaseBuilder.build(context = this, ...) }
 * ```
 */
inline fun <T: Any, V> receivedLazy(
    mode: LazyThreadSafetyMode = LazyThreadSafetyMode.SYNCHRONIZED,
    crossinline initializer: T.() -> V,
): ReadOnlyProperty<T, V> = object : ReadOnlyProperty<T, V> {
    var thisRef: T? = null

    val v by lazy(mode) {
        val thisRef = thisRef!!
        this.thisRef = null
        initializer(thisRef)
    }

    override fun getValue(thisRef: T, property: KProperty<*>): V {
        this.thisRef = thisRef
        return v
    }
}