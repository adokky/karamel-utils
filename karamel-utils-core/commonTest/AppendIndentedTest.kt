package karamel.utils

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class AppendIndentedTest {
    private fun check(s: String, spaces: Int) {
        val tab = buildString { appendSpaces(spaces) }
        val expected = tab + s.replace("\n", "\n$tab")
        val actual = buildString { appendIndented(s, spaces = spaces) }
        if (expected != actual) fail(
            "input: '${s.replace("\n", "\\n")}' " +
            "expected:<'${expected.replace("\n", "\\n")}'> " +
            "but was:<'${actual.replace("\n", "\\n")}'>"
        )
    }

    @Test
    fun test() {
        listOf(
            "",
            " ",
            "\n",
            "\n\n",
            "\na\n",
            "a\nA",
            "\n\nA",
            "\n\n\n",
            " \n \n \n ",
            "abcde\n",
            "\nFGHI",
            "abcde\n\nFGHI",
            "a\n bcd \n\n\ndfe\n",
            "\n\nABC\n\n",
            "A\nB\nC\n",
        ).forEach { s ->
            check(s, 1)
            check(s, 3)
        }
    }

    @Test
    fun append_spaces() {
        assertEquals("", buildString { appendSpaces(0) })
        assertEquals(" ", buildString { appendSpaces(1) })
        assertEquals("  ", buildString { appendSpaces(2) })
    }
}