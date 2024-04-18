package org.example.formatter

import org.example.Lexer
import org.example.parser.Parser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AddSemicolonAndNewLinesRule {
    val lexer = Lexer("1.1")
    val parser = Parser()
    val formater = Formatter()

    @Test
    fun `test 001 add the semicolon with new line`() {
        val input = "let x : number;"
        val tokens = lexer.execute(input)
        val trees = parser.execute(tokens)
        val result = formater.execute(trees)
        assertEquals("let x: number;\n", result)
    }

    @Test
    fun `test 002 add the semicolon with new line`() {
        val input = "let x : number;"
        val tokens = lexer.execute(input)
        val trees = parser.execute(tokens)
        val result = formater.execute(trees)
        assertEquals("let x: string;\n", result)
    }

}
