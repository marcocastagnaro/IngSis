
import org.example.*
import org.example.sca.Sca
import org.example.sca.ScaImpl
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ScaTest {

    @Test
    fun test001_readJSON() {
        val sca = ScaImpl()
        sca.readJson("src/main/resources/linternRules.json")
        assert(sca.getRules().size == 2)
        assert(sca.getRules()[0].javaClass.simpleName == "CamelCase")
        assert(sca.getRules()[1].javaClass.simpleName == "PrintWithoutExpresion")


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




}