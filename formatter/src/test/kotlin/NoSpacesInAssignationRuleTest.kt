package org.example
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class NoSpacesInAssignationRuleTest {
    val lexer = Lexer(ValueMapper())
    val parser = Parser()
    val formater = Formatter(listOf(NoSpacesInAssignationRule()))

    @Test
    fun `test 001 taking out spaces`() {
        val input = "let variable:string = \"this is a variable\"; "
        val tokens = lexer.execute(input)
        val trees = parser.execute(tokens)
        val result = formater.execute(trees)
        assertEquals("let variable : string=\"this is a variable\"", result)
    }

    @Test
    fun `test 002 takeing out a lot of spaces`() {
        val input = "let variable : number     =     7;"
        val tokens = lexer.execute(input)
        val trees = parser.execute(tokens)
        val result = formater.execute(trees)
        assertEquals("let variable : number=7", result)
    }
}
