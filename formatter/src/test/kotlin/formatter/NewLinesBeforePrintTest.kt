package org.example.formatter
import org.example.Lexer
import org.example.parser.Parser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class NewLinesBeforePrintTest {
    val lexer = Lexer("1.1")
    val parser = Parser()

    @Test
    fun `test 001 apply one new line before a println`() {
        val formatter = Formatter()
        val input =
            """
            let x: number = 8;println(x);
            """.trimIndent()
        val tokens = lexer.execute(input)
        val trees = parser.execute(tokens)
        val result = formatter.execute(trees)
        assertEquals("let x: number = 8;\n\nprintln(x);\n", result)
    }
}
