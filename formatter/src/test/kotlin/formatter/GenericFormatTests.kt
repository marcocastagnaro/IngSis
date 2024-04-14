package formatter

import org.example.Lexer
import org.example.Parser
import org.example.ValueMapper
import org.example.formatter.Formatter
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class GenericFormatTests {
    val lexer = Lexer(ValueMapper())
    val formatter = Formatter()
    val parser = Parser()

    /*@Test
    fun `test 001 formating rules work with readInput`() {
        val input =
            """
            println(readInput("hello world"));
            """.trimIndent()
        val tokens = lexer.execute(input)
        val ast = parser.execute(tokens)
        val formatted = formatter.execute(ast)
        assertEquals("\nprintln(readInput(\"hello world\"));\n", formatted)
    }*/
}
