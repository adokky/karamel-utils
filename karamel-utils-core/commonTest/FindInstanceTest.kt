package karamel.utils

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

class FindInstanceTest {
    private val strings = arrayListOf<String>("a", "b", "c")
    private val nullableStrings = arrayListOf<String?>("a", "b", null, "c")
    private val nullableLists = arrayListOf<List<Int>?>(
        listOf(1), listOf(2), null, listOf(3)
    )

    @Test
    fun find_by_exact_type() {
        assertEquals("a", strings.findFirstInstanceOf<String>())
        assertEquals("c", strings.findLastInstanceOf<String>())

        assertEquals("a", nullableStrings.findFirstInstanceOf<String>())
        assertEquals("c", nullableStrings.findLastInstanceOf<String>())

        assertEquals(listOf(1), nullableLists.findFirstInstanceOf<List<Int>>())
        assertEquals(listOf(3), nullableLists.findLastInstanceOf<List<Int>>())
    }

    @Test
    fun find_by_super_type() {
        assertEquals("a", strings.findFirstInstanceOf<CharSequence>())
        assertEquals("c", strings.findLastInstanceOf<CharSequence>())

        assertEquals("a", nullableStrings.findFirstInstanceOf<CharSequence>())
        assertEquals("c", nullableStrings.findLastInstanceOf<CharSequence>())

        assertEquals(listOf(1), nullableLists.findFirstInstanceOf<Collection<Any>>())
        assertEquals(listOf(3), nullableLists.findLastInstanceOf<Collection<Any>>())
    }

    @Test
    fun should_not_find() {
        assertFailsWith<NoSuchElementException> { strings.findFirstInstanceOf<Int>() }
        assertFailsWith<NoSuchElementException> { strings.findLastInstanceOf<Int>() }

        assertFailsWith<NoSuchElementException> { nullableStrings.findFirstInstanceOf<Int>() }
        assertFailsWith<NoSuchElementException> { nullableStrings.findLastInstanceOf<Int>() }

        assertFailsWith<NoSuchElementException> { nullableLists.findFirstInstanceOf<Set<Any>>() }
        assertFailsWith<NoSuchElementException> { nullableLists.findLastInstanceOf<Set<Any>>() }
    }

    @Test
    fun should_return_null() {
        assertNull(strings.findFirstInstanceOfOrNull<Int>())
        assertNull(strings.findLastInstanceOfOrNull<Int>())

        assertNull(nullableStrings.findFirstInstanceOfOrNull<Int>())
        assertNull(nullableStrings.findLastInstanceOfOrNull<Int>())

        assertNull(nullableLists.findFirstInstanceOfOrNull<Set<Any>>())
        assertNull(nullableLists.findLastInstanceOfOrNull<Set<Any>>())
    }
}