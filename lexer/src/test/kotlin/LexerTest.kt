package org.example
import org.junit.jupiter.api.Assertions
import kotlin.test.Test

class LexerTest {
    private val lexer = Lexer2(ValueMapper())

    @Test
    fun simpleLexing() {
        val result = lexer.execute("let     name  = \"Pe  dro \"")
        Assertions.assertEquals(4, result.size)
        Assertions.assertEquals(Types.KEYWORD, result[0].getType())
        Assertions.assertEquals(Types.IDENTIFIER, result[1].getType())
        Assertions.assertEquals(Types.OPERATOR, result[2].getType())
        Assertions.assertEquals(Types.LITERAL, result[3].getType())
    }

    @Test
    fun emptyInput() {
        val result = lexer.execute("")
        Assertions.assertTrue(result.isEmpty())
    }

    @Test
    fun onlyWhitespacesInput() {
        val result = lexer.execute("    \t\n")
        Assertions.assertTrue(result.isEmpty())
    }

    @Test
    fun inputWithMultipleLines() {
        val input =
            """
            let x = 10;
            let y = 20;
            """.trimIndent()
        val result = lexer.execute(input)
        Assertions.assertEquals(10, result.size)
        Assertions.assertEquals(Types.KEYWORD, result[0].getType())
        Assertions.assertEquals(Types.IDENTIFIER, result[1].getType())
        Assertions.assertEquals(Types.OPERATOR, result[2].getType())
        Assertions.assertEquals(Types.LITERAL, result[3].getType())
        Assertions.assertEquals(Types.PUNCTUATOR, result[4].getType())
        Assertions.assertEquals(Types.KEYWORD, result[5].getType())
        Assertions.assertEquals(Types.IDENTIFIER, result[6].getType())
        Assertions.assertEquals(Types.OPERATOR, result[7].getType())
        Assertions.assertEquals(Types.LITERAL, result[8].getType())
        Assertions.assertEquals(Types.PUNCTUATOR, result[9].getType())
    }

    @Test
    fun testLexerWithFunction() {
        val input =
            """
            let x = 10;
            println(x);
            """.trimIndent()
        val result = lexer.execute(input)
        System.out.println(result.map { it.getValue() })
        Assertions.assertEquals(10, result.size)
        Assertions.assertEquals(Types.KEYWORD, result[0].getType())
        Assertions.assertEquals(Types.IDENTIFIER, result[1].getType())
        Assertions.assertEquals(Types.OPERATOR, result[2].getType())
        Assertions.assertEquals(Types.LITERAL, result[3].getType())
        Assertions.assertEquals(Types.PUNCTUATOR, result[4].getType())
        Assertions.assertEquals(Types.FUNCTION, result[5].getType())
        Assertions.assertEquals(Types.PUNCTUATOR, result[6].getType())
        Assertions.assertEquals(Types.IDENTIFIER, result[7].getType())
        Assertions.assertEquals(Types.PUNCTUATOR, result[8].getType())
        Assertions.assertEquals(Types.PUNCTUATOR, result[9].getType())
    }

    @Test
    fun testCompositeLexer() {
        val input = "println(\"Hello\" + \"World\");"
        val result = lexer.execute(input)
        System.out.println(result.map { it.getValue() })
        Assertions.assertEquals(7, result.size)
        Assertions.assertEquals(Types.FUNCTION, result[0].getType())
        Assertions.assertEquals(Types.PUNCTUATOR, result[1].getType())
        Assertions.assertEquals(Types.LITERAL, result[2].getType())
        Assertions.assertEquals(Types.OPERATOR, result[3].getType())
        Assertions.assertEquals(Types.LITERAL, result[4].getType())
        Assertions.assertEquals(Types.PUNCTUATOR, result[5].getType())
        Assertions.assertEquals(Types.PUNCTUATOR, result[6].getType())
    }
}
