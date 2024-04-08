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
        val interpreter = Interpreter2()

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
        val interpreter = Interpreter2()

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
        val interpreter = Interpreter2()

        assertEquals("Hello world!", interpreter.execute(trees).string)
    }

    @Test
    fun testSumVariables() {
        val input =
            """
            let x: number = 2;
            let y: number = 3;
            println(x + y);
            """.trimIndent()
        val lexer = Lexer(ValueMapper())
        val tokens = lexer.execute(input)
        println(tokens.map { it.getValue() })
        val parser = Parser()
        val trees = parser.execute(tokens)
        val interpreter = Interpreter2()
        val result = interpreter.execute(trees)
        assertEquals("5", result.string)
    }

    @Test
    fun testMultiplicationVariables() {
        val input =
            """
            let x: number = 2;
            let y: number = 3;
            println(x * y);
            """.trimIndent()
        val trees =
            listOf(
                CompositeAbstractSyntaxTree(
                    Token(Types.ASSIGNATION, "=", Position(1, 1), Position(1, 6)),
                    right =
                        Leaf(
                            Token(
                                Types.LITERAL,
                                value = "2",
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
                                value = "*",
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
        val interpreter = Interpreter2()
        val result = interpreter.execute(trees)
        assertEquals("6", result.string)
    }

    @Test
    fun testDivisionVariables() {
        val input =
            """
            let x: number = 6;
            let y: number = 3;
            println(x / y);
            """.trimIndent()
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
                                value = "/",
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
        val interpreter = Interpreter2()
        val result = interpreter.execute(trees)
        assertEquals("2", result.string)
    }

    @Test
    fun testSubtractionVariables() {
        val input =
            """
            let x: number = 6;
            let y: number = 3;
            println(x - y);
            """.trimIndent()
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
        val interpreter = Interpreter2()
        val result = interpreter.execute(trees)
        assertEquals("3", result.string)
    }

    @Test
    fun testTripleSum() {
        val input =
            """
            let x: number = 6;
            let y: number = 3;
            let z: number = 1;
            println(x + y + z);
            """.trimIndent()
        val result = Interpreter2().execute(Parser().execute(Lexer(ValueMapper()).execute(input)))
        assertEquals("10", result.string)
    }
}
