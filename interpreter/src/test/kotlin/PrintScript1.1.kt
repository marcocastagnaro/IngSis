package org.example

import org.example.inputReader.DummyInputReader
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PrintScript1 {
    @Test
    fun `test 001 readlinePrint`() {
        val lexer = Lexer("1.1")
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

    @Test
    fun `a failing test from tck`() {
        val input = """
               let numberResult: number = 5 * 5 - 8;
               println(numberResult);"""
        val lexer = Lexer("1.1")
        val interpreter = Interpreter(DummyInputReader())
        val parser = Parser()
        val tokens = lexer.execute(input)
        val trees = parser.execute(tokens)
        val result = interpreter.execute(trees).string
        assertEquals("17", result)
    }

    @Test
    fun `a simple if test from tck`() {
        val input: MutableList<String> =
            @Suppress("ktlint:standard:max-line-length")
            "const booleanValue: boolean = true;\r\nif(booleanValue) {\r\nprintln(\"if statement is not working correctly\");}\r\nprintln(\"outside of conditional\");".split(
                "if(",
            ).toMutableList()
        val lexer = Lexer("1.1")
        val interpreter = Interpreter(DummyInputReader())
        var result = ""
        input.set(1, "if(" + input[1])
        input.forEach {
            val tokens = lexer.execute(it)
            val trees = Parser().execute(tokens)
            result += interpreter.execute(trees).string + "\n"
        }
        assertEquals("\nif statement is not working correctly\noutside of conditional\n", result)
    }

    @Test
    fun `make an invalid code`() {
        try {
            val input =
                """
                const name: string;
                name = 13;
                """.trimIndent()
            val lexer = Lexer("1.1")
            val interpreter = Interpreter(DummyInputReader())
            val tokens = lexer.execute(input)
            val trees = Parser().execute(tokens)
            val result = interpreter.execute(trees)
            assertEquals("Invalid syntax", result.string)
        } catch (e: Exception) {
            assertEquals("Invalid syntax", e.message)
        }
    }

    @Test
    fun `make an invalid code because variable doesn't exist`() {
        try {
            val input =
                """
                const name: string;
                player = 13;
                """.trimIndent()
            val lexer = Lexer("1.1")
            val interpreter = Interpreter(DummyInputReader())
            val tokens = lexer.execute(input)
            val trees = Parser().execute(tokens)
            val result = interpreter.execute(trees)
            assertEquals("Invalid syntax", result.string)
        } catch (e: Exception) {
            assertEquals("Variable player not declared", e.message)
        }
    }

    @Test
    fun `simple passing validation`() {
        val input = "let x: number = 8;"
        val lexer = Lexer("1.1")
        val interpreter = Interpreter(DummyInputReader())
        val tokens = lexer.execute(input)
        val trees = Parser().execute(tokens)
        val result = interpreter.execute(trees).string
    }
}
