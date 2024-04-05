package org.example

import Parser
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
        val interpreter = Interpreter()
        assertEquals("\"Hello world!\"", interpreter.execute(abstractSyntaxTrees).string)
    }

    @Test
    fun `test 002 assignation from lexer to interpreter`() {
        val input = "let x : number = 10;"
        val lexer = Lexer2(ValueMapper())
        val tokens = lexer.execute(input)
        assertEquals(7, tokens.size)
        val parser = Parser()
        val abstractSyntaxTrees = parser.execute(tokens)
        assertEquals(1, abstractSyntaxTrees.size)
        val interpreter = Interpreter()
        interpreter.execute(abstractSyntaxTrees)
        assertEquals("10", interpreter.testingVariables("x"))
    }

    @Test
    fun `test 003 declaring a variable and then printing it`() {
        val input = "let x : number = 10;\nprintln(x);"
        val lexer = Lexer2(ValueMapper())
        val tokens = lexer.execute(input)
        assertEquals(12, tokens.size)
        val parser = Parser()
        val abstractSyntaxTrees = parser.execute(tokens)
        assertEquals(2, abstractSyntaxTrees.size)
        val interpreter = Interpreter()
        val result = interpreter.execute(abstractSyntaxTrees)
        assertEquals("10", result.string)
    }

    @Test
    fun `test 004 printing a non existing variable`() {
        val input = "println(x);"
        val lexer = Lexer2(ValueMapper())
        val tokens = lexer.execute(input)
//        assertEquals(3, tokens.size)
        val parser = Parser()
        val abstractSyntaxTrees = parser.execute(tokens)
        assertEquals(1, abstractSyntaxTrees.size)
        val interpreter = Interpreter()
        try {
            val result = interpreter.execute(abstractSyntaxTrees)
        } catch (e: Exception) {
            assertEquals("Error! Not Valid Variable", e.message)
        }
    }

//    @Test
//    fun `test 005 declaring a variable and then adding one to it`() {
//        val input = "let x : number = 10;\nx = x + 1;\nprintln(x);"
//        val lexer = Lexer2(ValueMapper())
//        val tokens = lexer.execute(input)
//        assertEquals(18, tokens.size)
//        val parser = Parser()
//        val abstractSyntaxTrees = parser.execute(tokens)
//        assertEquals(3, abstractSyntaxTrees.size)
//        val interpreter = Interpreter()
//        val result = interpreter.execute(abstractSyntaxTrees)
//        assertEquals("11", result.string)
//
//        // TODO : this case is not yet implemented
//    }
}
