package org.example.formatter
import org.example.Lexer
import org.example.parser.Parser
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class NoSpacesInAssignationRuleTest {
    val lexer = Lexer("1.1")
    val parser = Parser()
    val formater = Formatter("src/main/resources/formatter/NoSpacesInAssignation.json")

    @Test
    fun `test 001 taking out spaces`() {
        val input = "let variable:string = \"this is a variable\"; "
        val tokens = lexer.execute(input)
        val trees = parser.execute(tokens)
        val result = formater.execute(trees)
        assertEquals("let variable: string=\"this is a variable\";\n", result)
    }

    @Test
    fun `test 002 takeing out a lot of spaces`() {
        val input = "let variable : number     =     7;"
        val tokens = lexer.execute(input)
        val trees = parser.execute(tokens)
        val result = formater.execute(trees)
        assertEquals("let variable: number=7;\n", result)
    }
}
