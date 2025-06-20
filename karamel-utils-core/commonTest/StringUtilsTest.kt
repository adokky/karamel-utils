package karamel.utils

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class StringUtilsTest {
    @Test
    fun with_suffix() {
        assertEquals("", "".withSuffix(""))
        assertEquals("abc", "".withSuffix("abc"))
        assertEquals("zzz", "zzz".withSuffix(""))
        assertEquals("abcab", "abc".withSuffix("ab"))
        assertEquals("zzz", "zzz".withSuffix("z"))
        assertEquals("zzz", "zzz".withSuffix("zzz"))
        assertEquals("zzzzxz", "zzz".withSuffix("zxz"))
    }

    @Test
    fun with_prefix() {
        assertEquals("", "".withPrefix(""))
        assertEquals("abc", "".withPrefix("abc"))
        assertEquals("zzz", "zzz".withPrefix(""))
        assertEquals("bcabc", "abc".withPrefix("bc"))
        assertEquals("zzz", "zzz".withPrefix("z"))
        assertEquals("zzz", "zzz".withPrefix("zzz"))
        assertEquals("zxzzzz", "zzz".withPrefix("zxz"))
    }

    @Test
    fun contains_any_char() {
        assertFalse("".containsAny('a'))
        assertFalse("".containsAny('a', 'b'))
        assertTrue("a".containsAny('a'))
        assertTrue("hello".containsAny('a', 'e'))
        assertTrue("hello".containsAny('e', 'a'))
        assertTrue("hello".containsAny('o', 'h'))
        assertFalse("hello".containsAny('z', 'y'))

        assertTrue("hello".containsAny('h', 'e', startIndex = 1))
        assertFalse("hello".containsAny('h', 'e', startIndex = 2))
    }

    @Test
    fun contains_any_char_ignore_case() {
        assertFalse("".containsAny('a', ignoreCase = true))
        assertFalse("".containsAny('a', 'b', ignoreCase = true))
        assertTrue("A".containsAny('a', ignoreCase = true))
        assertTrue("hello".containsAny('A', 'E', ignoreCase = true))
        assertTrue("hello".containsAny('E', 'A', ignoreCase = true))
        assertTrue("HellO".containsAny('o', 'h', ignoreCase = true))
        assertFalse("hello".containsAny('z', 'y', ignoreCase = true))

        assertTrue("hEllo".containsAny('H', 'e', startIndex = 1, ignoreCase = true))
        assertFalse("Hello".containsAny('h', 'E', startIndex = 2, ignoreCase = true))
    }

    @Test
    fun replace_char_at() {
        assertEquals("Z", "Z".replaceCharAt(0, 'Z'))
        assertEquals("Z", "a".replaceCharAt(0, 'Z'))
        assertEquals("Zbc", "abc".replaceCharAt(0, 'Z'))
        assertEquals("aZc", "abc".replaceCharAt(1, 'Z'))
        assertEquals("abZ", "abc".replaceCharAt(2, 'Z'))
    }

    @Test
    fun index_of_char_in_range() {
        assertEquals(-1, "".indexOf('a', startIndex = 0, endIndex = 0))
        assertEquals(-1, "X".indexOf('a', startIndex = 0, endIndex = 1))
        assertEquals(-1, "XYZ".indexOf('a', startIndex = 0, endIndex = 3))

        assertEquals(0, "a".indexOf('a', startIndex = 0, endIndex = 1))
        assertEquals(1, "abcb".indexOf('b', startIndex = 0, endIndex = 4))
        assertEquals(2, "abcc".indexOf('c', startIndex = 0, endIndex = 4))

        assertEquals(-1, "a".indexOf('a', startIndex = 1, endIndex = 1))
        assertEquals(1, "abcb".indexOf('b', startIndex = 1, endIndex = 4))
        assertEquals(2, "abcc".indexOf('c', startIndex = 1, endIndex = 4))

        assertEquals(-1, "a".indexOf('a', startIndex = 2, endIndex = 1))
        assertEquals(-1, "abc".indexOf('b', startIndex = 2, endIndex = 3))
        assertEquals(2, "abc".indexOf('c', startIndex = 2, endIndex = 3))

        assertEquals(-1, "a".indexOf('a', startIndex = 0, endIndex = 0))
        assertEquals(1, "abc".indexOf('b', startIndex = 1, endIndex = 2))
        assertEquals(2, "abc".indexOf('c', startIndex = 2, endIndex = 3))
        assertEquals(-1, "abc".indexOf('c', startIndex = 0, endIndex = 2))
        assertEquals(-1, "abcb".indexOf('b', startIndex = 0, endIndex = 1))
        assertEquals(0, "abc".indexOf('a', startIndex = 0, endIndex = 1))
        assertEquals(2, "abcc".indexOf('c', startIndex = 1, endIndex = 3))
        assertEquals(1, "aaaa".indexOf('a', startIndex = 1, endIndex = 3))
    }

    @Test
    fun string_or_empty() {
        assertEquals("",    "abc".orEmptyIf { true })
        assertEquals("abc", "abc".orEmptyIf { false })

        assertEquals("abc", "abc".orEmptyUnless { true })
        assertEquals("",    "abc".orEmptyUnless { false })
    }

    @Test
    fun string_times_n() {
        assertEquals("", "" * 10)
        assertEquals("A", "A" * 1)
        assertEquals("AAA", "A" * 3)
        assertEquals("ABC", "ABC" * 1)
        assertEquals("ABCABC", "ABC" * 2)
        assertEquals("ABC_ABC_ABC_", "ABC_" * 3)
    }
}
