package org.example.formatter
import org.example.Lexer
import org.example.Parser
import org.example.ValueMapper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class NewLinesBeforePrintTest{

    val lexer = Lexer(ValueMapper())
    val parser = Parser()


    @Test
    fun `test 001 apply one new line before a println`() {
        val formatter = Formatter(listOf(NewLinesBeforePrint()))
        val input  = """
            let x: number = 8;println(x);
        """.trimIndent()
        val tokens = lexer.execute(input)
        val trees = parser.execute(tokens)
        val result = formatter.execute(trees)
        assertEquals("let x : number = 8;\n\nprintln(x);\n", result)
    }
}