package org.example
import Parser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ParserTest {
    @Test
    fun testExecute() {
        val tokens =
            listOf(
                Token(Types.KEYWORD, "let", Position(0, 0), Position(1, 3)),
                Token(Types.IDENTIFIER, "var", Position(0, 4), Position(1, 9)),
                Token(Types.DECLARATOR, ":", Position(0, 10), Position(1, 11)),
                Token(Types.DATA_TYPE, "string", Position(0, 12), Position(1, 18)),
                Token(Types.ASSIGNATION, "=", Position(0, 19), Position(1, 20)),
                Token(Types.LITERAL, "hola", Position(0, 21), Position(1, 27)),
                Token(Types.OPERATOR, "+", Position(0, 213), Position(1, 27)),
                Token(Types.LITERAL, "1", Position(0, 21234), Position(1, 27)),
                Token(Types.LITERAL, "*", Position(0, 21234), Position(1, 27)),
                Token(Types.LITERAL, "15", Position(0, 21234), Position(1, 27)),
                Token(Types.PUNCTUATOR, ";", Position(0, 28), Position(1, 29)),
            )

        val parser = Parser()
        val abstractSyntaxTrees = parser.execute(tokens)

        assertEquals(1, abstractSyntaxTrees.size)

        val ast = abstractSyntaxTrees[0]
        val leftNode = ast.getLeft()
        val rightNode = ast.getRight()

        assertEquals("=", ast.getToken().getValue())
        assertEquals("let", leftNode?.getToken()?.getValue())
        val declarationTokens = leftNode?.getRight()
        assertEquals(":", declarationTokens?.getToken()?.getValue())
        assertEquals("var", declarationTokens?.getLeft()?.getToken()?.getValue())
        assertEquals("string", declarationTokens?.getRight()?.getToken()?.getValue())
        assertEquals("+", rightNode?.getToken()?.getValue())
        assertEquals("hola", rightNode?.getLeft()?.getToken()?.getValue())
        assertEquals("*", rightNode?.getRight()?.getToken()?.getValue())
        assertEquals("15", rightNode?.getRight()?.getRight()?.getToken()?.getValue())
        assertEquals("1", rightNode?.getRight()?.getLeft()?.getToken()?.getValue())
    }

    @Test
    fun testExecute_Println() {
        val tokens =
            listOf(
                Token(Types.FUNCTION, "println", Position(0, 0), Position(1, 6)),
                Token(Types.PUNCTUATOR, "(", Position(0, 7), Position(1, 8)),
                Token(Types.LITERAL, "Hello", Position(0, 9), Position(1, 15)),
                Token(Types.OPERATOR, "+", Position(0, 16), Position(1, 15)),
                Token(Types.LITERAL, "world", Position(0, 16), Position(1, 15)),
                Token(Types.PUNCTUATOR, ")", Position(0, 22), Position(1, 23)),
                Token(Types.PUNCTUATOR, ";", Position(0, 24), Position(1, 25)),
            )

        val parser = Parser()
        val abstractSyntaxTrees = parser.execute(tokens)

        val ast = abstractSyntaxTrees[0]
        val rightNode = ast.getRight()

        assertEquals("println", ast.getToken().getValue())
        assertEquals("Hello", rightNode?.getLeft()?.getToken()?.getValue())
        assertEquals("world", rightNode?.getRight()?.getToken()?.getValue())
    }

    @Test
    fun testExecute_PrintlnWithArguments() {
        val tokens =
            listOf(
                Token(Types.FUNCTION, "println", Position(0, 0), Position(1, 6)),
                Token(Types.PUNCTUATOR, "(", Position(0, 7), Position(1, 8)),
                Token(Types.LITERAL, "Hello", Position(0, 9), Position(1, 15)),
                Token(Types.OPERATOR, "+", Position(0, 16), Position(1, 15)),
                Token(Types.LITERAL, "world", Position(0, 16), Position(1, 15)),
                Token(Types.OPERATOR, "+", Position(0, 16), Position(1, 15)),
                Token(Types.LITERAL, "!", Position(0, 16), Position(1, 15)),
                Token(Types.PUNCTUATOR, ")", Position(0, 22), Position(1, 23)),
                Token(Types.PUNCTUATOR, ";", Position(0, 24), Position(1, 25)),
            )

        val parser = Parser()
        val abstractSyntaxTrees = parser.execute(tokens)

        val ast = abstractSyntaxTrees[0]
        val rightNode = ast.getRight()

        assertEquals("println", ast.getToken().getValue())
        assertEquals("Hello", rightNode?.getLeft()?.getToken()?.getValue())
        assertEquals("+", rightNode?.getToken()?.getValue())
        assertEquals("+", rightNode?.getRight()?.getToken()?.getValue())
        assertEquals("!", rightNode?.getRight()?.getRight()?.getToken()?.getValue())
        assertEquals("world", rightNode?.getRight()?.getLeft()?.getToken()?.getValue())
    }

    @Test
    public fun testAssignationVariable() {
        val tokens =
            listOf(
                Token(Types.KEYWORD, "let", Position(0, 0), Position(1, 3)),
                Token(Types.IDENTIFIER, "var", Position(0, 4), Position(1, 9)),
                Token(Types.DECLARATOR, ":", Position(0, 10), Position(1, 11)),
                Token(Types.DATA_TYPE, "string", Position(0, 12), Position(1, 18)),
                Token(Types.ASSIGNATION, "=", Position(0, 19), Position(1, 20)),
                Token(Types.LITERAL, "hola", Position(0, 21), Position(1, 27)),
                Token(Types.PUNCTUATOR, ";", Position(0, 28), Position(1, 29)),
                Token(Types.IDENTIFIER, "var", Position(1, 0), Position(1, 3)),
                Token(Types.ASSIGNATION, "=", Position(1, 4), Position(1, 5)),
                Token(Types.LITERAL, "chau", Position(1, 6), Position(1, 11)),
                Token(Types.PUNCTUATOR, ";", Position(1, 12), Position(1, 13)),
            )

        val parser = Parser()
        val abstractSyntaxTrees = parser.execute(tokens)

        assertEquals(2, abstractSyntaxTrees.size)

        val ast = abstractSyntaxTrees[0]
        val leftNode = ast.getLeft()
        val rightNode = ast.getRight()

        assertEquals("=", ast.getToken().getValue())
        assertEquals("let", leftNode?.getToken()?.getValue())
        val declarators = leftNode?.getRight()
        assertEquals(":", declarators?.getToken()?.getValue())
        assertEquals("var", declarators?.getLeft()?.getToken()?.getValue())
        assertEquals("string", declarators?.getRight()?.getToken()?.getValue())
        assertEquals("hola", rightNode?.getToken()?.getValue())

        val ast2 = abstractSyntaxTrees[1]
        val leftNode2 = ast2.getLeft()
        val rightNode2 = ast2.getRight()

        assertEquals("=", ast2.getToken().getValue())
        assertEquals("var", leftNode2?.getToken()?.getValue())
        assertEquals("chau", rightNode2?.getToken()?.getValue())
    }

    @Test
    fun `test 005 create a declaration tree only`() {
        val tokens =
            listOf(
                Token(Types.KEYWORD, "let", Position(0, 0), Position(1, 3)),
                Token(Types.IDENTIFIER, "x", Position(0, 4), Position(1, 5)),
                Token(Types.DECLARATOR, ":", Position(0, 6), Position(1, 7)),
                Token(Types.DATA_TYPE, "number", Position(0, 8), Position(1, 14)),
                Token(Types.PUNCTUATOR, ";", Position(0, 15), Position(1, 16)),
            )

        val parser = Parser()
        val abstractSyntaxTrees = parser.execute(tokens)

        assertEquals(1, abstractSyntaxTrees.size)

        val ast = abstractSyntaxTrees[0]
        val rightNode = ast.getRight()

        assertEquals("let", ast.getToken().getValue())
        assertEquals(":", rightNode?.getToken()?.getValue())
        assertEquals("x", rightNode?.getLeft()?.getToken()?.getValue())
        assertEquals("number", rightNode?.getRight()?.getToken()?.getValue())
    }
}
