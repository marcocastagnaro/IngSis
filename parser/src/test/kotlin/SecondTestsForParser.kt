package org.example

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class SecondTestsForParser {
    @Test
    fun `test 012 test println (read input)`() {
        val input = "println(readInput(\"Ingrese valor\"));"
        val lexer = Lexer("1.1")
        val tokens = lexer.execute(input)
        val parser = Parser()
        val trees = parser.execute(tokens)
        assertEquals(1, trees.size)
        assertEquals("println", trees[0].getToken().getValue())
        assertEquals("readInput", trees[0].getRight()?.getToken()?.getValue())
        assertEquals("\"Ingrese valor\"", trees[0].getRight()?.getRight()?.getToken()?.getValue())
    }
}
