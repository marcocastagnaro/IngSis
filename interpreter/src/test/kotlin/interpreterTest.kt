
import org.example.CompositeAbstractSyntaxTree
import org.example.Interpreter
import org.example.Leaf
import org.example.PrintNode
import org.example.Token.Position
import org.example.Token.Token
import org.example.Token.Types
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class InterpreterTest {
    @Test
    fun testPrintOutput() {
        val trees =
            listOf(
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
                ),
            )
        val interpreter = Interpreter(trees)

        assertEquals("Hello world!", interpreter.execute().string)
    }

    @Test
    fun testConcatenation() {
        val trees =
            listOf(
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
                ),
            )
        val interpreter = Interpreter(trees)

        assertEquals("Hello world!", interpreter.execute().string)
    }

    @Test
    fun testPrintVariable() {
        val trees =
            listOf(
                CompositeAbstractSyntaxTree(
                    Token(Types.ASSIGNATOR, "=", Position(1, 1), Position(1, 6)),
                    right =
                        Leaf(
                            Token(
                                Types.LITERAL,
                                value = " world!",
                                Position(2, 2),
                                Position(3, 3),
                            ),
                        ),
                    left =
                        Leaf(
                            Token(
                                Types.IDENTIFIER,
                                value = "x",
                                Position(1, 1),
                                Position(1, 1),
                            ),
                        ),
                ),
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
                                    Types.IDENTIFIER,
                                    "x",
                                    Position(1, 1),
                                    Position(1, 1),
                                ),
                            ),
                        ),
                ),
            )
        val interpreter = Interpreter(trees)

        assertEquals("Hello world!", interpreter.execute().string)
    }
}
