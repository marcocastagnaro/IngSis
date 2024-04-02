package org.example

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ReadSpacesFormatTest {
    @Test
    fun `test 001 create a simple two words one space`() {
        val tokenList =
            listOf(
                Token(Types.LITERAL, "Hello", Position(1, 1), Position(1, 5)),
                Token(Types.LITERAL, "World", Position(1, 8), Position(1, 11)),
            )
        val expected =
            listOf(
                Token(Types.LITERAL, "Hello", Position(1, 1), Position(1, 5)),
                Token(Types.PUNCTUATOR, "  ", Position(1, 6), Position(1, 6)),
                Token(Types.LITERAL, "World", Position(1, 7), Position(1, 11)),
            )
        val readSpacesFormat = ReadSpacesFormat()
        val result = readSpacesFormat.applyRule(tokenList)
        assertEquals(expected[1].getValue().length, result[1].getValue().length)
    }

    @Test
    fun `test 002 try when there are no spaces`() {
        val tokenList =
            listOf(
                Token(Types.LITERAL, "Hello", Position(1, 1), Position(1, 5)),
                Token(Types.LITERAL, "World", Position(1, 6), Position(1, 10)),
            )
        val readSpacesFormat = ReadSpacesFormat()
        val result = readSpacesFormat.applyRule(tokenList)
        assertEquals(tokenList, result)
    }
}
