import org.example.AST.AbstractSyntaxTree
import org.example.AST.CompositeAbstractSyntaxTree
import org.example.Token.Position
import org.example.Token.Token
import org.example.Token.Types
import org.example.interpreter.Interpreter
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class interpreterTest {
    @Test
    fun `test execute with assignment`() {
        var lefttree = CompositeAbstractSyntaxTree(Token(Types.KEYWORD, "let", Position(1,0), Position(1,4)), left = CompositeAbstractSyntaxTree(Token(Types.LITERAL, "x", Position(1,5), Position(1,6))), right = CompositeAbstractSyntaxTree(Token(Types.DATA_TYPE, "string", Position(1,7), Position(1,13))))
        var righttree = CompositeAbstractSyntaxTree(Token(Types.LITERAL, "value", Position(1,14), Position(1,18)))
        val trees = listOf(
            CompositeAbstractSyntaxTree(Token(Types.OPERATOR, "=", Position(1,6), Position(1,7)), left = lefttree, right = righttree)
        )
        val interpreter = Interpreter(trees)
        interpreter.execute()

        assertEquals("value", interpreter.mapValuesAndVariables["x"])
    }
    @Test
    fun `test execute with println`() {
        val trees = listOf(
            CompositeAbstractSyntaxTree(Token( Types.OUTPUT, "println", Position(1,1), Position(1,6)), left = CompositeAbstractSyntaxTree(Token( Types.LITERAL,"Hello world!", Position(1,1), Position(1,1))))
        )
        val interpreter = Interpreter(trees)

        assertEquals("Hello world!", interpreter.execute().string)
    }
}