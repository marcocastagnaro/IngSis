package org.example

import org.example.inputReader.DummyInputReader
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PrintScript1 {
    @Test
    fun `test 001 readlinePrint`() {
        val lexer = Lexer(ValueMapper())
        val interpreter = Interpreter(DummyInputReader())
        var result = ""
        val input =
            """
            const name: string = readInput("Name:");
            println("Hello " + name + "!");
            """.trimIndent().split("\n")
        input.forEach {
            val tokens = lexer.execute(it)
            val trees = Parser().execute(tokens)
            result += interpreter.execute(trees).string
        }

        assertEquals("Name:Hello dummy input!", result)
    }
}
