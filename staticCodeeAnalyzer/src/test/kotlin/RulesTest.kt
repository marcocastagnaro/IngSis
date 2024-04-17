package org.example

import org.example.parser.Parser
import org.example.rules.rulesImpls.CamelCase
import org.example.rules.rulesImpls.PrintWithoutExpresion
import org.example.rules.rulesImpls.SnakeCase
import org.junit.jupiter.api.Test
import rules.rulesImpls.ReadInputWithoutExpresion
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RulesTest {
    val lexer = Lexer("1.1")
    val parser = Parser()
    val parseTreeToTokens = ParseTreeToTokens()

    @Test
    fun test001_testNotInCamelCase() {
        val input = "let my_variable:string = \"this is a variable\"; "
        val tokens = lexer.execute(input)
        val trees = parser.execute(tokens)
        val tokensList = parseTreeToTokens.parseToTokens(trees[0])
        val camelCase = CamelCase()
        val brokenRules = camelCase.applyRule(listOf(tokensList))
        assertTrue(brokenRules.isNotEmpty())
        assertEquals("The following identifier must be in camelCase", brokenRules[0].getBrokenRule())
        assertEquals(Position(0, 4), brokenRules[0].getErrorPosition())
    }

    @Test
    fun test002_testIsInCamelCase() {
        val input = "let variableMyString:string = \"this is a variable\"; "
        val tokens = lexer.execute(input)
        val trees = parser.execute(tokens)
        val tokensList = parseTreeToTokens.parseToTokens(trees[0])
        val camelCase = CamelCase()
        val brokenRules = camelCase.applyRule(listOf(tokensList))
        assertTrue(brokenRules.isEmpty())
    }

    @Test
    fun test003_test_not_in_snake_case() {
        val input = "let variableMyString:string = \"this is a variable\"; "
        val tokens = lexer.execute(input)
        val trees = parser.execute(tokens)
        val tokensList = parseTreeToTokens.parseToTokens(trees[0])
        val snakeCase = SnakeCase()
        val brokenRules = snakeCase.applyRule(listOf(tokensList))
        assertTrue(brokenRules.isNotEmpty())
        assertEquals("The following identifier must be in snake_case", brokenRules[0].getBrokenRule())
        assertEquals(Position(0, 4), brokenRules[0].getErrorPosition())
    }

    @Test
    fun test004_test_is_in_snake_case() {
        val input = "let variable_my_string:string = \"this is a variable\"; "
        val tokens = lexer.execute(input)
        val trees = parser.execute(tokens)
        val tokensList = parseTreeToTokens.parseToTokens(trees[0])
        val snakeCase = SnakeCase()
        val brokenRules = snakeCase.applyRule(listOf(tokensList))
        assertTrue(brokenRules.isEmpty())
    }

    @Test
    fun test005_test_not_print_exp() {
        val input = "println(\"hola\" + \"juan\"); "
        val tokens = lexer.execute(input)
        val trees = parser.execute(tokens)
        val tokensList = parseTreeToTokens.parseToTokens(trees[0])
        val printWithoutExpresion = PrintWithoutExpresion()
        val brokenRules = printWithoutExpresion.applyRule(listOf(tokensList))
        assertTrue(brokenRules.isNotEmpty())
        assertEquals("Printlns must not be called with an expresion", brokenRules[0].getBrokenRule())
        assertEquals(Position(0, 0), brokenRules[0].getErrorPosition())
    }

    @Test
    fun test006_test_print_exp() {
        val input = "println(\"hola + juan\"); "
        val tokens = lexer.execute(input)
        val trees = parser.execute(tokens)
        val tokensList = parseTreeToTokens.parseToTokens(trees[0])
        val printWithoutExpresion = PrintWithoutExpresion()
        val brokenRules = printWithoutExpresion.applyRule(listOf(tokensList))
        assertTrue(brokenRules.isEmpty())
    }

    @Test
    fun test007_test_not_read_input_exp() {
        val input = "let variableMyString:string = readInput(\"hola\" + \"juan\"); "
        val tokens = lexer.execute(input)
        val trees = parser.execute(tokens)
        val tokensList = parseTreeToTokens.parseToTokens(trees[0])
        val readInputWithoutExpresion = ReadInputWithoutExpresion()
        val brokenRules = readInputWithoutExpresion.applyRule(listOf(tokensList))
        assertTrue(brokenRules.isNotEmpty())
        assertEquals("ReadInputs must not be called with an expresion", brokenRules[0].getBrokenRule())
        assertEquals(Position(0, 0), brokenRules[0].getErrorPosition())
    }

    @Test
    fun test008_test_read_input_exp() {
        val input = "let variableMyString:string = readInput(\"hola + juan\"); "
        val tokens = lexer.execute(input)
        val trees = parser.execute(tokens)
        val tokensList = parseTreeToTokens.parseToTokens(trees[0])
        val readInputWithoutExpresion = ReadInputWithoutExpresion()
        val brokenRules = readInputWithoutExpresion.applyRule(listOf(tokensList))
        assertTrue(brokenRules.isEmpty())
    }
}
