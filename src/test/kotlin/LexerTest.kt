import org.example.Token.Types
import org.example.lexer.Lexer
import org.example.lexer.ValueMapper
import org.junit.jupiter.api.Assertions
import kotlin.test.Test

class LexerTest {
    val valueMapper = ValueMapper()
    val lexer = Lexer(ValueMapper())

    @Test
    fun simpleLexing() {
        val result = lexer.lex("val name = \"Pedro\"")
        Assertions.assertEquals(4, result.size)
        Assertions.assertEquals(Types.KEYWORD, result[0].getType())
        Assertions.assertEquals(Types.IDENTIFIER, result[1].getType())
        Assertions.assertEquals(Types.OPERATOR, result[2].getType())
        Assertions.assertEquals(Types.LITERAL, result[3].getType())
    }
}