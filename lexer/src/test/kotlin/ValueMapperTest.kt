import org.example.Lexer
import org.example.ValueMapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ValueMapperTest {
    val valueMapper = ValueMapper()
    val lexer = Lexer(valueMapper)

    @Test
    fun simpleStringTest()  {
        val tokens = lexer.split("val nombre = \"Pedro\" \n109")
        val result = valueMapper.assigningTypesToTokenValues(tokens)
        for (token in result) {
            println(token.getValue() + "|" + token.getType())
        }
        Assertions.assertEquals(5, result.size)
    }
}
