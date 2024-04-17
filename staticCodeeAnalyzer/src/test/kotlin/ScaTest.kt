

import org.example.Leaf
import org.example.Lexer
import org.example.Parser
import org.example.Position
import org.example.PrintNode
import org.example.Token
import org.example.Types
import org.example.sca.ScaImpl
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ScaTest {
    @Test
    fun test001_readJSON() {
        val sca = ScaImpl()
        sca.readJson("src/main/resources/linternRules.json")
        assert(sca.getRules().size == 3)
        assert(sca.getRules()[0].javaClass.simpleName == "CamelCase")
        assert(sca.getRules()[1].javaClass.simpleName == "PrintWithoutExpresion")
        assert(sca.getRules()[2].javaClass.simpleName == "ReadInputWithoutExpresion")
    }

    @Test
    fun test002_applyRules() {
        val sca = ScaImpl()
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
        val sca = ScaImpl()
        sca.readJson("src/main/resources/linternRules.json")
        val input = "println(\"hola\" + \"juan\"); "
        val lexer = Lexer("1.1")
        val parser = Parser()
        val tokens = lexer.execute(input)
        val trees = parser.execute(tokens)

        val output = sca.check(trees)
        assertFalse(output.isOk())
        assertTrue(output.getBrokenRules().size == 1)
        assertEquals("Printlns must not be called with an expresion at line 0", output.getBrokenRules()[0])
    }
}
