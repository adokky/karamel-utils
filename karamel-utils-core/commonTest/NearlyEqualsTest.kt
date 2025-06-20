package karamel.utils

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class NearlyEqualsTest {
    @Test
    fun doubles() {
        assertTrue(1.0.nearlyEquals(1.0))
        assertTrue(1.000_000_056.nearlyEquals(1.000_000_1))
        assertTrue((-1.000_000_056).nearlyEquals(-1.000_000_1))

        assertFalse(1.0.nearlyEquals(1.01))
        assertFalse(1.000_02.nearlyEquals(1.002))
        assertFalse((-1.000_001).nearlyEquals(-1.000_1))

        assertTrue(1.0.nearlyEquals(1.01, eps = 0.05))
        assertFalse(1.0.nearlyEquals(1.01, eps = 0.005))
    }

    @Test
    fun floats() {
        assertTrue(1.0f.nearlyEquals(1.0f))
        assertTrue(1.000_005_6f.nearlyEquals(1.000_01f))
        assertTrue((-1.000_005_6f).nearlyEquals(-1.000_01f))

        assertFalse(1.0f.nearlyEquals(1.01f))
        assertFalse(1.000_02f.nearlyEquals(1.002f))
        assertFalse((-1.000_001f).nearlyEquals(-1.000_1f))

        assertTrue(1.0f.nearlyEquals(1.01f, eps = 0.05f))
        assertFalse(1.0f.nearlyEquals(1.01f, eps = 0.005f))
    }
}