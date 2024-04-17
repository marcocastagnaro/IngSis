package org.example

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ASTtests {
    @Test
    fun `test 001 print abstract syntax tree`()  {
        val token = Token(Types.FUNCTION, "print", Position(0, 0), Position(0, 5))
        val printNode = PrintNode(token)
        val token2 = Token(Types.LITERAL, "Hello World", Position(0, 0), Position(0, 5))
        val stringNode = NodeBuilder().setValue(token2).build()
        printNode.setRight(stringNode)
        assertTrue(printNode.getRight() == stringNode)
    }

    @Test
    fun `test 002 assignation abstract syntax tree`()  {
        val trees =
            listOf(
                CompositeAbstractSyntaxTree(
                    Token(Types.ASSIGNATION, "=", Position(1, 1), Position(1, 6)),
                    right =
                        Leaf(
                            Token(
                                Types.LITERAL,
                                value = "6",
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
                                        Types.IDENTIFIER,
                                        value = ":",
                                        Position(1, 1),
                                        Position(1, 1),
                                    ),
                                    right =
                                        Leaf(
                                            Token(
                                                Types.DATA_TYPE,
                                                value = "number",
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
                CompositeAbstractSyntaxTree(
                    Token(Types.ASSIGNATION, "=", Position(1, 1), Position(1, 6)),
                    right =
                        Leaf(
                            Token(
                                Types.LITERAL,
                                value = "3",
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
                                        Types.IDENTIFIER,
                                        value = ":",
                                        Position(1, 1),
                                        Position(1, 1),
                                    ),
                                    right =
                                        Leaf(
                                            Token(
                                                Types.DATA_TYPE,
                                                value = "number",
                                                Position(1, 1),
                                                Position(1, 1),
                                            ),
                                        ),
                                    left =
                                        Leaf(
                                            Token(
                                                Types.IDENTIFIER,
                                                value = "y",
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
                                value = "-",
                                Position(2, 2),
                                Position(3, 3),
                            ),
                            Leaf(
                                Token(
                                    Types.IDENTIFIER,
                                    "x",
                                    Position(1, 1),
                                    Position(1, 1),
                                ),
                            ),
                            Leaf(
                                Token(
                                    Types.IDENTIFIER,
                                    "y",
                                    Position(1, 1),
                                    Position(1, 1),
                                ),
                            ),
                        ),
                ),
            )
        assertTrue { trees.size == 3 }
        assertEquals("6", trees[0].getRight()?.getToken()?.getValue())
        assertTrue { trees[0].getLeft()?.getRight()?.getLeft()?.getToken()?.getValue() == "x" }
    }
}
