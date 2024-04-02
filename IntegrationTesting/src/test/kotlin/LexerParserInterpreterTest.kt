package org.example

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class LexerParserInterpreterTest {
    @Test
    fun `test 001 test a simple println from lexer to interpreter`() {
        val input = "println(\"Hello world!\");"
        val lexer = Lexer2(ValueMapper())
        val tokens = lexer.execute(input)
        assertEquals(5, tokens.size)
        val parser = Parser()
        val abstractSyntaxTrees = parser.execute(tokens)
        assertEquals(1, abstractSyntaxTrees.size)
        val interpreter = Interpreter(abstractSyntaxTrees)
        assertEquals("\"Hello world!\"", interpreter.execute().string)
    }
    @Test
    fun `test 002 test assignation from lexer to interpreter` (){
        val input = "let x : number = 10;"
        val lexer = Lexer2(ValueMapper())
        val tokens = lexer.execute(input)
        assertEquals(7, tokens.size)
        val parser = Parser()
        val abstractSyntaxTrees = parser.execute(tokens)
        assertEquals(1, abstractSyntaxTrees.size)
        val interpreter = Interpreter(abstractSyntaxTrees)
        val result = interpreter.execute()
        assertEquals("10", interpreter.testingVariables("x"))
    }
}
