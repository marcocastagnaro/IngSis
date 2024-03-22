import org.example.Lexer
import org.example.ValueMapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Splitting {
    val valueMapper = ValueMapper()
    val lex = Lexer(valueMapper)

    @Test
    fun simpleSplit() {
        val result = lex.execute("a b c")
        Assertions.assertEquals(3, result.size)
    }

    @Test
    fun newLineTest() {
        val result = lex.execute("hola\nMe llamo Pedro")
        Assertions.assertEquals(4, result.size)
        Assertions.assertEquals(0, result[0].getInitialPosition().getRow())
        Assertions.assertEquals(1, result[2].getInitialPosition().getRow())
    }

    @Test
    fun insideString() {
        val result = lex.execute("val nombre = \"Pepe\"")
        Assertions.assertEquals(4, result.size)
    }
}
