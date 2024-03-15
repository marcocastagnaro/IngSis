import org.example.AST.AbstractSyntaxTree
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
            Token(Types.IDENTIFIER, "let", Position(0, 0), Position(1, 3)),
            Token(Types.LITERAL, "var", Position(0, 4), Position(1, 9)),
            Token(Types.PUNCTUATOR, ":", Position(0, 10), Position(1, 11)),
            Token(Types.DATA_TYPE, "string", Position(0, 12), Position(1, 18)),
            Token(Types.ASSIGNATOR, "=", Position(0, 19), Position(1, 20)),
            Token(Types.LITERAL, "\"hola\"", Position(0, 21), Position(1, 27))
        )

        val parser = Parser()
        val abstractSyntaxTrees = parser.execute(tokens)

        assertEquals(1, abstractSyntaxTrees.size)

        val ast = abstractSyntaxTrees[0]
        val leftNode = ast.left()
        val rightNode = ast.right()

        assertEquals("=", ast.getValue().getValue())
        assertEquals("let", leftNode?.getValue()?.getValue())
        assertEquals("var", leftNode?.left()?.getValue()?.getValue())
        assertEquals("string", leftNode?.right()?.getValue()?.getValue())
        assertEquals("\"hola\"", rightNode?.getValue()?.getValue())
    }
}
