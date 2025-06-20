package karamel.utils

import kotlin.test.Test
import kotlin.test.assertEquals

class LazyTest {
    private var initCalled = 0
    private val unsafeValue by unsafeLazy { initCalled++; 145 }

    @Test
    fun unsafe_lazy() {
        assertEquals(0, initCalled)
        assertEquals(145, unsafeValue)
        assertEquals(1, initCalled)
        assertEquals(145, unsafeValue)
        assertEquals(1, initCalled)
    }

    private var mutInitCalled = 0
    private var unsafeMutableValue by unsafeMutableLazy { mutInitCalled++; 999 }

    @Test
    fun mutable_lazy() {
        assertEquals(0, mutInitCalled)
        assertEquals(999, unsafeMutableValue)
        assertEquals(1, mutInitCalled)
        assertEquals(999, unsafeMutableValue)
        assertEquals(1, mutInitCalled)

        unsafeMutableValue = 888
        assertEquals(1, mutInitCalled)
        assertEquals(888, unsafeMutableValue)
        assertEquals(1, mutInitCalled)
        assertEquals(888, unsafeMutableValue)
        assertEquals(1, mutInitCalled)

        unsafeMutableValue = 777
        assertEquals(1, mutInitCalled)
        assertEquals(777, unsafeMutableValue)
        assertEquals(1, mutInitCalled)
    }

    @Test
    fun received_lazy() {
        assertEquals(0, SomeObject.initCalled)
        assertEquals(13, SomeObject.receivedValue)
        assertEquals(1, SomeObject.initCalled)
        assertEquals(13, SomeObject.receivedValue)
        assertEquals(1, SomeObject.initCalled)
    }

    @Test
    fun received_unsafe_lazy() {
        assertEquals(0, SomeObject.unsafeInitCalled)
        assertEquals(7, SomeObject.unsafeReceivedValue)
        assertEquals(1, SomeObject.unsafeInitCalled)
        assertEquals(7, SomeObject.unsafeReceivedValue)
        assertEquals(1, SomeObject.unsafeInitCalled)
    }
}

private object SomeObject {
    var unsafeInitCalled = 0
    var initCalled = 0
}

private val SomeObject.receivedValue by receivedLazy { initCalled++; 13 }

private val SomeObject.unsafeReceivedValue by unsafeReceivedLazy { unsafeInitCalled++; 7 }