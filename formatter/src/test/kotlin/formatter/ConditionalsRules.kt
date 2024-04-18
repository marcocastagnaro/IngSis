package formatter

import org.example.IfParserToTokens
import org.example.Lexer
import org.example.formatter.Formatter
import org.example.parser.Parser

class ConditionalsRules {
    private val lexer = Lexer("1.1")
    private val parser = Parser()
    private val formatter = Formatter(parser = IfParserToTokens())

   /* @Test
    fun test001_ifIndentation() {
        val input =
            """

            let x: boolean = false;
            if (x) {
                println("Hola");
                 }
                else {
                println("Chau");
                }

            """.trimIndent()
        val tokens = lexer.execute(input)
        val trees = parser.execute(tokens)
        val result = formatter.execute(trees)
        //     assertEquals("let x: boolean = false; if(x){\n println(\"Hola\");\n else {\n println(\"chau\");\n}", result)
    }

    @Test
    fun test001_ConditionalIndentation() {
        val input =
            """

            let x: boolean = false;
            if (x) {
            println("Hola");
            }
            else
            {
            println("Chau");
            }

            """.trimIndent()

        val expectedOutput =
            """

            let x: boolean = false;
            if (x) {
                println("Hola");
            }
            else
            {
                println("Chau");
            }

            """.trimIndent()

        val tokens = lexer.execute(input)
        val trees = parser.execute(tokens)
        val result = formatter.execute(trees)
        //     assertEquals(expectedOutput, result)
    }*/
}
