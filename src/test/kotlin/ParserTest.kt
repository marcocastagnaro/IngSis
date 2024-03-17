import org.example.Token.Position
import org.example.Token.Token
import org.example.Token.Types
import org.example.parser.Parser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ParserTest {


    @Test
    fun testExecute() {
        val tokens = listOf(
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
            Token(Types.PUNCTUATOR, ";", Position(0, 28), Position(1, 29))
        )

        val parser = Parser()
        val abstractSyntaxTrees = parser.execute(tokens)

        assertEquals(1, abstractSyntaxTrees.size)

        val ast = abstractSyntaxTrees[0]
        val leftNode = ast.getLeft()
        val rightNode = ast.getRight()

        assertEquals("=", ast.getValue().getValue())
        assertEquals("let", leftNode?.getValue()?.getValue())
        assertEquals("var", leftNode?.getLeft()?.getValue()?.getValue())
        assertEquals("string", leftNode?.getRight()?.getValue()?.getValue())
        assertEquals("+", rightNode?.getValue()?.getValue())
        assertEquals("hola", rightNode?.getLeft()?.getValue()?.getValue())
        assertEquals("*", rightNode?.getRight()?.getValue()?.getValue())
        assertEquals("15", rightNode?.getRight()?.getRight()?.getValue()?.getValue())
        assertEquals("1", rightNode?.getRight()?.getLeft()?.getValue()?.getValue())
    }
}
