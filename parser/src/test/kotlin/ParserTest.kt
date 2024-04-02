package org.example
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ParserTest {
    @Test
    fun testExecute() {
        val tokens =
            listOf(
                Token(Types.KEYWORD, "let", Position(0, 0), Position(1, 3)),
                Token(Types.IDENTIFIER, "var", Position(0, 4), Position(1, 9)),
                Token(Types.PUNCTUATOR, ":", Position(0, 10), Position(1, 11)),
                Token(Types.DATA_TYPE, "string", Position(0, 12), Position(1, 18)),
                Token(Types.ASSIGNATOR, "=", Position(0, 19), Position(1, 20)),
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
        assertEquals("var", leftNode?.getLeft()?.getToken()?.getValue())
        assertEquals("string", leftNode?.getRight()?.getToken()?.getValue())
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
        val leftNode = ast.getLeft()
        val rightNode = ast.getRight()

        assertEquals("println", ast.getToken().getValue())
        assertEquals("Hello", leftNode?.getToken()?.getValue())
        assertEquals("world", rightNode?.getToken()?.getValue())
    }

//    @Test
//    fun testExecute_PrintlnWithArguments() {
//        val tokens =
//            listOf(
//                Token(Types.FUNCTION, "println", Position(0, 0), Position(1, 6)),
//                Token(Types.PUNCTUATOR, "(", Position(0, 7), Position(1, 8)),
//                Token(Types.LITERAL, "Hello", Position(0, 9), Position(1, 15)),
//                Token(Types.OPERATOR, "+", Position(0, 16), Position(1, 15)),
//                Token(Types.LITERAL, "world", Position(0, 16), Position(1, 15)),
//                Token(Types.OPERATOR, "+", Position(0, 16), Position(1, 15)),
//                Token(Types.LITERAL, "!", Position(0, 16), Position(1, 15)),
//                Token(Types.PUNCTUATOR, ")", Position(0, 22), Position(1, 23)),
//                Token(Types.PUNCTUATOR, ";", Position(0, 24), Position(1, 25)),
//            )
//
//        val parser = Parser()
//        val abstractSyntaxTrees = parser.execute(tokens)
//
//        val ast = abstractSyntaxTrees[0]
//        val leftNode = ast.getLeft()
//        val rightNode = ast.getRight()
//
//        assertEquals("println", ast.getToken().getValue())
//        assertEquals("Hello", leftNode?.getToken()?.getValue())
//        assertEquals("+", rightNode?.getToken()?.getValue())
//        assertEquals("world", rightNode?.getLeft()?.getToken()?.getValue())
//        assertEquals("!", rightNode?.getRight()?.getToken()?.getValue())
//    }

    /*@Test
    fun testExecute_Println_NoArgs() {
        val tokens = listOf(
            Token(Types.KEYWORD, "println", Position(0, 0), Position(1, 6)),
            Token(Types.PUNCTUATOR, "(", Position(0, 7), Position(1, 8)),
            Token(Types.PUNCTUATOR, ")", Position(0, 9), Position(1, 10)),
            Token(Types.PUNCTUATOR, ";", Position(0, 11), Position(1, 12))
        )

        val parser = Parser()
        val abstractSyntaxTrees = parser.execute(tokens)

        assertEquals(1, abstractSyntaxTrees.size)

        val ast = abstractSyntaxTrees[0]
        assertEquals("println", ast.getToken().getValue())
    }*/
}
