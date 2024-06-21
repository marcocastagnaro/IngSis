package org.example.staticCodeeAnalyzer

import org.example.Leaf
import org.example.Lexer
import org.example.Position
import org.example.PrintNode
import org.example.Token
import org.example.Types
import org.example.parser.Parser
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ScaTest {
    @Test
    fun test001_readJSON() {
        val sca = ScaImpl(ScaVersion.VERSION_1_1)
        sca.readJson("src/main/resources/linternRules.json")
        assert(sca.getRules().size == 3)
        assert(sca.getRules()[0].getRuleName() == "CamelCase")
        assert(sca.getRules()[1].getRuleName() == "PrintWithoutExpresion")
        assert(sca.getRules()[2].getRuleName() == "ReadInputWithoutExpresion")
    }

    @Test
    fun test002_applyRules() {
        val sca = ScaImpl(ScaVersion.VERSION_1_1)
        sca.readJson("src/main/resources/linternRules.json")
        val trees =
            listOf(
                PrintNode(
                    Token(Types.FUNCTION, "println", Position(1, 1), Position(1, 6)),
                    child =
                        Leaf(
                            Token(
                                Types.LITERAL,
                                "Hello" + "world!",
                                Position(1, 1),
                                Position(1, 1),
                            ),
                        ),
                ),
            )
        sca.check(trees)
        assertFalse(sca.getRules().isEmpty())
    }

    @Test
    fun test003_outputTest() {
        val sca = ScaImpl(ScaVersion.VERSION_1_1)
        sca.readJson("src/main/resources/linternRules.json")
        val input = "println(\"hola\" + \"juan\"); "
        val lexer = Lexer("1.1")
        val parser = Parser()
        val tokens = lexer.execute(input)
        val trees = parser.execute(tokens)

        val output = sca.check(trees)
        assertFalse(output.isOk())
        assertTrue(output.getBrokenRules().size == 1)
        assertEquals("Println must not be called with an expression at line 0", output.getBrokenRules()[0])
    }

    @Test
    fun test004_errorWhenWrongVersion() {
        val sca = ScaImpl(ScaVersion.VERSION_1_0)
        assertThrows<IllegalArgumentException> {
            sca.readJson("src/main/resources/linternRules.json")
        }
    }
}
