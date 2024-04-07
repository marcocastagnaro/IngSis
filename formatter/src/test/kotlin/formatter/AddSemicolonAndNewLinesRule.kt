package org.example.formatter

import org.example.Lexer
import org.example.Parser
import org.example.ValueMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AddSemicolonAndNewLinesRule {
    val lexer = Lexer(ValueMapper())
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
}
