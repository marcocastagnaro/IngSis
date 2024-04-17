package org.example.formatter

import org.example.Lexer
import org.example.Parser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class EnforceSpacesTest {
    val lexer = Lexer("1.1")
    val parser = Parser()
    val formatter = Formatter()

    @Test
    fun `test 001 enforce spaces`() {
        val input = "println(8+5);"
        val tokens = lexer.execute(input)
        assertEquals(7, tokens.size)
        val trees = parser.execute(tokens)
        val result = formatter.execute(trees)
        assertEquals("\nprintln(8 + 5);\n", result)
    }

    @Test
    fun `test 002 more complex space enforce`() {
        val input = "let x : number = 5+5;println(x+70);"
        val tokens = lexer.execute(input)
        val trees = parser.execute(tokens)
        val result = formatter.execute(trees)
        assertEquals("let x: number = 5 + 5;\n\nprintln(x + 70);\n", result)
    }

    @Test
    fun `test 003 multiple operators in single line`() {
        val input = "let x : number = 5+7+9/5*3;"
        val tokens = lexer.execute(input)
        assertEquals(listOf("let", "x", ":", "number", "=", "5", "+", "7", "+", "9", "/", "5", "*", "3", ";"), tokens.map { it.getValue() })
        assertEquals(15, tokens.size)
        val trees = parser.execute(tokens)
        val result = formatter.execute(trees)
        assertEquals("let x: number = 5 + 7 + 9 / 5 * 3;\n", result)
    }

    @Test
    fun `test 004 multiple operators in println`() {
        val input = "println(5+7-9/5*3);"
        val tokens = lexer.execute(input)
        assertEquals(listOf("println", "(", "5", "+", "7", "-", "9", "/", "5", "*", "3", ")", ";"), tokens.map { it.getValue() })
        assertEquals(13, tokens.size)
        val trees = parser.execute(tokens)
        val result = formatter.execute(trees)
//        TODO("Parser println needs modifying")
//        assertEquals("println(5 + 7 - 9 / 5 * 3);\n", result)
    }
}
