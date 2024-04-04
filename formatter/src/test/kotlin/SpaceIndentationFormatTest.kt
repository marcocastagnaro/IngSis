package org.example

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SpaceIndentationFormatTest {
    private val spaceIndentationFormat = SpaceIndentationFormat()

    @Test
    fun `test 001 testing a simple elimination of indentation`() {
        val tokens =
            listOf(
                Token(Types.IDENTIFIER, "let x = 5", Position(1, 1), Position(1, 9)),
                Token(Types.IDENTIFIER, "    println(x)", Position(2, 1), Position(2, 14)),
            )
        val expected =
            listOf(
                Token(Types.IDENTIFIER, "let x = 5", Position(1, 1), Position(1, 9)),
                Token(Types.IDENTIFIER, "println(x)", Position(2, 1), Position(2, 11)),
            )
        val result = spaceIndentationFormat.applyRule(tokens)
        assertEquals(expected[0].getValue(), result[0].getValue())
        assertEquals(expected[1].getValue(), result[1].getValue())
    }

    @Test
    fun `test 002 testing a case of no elimination of indentation`() {
        val tokens =
            listOf(
                Token(Types.IDENTIFIER, "let x = 5", Position(1, 1), Position(1, 9)),
                Token(Types.IDENTIFIER, "println(x)", Position(2, 1), Position(2, 11)),
            )
        val expected =
            listOf(
                Token(Types.IDENTIFIER, "let x = 5", Position(1, 1), Position(1, 9)),
                Token(Types.IDENTIFIER, "println(x)", Position(2, 1), Position(2, 11)),
            )
        val result = spaceIndentationFormat.applyRule(tokens)
        assertEquals(expected[0].getValue(), result[0].getValue())
        assertEquals(expected[1].getValue(), result[1].getValue())
    }
}
