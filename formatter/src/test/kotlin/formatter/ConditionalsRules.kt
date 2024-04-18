package formatter

import org.example.Lexer
import org.example.formatter.Formatter
import org.example.parser.Parser
import org.junit.jupiter.api.Test

class ConditionalsRules {
    private val lexer = Lexer("1.1")
    private val parser = Parser()
    private val formatter = Formatter()

    @Test
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
        //  assertEquals("let x: boolean = false; if(x){\n println(\"Hola\");\n else {\n println(\"chau\");\n}", result)
    }

    @Test
    fun test001_ifSameLine() {
        val input =
            """
                            
            let x: boolean = false; 
            if (x) 
                { 
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
        //  assertEquals("let x: boolean = false; if(x){\n println(\"Hola\");\n else {\n println(\"chau\");\n}", result)
    }
}
