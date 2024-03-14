import org.example.lexer.Lexer
import org.example.lexer.ValueMapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Spliting {
    val valueMapper = ValueMapper()
    val lex = Lexer(valueMapper)

    @Test
    fun simpleSplit() {
        val result = lex.split("a b c")
        Assertions.assertEquals(3, result.size)
    }

    @Test
    fun newLineTest() {
        val result = lex.split("hola\nMe llamo Pedro")
        Assertions.assertEquals(4, result.size)
        Assertions.assertEquals(0, result[0].getInitialPosition().getRow())
        Assertions.assertEquals(1, result[2].getInitialPosition().getRow())
    }

    @Test
    fun insideString() {
        val result = lex.split("val nombre = \"Pepe\"")
        Assertions.assertEquals(4, result.size)
    }
}