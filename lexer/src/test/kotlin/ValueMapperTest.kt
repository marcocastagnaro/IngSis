import org.example.Lexer
import org.example.ValueMapper

class ValueMapperTest {
    val valueMapper = ValueMapper()
    val lexer = Lexer(valueMapper)

//    @Test
//    fun simpleStringTest() {
//        val tokens = lexer.execute("val nombre = \"Pedro\" \n109")
//        val result = valueMapper.assigningTypesToTokenValues(tokens)
//        for (token in result) {
//            println(token.getValue() + "|" + token.getType())
//        }
//        Assertions.assertEquals(5, result.size)
//    }
}
