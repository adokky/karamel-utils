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
        assertEquals("a", strings.firstInstanceOf<String>())
        assertEquals("c", strings.lastInstanceOf<String>())

        assertEquals("a", nullableStrings.firstInstanceOf<String>())
        assertEquals("c", nullableStrings.lastInstanceOf<String>())

        assertEquals(listOf(1), nullableLists.firstInstanceOf<List<Int>>())
        assertEquals(listOf(3), nullableLists.lastInstanceOf<List<Int>>())
    }

    @Test
    fun find_by_super_type() {
        assertEquals("a", strings.firstInstanceOf<CharSequence>())
        assertEquals("c", strings.lastInstanceOf<CharSequence>())

        assertEquals("a", nullableStrings.firstInstanceOf<CharSequence>())
        assertEquals("c", nullableStrings.lastInstanceOf<CharSequence>())

        assertEquals(listOf(1), nullableLists.firstInstanceOf<Collection<Any>>())
        assertEquals(listOf(3), nullableLists.lastInstanceOf<Collection<Any>>())
    }

    @Test
    fun should_not_find() {
        assertFailsWith<NoSuchElementException> { strings.firstInstanceOf<Int>() }
        assertFailsWith<NoSuchElementException> { strings.lastInstanceOf<Int>() }

        assertFailsWith<NoSuchElementException> { nullableStrings.firstInstanceOf<Int>() }
        assertFailsWith<NoSuchElementException> { nullableStrings.lastInstanceOf<Int>() }

        assertFailsWith<NoSuchElementException> { nullableLists.firstInstanceOf<Set<Any>>() }
        assertFailsWith<NoSuchElementException> { nullableLists.lastInstanceOf<Set<Any>>() }
    }

    @Test
    fun should_return_null() {
        assertNull(strings.firstInstanceOfOrNull<Int>())
        assertNull(strings.lastInstanceOfOrNull<Int>())

        assertNull(nullableStrings.firstInstanceOfOrNull<Int>())
        assertNull(nullableStrings.lastInstanceOfOrNull<Int>())

        assertNull(nullableLists.firstInstanceOfOrNull<Set<Any>>())
        assertNull(nullableLists.lastInstanceOfOrNull<Set<Any>>())
    }
}