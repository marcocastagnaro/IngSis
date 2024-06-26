package org.example

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class ParseTreeToTokensTest {
    @Test
    fun `test 001 parse simple tree to tokens`() {
        val parser = ParseTreeToTokens()
        val tree =
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
            )

        val result = parser.parseToTokens(tree)
        assertEquals("println", result[0].getValue())
        assertEquals("Hello world!", result[1].getValue())
    }

    @Test
    fun `test 002 parse complex tree to tokens`() {
        val parser = ParseTreeToTokens()
        val tree =
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
            )

        val result = parser.parseToTokens(tree)
        assertEquals("println", result[0].getValue())
        assertEquals("Hello", result[1].getValue())
        assertEquals("+", result[2].getValue())
        assertEquals(" world!", result[3].getValue())
    }
}
