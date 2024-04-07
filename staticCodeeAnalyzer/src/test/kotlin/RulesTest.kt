
import org.example.Lexer
import org.example.Parser
import org.example.ValueMapper
import org.example.formatter.ParseTreeToTokens
import org.example.rules.rulesImpls.CamelCase
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue


class RulesTest {
    val lexer = Lexer(ValueMapper())
    val parser = Parser()
    val parseTreeToTokens = ParseTreeToTokens()


    @Test
    fun test001_testNotInCamelCase() {
        val input = "let variable_my_string:string = \"this is a variable\"; "
        val tokens = lexer.execute(input)
        val trees = parser.execute(tokens)
        val tokensList = parseTreeToTokens.parseToString(trees[0])
        val camelCase = CamelCase()
        val brokenRules = camelCase.applyRule(listOf(tokensList))
        assertTrue(brokenRules.isNotEmpty())


    }/*

    @Test
    fun test002_testIsInCamelCase() {
        val input = "let variableMyString:string = \"this is a variable\"; "
        val tokens = lexer.execute(input)
        val trees = parser.execute(tokens)

        assertEquals("let variable : number=7;\n", result)
    }

    @Test
    fun test003_test_not_in_snake_case() {
        val input = "let variable_my_string:string = \"this is a variable\"; "
        val tokens = lexer.execute(input)
        val trees = parser.execute(tokens)
        val sca =  Sca()
        sca.setRules()
        sca.applyRules()
        assertEquals(sca.getRules(), result)
    }

    @Test
    fun test004_test_is_in_snake_case() {
        val input = "let variableMyString:string = \"this is a variable\"; "
        val tokens = lexer.execute(input)
        val trees = parser.execute(tokens)

        assertEquals("let variable : number=7;\n", result)
    }

    @Test
    fun test005_test_not_print_exp() {
        val input = "let variableMyString:string = \"this is a variable\"; "
        val tokens = lexer.execute(input)
        val trees = parser.execute(tokens)

        assertEquals("let variable : number=7;\n", result)
    }
*/

}