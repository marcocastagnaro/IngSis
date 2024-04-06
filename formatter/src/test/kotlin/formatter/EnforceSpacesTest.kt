package org.example.formatter

import org.example.Lexer
import org.example.Parser
import org.example.ValueMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class EnforceSpacesTest {
    val lexer = Lexer(ValueMapper())
    val parser = Parser()
    val formatter = Formatter()

    @Test
    fun `test 001 enforce spaces`() {
        val input = "println(8+5);"
        val tokens = lexer.execute(input)
        assertEquals(7, tokens.size)
        val trees = parser.execute(tokens)
        val result = formatter.execute(trees)
        assertEquals("println(8 + 5);\n", result)
    }

    @Test
    fun `test 002 more complex space enforce`() {
        val input = "let x = 5+5;println(x+70);"
        val tokens = lexer.execute(input)
        val trees = parser.execute(tokens)
        val result = formatter.execute(trees)
        assertEquals("let x = 5 + 5;\n\nprintln(x + 70);\n", result)
    }
}