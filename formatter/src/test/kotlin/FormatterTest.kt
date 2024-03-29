package org.example

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class FormatterTest {
    @Test
    fun `test 001 no formatting rules applied`() {
        val formatter = Formatter(emptyList())
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
                                "world!",
                                Position(1, 1),
                                Position(1, 1),
                            ),
                        ),
                    ),
            )
        val result = formatter.execute(tree)
        assertEquals("printlnHello+world!", result)
    }

    @Test
    fun `test 002 space rule applied`()  {
        val formatter = Formatter(emptyList())
        val tree =
            PrintNode(
                Token(Types.OUTPUT, "println", Position(1, 1), Position(1, 6)),
                right =
                    CompositeAbstractSyntaxTree(
                        Token(
                            Types.OPERATOR,
                            value = "+",
                            Position(1, 15),
                            Position(1, 15),
                        ),
                        Leaf(
                            Token(
                                Types.LITERAL,
                                "Hello",
                                Position(1, 8),
                                Position(1, 13),
                            ),
                        ),
                        Leaf(
                            Token(
                                Types.LITERAL,
                                "world!",
                                Position(1, 17),
                                Position(1, 23),
                            ),
                        ),
                    ),
            )
        val result = formatter.execute(tree)
        assertEquals("println Hello + world!", result)
    }
}
