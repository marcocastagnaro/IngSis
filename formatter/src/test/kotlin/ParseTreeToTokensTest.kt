import org.example.CompositeAbstractSyntaxTree
import org.example.Leaf
import org.example.PrintNode
import org.example.Token.Position
import org.example.Token.Token
import org.example.Token.Types
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class ParseTreeToTokensTest {
    @Test
    fun `test 001 parse simple tree to tokens`() {
        val parser = ParseTreeToTokens()
        val tree =
            PrintNode(
                Token(Types.OUTPUT, "println", Position(1, 1), Position(1, 6)),
                right =
                    Leaf(
                        Token(
                            Types.LITERAL,
                            "Hello world!",
                            Position(1, 1),
                            Position(1, 1),
                        ),
                    ),
            )

        val result = parser.parseToString(tree)
        assertEquals("println", result[0].getValue())
        assertEquals("Hello world!", result[1].getValue())
    }

    @Test
    fun `test 002 parse complex tree to tokens`() {
        val parser = ParseTreeToTokens()
        val tree =
            PrintNode(
                Token(Types.OUTPUT, "println", Position(1, 1), Position(1, 6)),
                right =
                    CompositeAbstractSyntaxTree(
                        Token(
                            Types.OPERATOR,
                            value = "+",
                            Position(2, 2),
                            Position(3, 3),
                        ),
                        Leaf(
                            Token(
                                Types.LITERAL,
                                "Hello",
                                Position(1, 1),
                                Position(1, 1),
                            ),
                        ),
                        Leaf(
                            Token(
                                Types.LITERAL,
                                " world!",
                                Position(1, 1),
                                Position(1, 1),
                            ),
                        ),
                    ),
            )

        val result = parser.parseToString(tree)
        assertEquals("println", result[0].getValue())
        assertEquals("Hello", result[1].getValue())
        assertEquals("+", result[2].getValue())
        assertEquals(" world!", result[3].getValue())
    }
}
