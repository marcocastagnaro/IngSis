package org.example

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ReadLinesFormatTest {
    @Test
    fun `test 001 two lines simple formatting`()  {
        val tokenList =
            listOf(
                Token(Types.LITERAL, "Hello", Position(1, 1), Position(1, 5)),
                Token(Types.LITERAL, "World", Position(2, 1), Position(2, 5)),
            )
        val expected =
            listOf(
                Token(Types.LITERAL, "Hello", Position(1, 1), Position(1, 5)),
                Token(Types.PUNCTUATOR, ";\n", Position(1, 5), Position(2, 1)),
                Token(Types.LITERAL, "World", Position(2, 1), Position(2, 5)),
            )
        val result = ReadLinesFormat().applyRule(tokenList)
        assertEquals(expected[0].getValue(), result[0].getValue())
        assertEquals(expected[1].getValue(), result[1].getValue())
        assertEquals(expected[2].getValue(), result[2].getValue())
    }
}
