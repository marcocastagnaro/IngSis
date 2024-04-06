package org.example

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class InterpreterTest {
    @Test
    fun testPrintOutput() {
        val trees =
            listOf(
                PrintNode(
                    Token(Types.FUNCTION, "println", Position(1, 1), Position(1, 6)),
                    child =
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
        val interpreter = Interpreter()

        assertEquals("Hello world!", interpreter.execute(trees).string)
    }

    @Test
    fun testConcatenation() {
        val trees =
            listOf(
                PrintNode(
                    Token(Types.FUNCTION, "println", Position(1, 1), Position(1, 6)),
                    child =
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
        val interpreter = Interpreter()

        assertEquals("Hello world!", interpreter.execute(trees).string)
    }

    @Test
    fun testPrintVariable() {
        val trees =
            listOf(
                CompositeAbstractSyntaxTree(
                    Token(Types.ASSIGNATION, "=", Position(1, 1), Position(1, 6)),
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
                        CompositeAbstractSyntaxTree(
                            Token(
                                Types.KEYWORD,
                                value = "let",
                                Position(1, 1),
                                Position(1, 1),
                            ),
                            right =
                                CompositeAbstractSyntaxTree(
                                    Token(
                                        Types.DECLARATOR,
                                        value = ":",
                                        Position(1, 1),
                                        Position(1, 1),
                                    ),
                                    right =
                                        Leaf(
                                            Token(
                                                Types.DATA_TYPE,
                                                value = "string",
                                                Position(1, 1),
                                                Position(1, 1),
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
                        ),
                ),
                PrintNode(
                    Token(Types.FUNCTION, "println", Position(1, 1), Position(1, 6)),
                    child =
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
        val interpreter = Interpreter()

        assertEquals("Hello world!", interpreter.execute(trees).string)
    }
}
