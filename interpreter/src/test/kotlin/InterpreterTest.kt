package org.example

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class InterpreterTest {
    val interpreter = Interpreter()
    val parser = Parser()
    val lexer = Lexer(ValueMapper())

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
        val interpreter = Interpreter()
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
        val interpreter = Interpreter()
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
        val interpreter = Interpreter()
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
        val interpreter = Interpreter()
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
        val result = Interpreter().execute(Parser().execute(Lexer(ValueMapper()).execute(input)))
        assertEquals("10", result.string)
    }

    @Test
    fun `test 001 -should be able to concatenate a string and a number`() {
        val input =
            """
            |let x: string = "Hello";
            |let y: number = 1;
            |println(x + y);
            """.trimMargin()
        val tokens = lexer.execute(input)
        val trees = parser.execute(tokens)
        val result = interpreter.execute(trees)
        assertEquals("Hello1", result.string)
    }

    @Test
    fun `test 002 -should be able to make a complex sum with different terms`() {
        val input =
            """
            let x: number = 4 + 3*2;
            let y: number = 10;
            println(x / y);
            """.trimIndent()
        val tokens = lexer.execute(input)
        val trees = parser.execute(tokens)
        val result = interpreter.execute(trees)
        assertEquals("1", result.string)
    }

    @Test
    fun `test 003 -should be able to concatenate a string with a number that comes from a product`() {
        val input =
            """
            let x: string = "The result of the operations is: "; 
            let y: number = 3 * 2;
            println(x + y);
            """.trimIndent()
        val tokens = lexer.execute(input)
        val trees = parser.execute(tokens)
        val result = interpreter.execute(trees)
        assertEquals("The result of the operations is: 6", result.string)
    }

    @Test
    fun `test 004 -should be able to make a complex sum with different terms`() {
        val input =
            """
            let x: number = 4 * 5 + 10 + 5*2;
            let y: number = 10;
            println(x / y);
            """.trimIndent()
        val tokens = lexer.execute(input)
        val trees = parser.execute(tokens)
        val result = interpreter.execute(trees)
        assertEquals("4", result.string)
    }

    @Test
    fun `test 005 testing red env variables`() {
        val input = "let x : string = readEnv(JOAFAC); println(x)"
        val tokens = lexer.execute(input)
        val trees = parser.execute(tokens)
        val result = interpreter.execute(trees)
        assertEquals("JOAFAC_PUTO", result.string)
    }

    @Test
    fun `test 006 - with readInput it shouldn't throw error and should ask for an input`() {
        val input = "let x: number = readInput(\"Please enter a value for x\"); println(x);"
        val tokens = lexer.execute(input)
        val trees = parser.execute(tokens)
        val result = interpreter.execute(trees)
        assertEquals("Falta agregar el readInput en el CLI", result.string)
    }

    @Test
    fun `test 007 -should printout the input`() {
        val input = "let x: string = readInput(); println(x);"
        val tokens = lexer.execute(input)
        val trees = parser.execute(tokens)
        val result = interpreter.execute(trees)
        assertEquals("Falta agregar el readInput en el CLI", result.string)
    }

    @Test
    fun `test 008 -should print directly the output from the println`() {
        val input = "println(readInput())"
        val tokens = lexer.execute(input)
        val trees = parser.execute(tokens)
        val result = interpreter.execute(trees)
        assertEquals("Falta agregar el readInput en el CLI", result.string)
    }
}
