package validator

import org.example.Lexer
import org.example.Parser
import org.example.ValueMapper
import org.example.validator.Validator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ValidatorTest {
    val formatter = Lexer(ValueMapper())
    val parser = Parser()

    @Test
    fun `test 001 - should return true when the input is valid`() {
        val input =
            """"
            let values : number = 1 + 2+ 3;
            let literal : string = "hello World";
            """.trimMargin()
        val tokens = formatter.execute(input)
        val trees = parser.execute(tokens)
        val validator = Validator()
        val result = validator.validate(trees)
        assertEquals(true, result)
    }

    @Test
    fun `test 002 - should return false when input is invalid`() {
        val input =
            """
            let values : string = "hello World";
            let values : number = 1 + 2+ '3';
            """.trimMargin()
        val tokens = formatter.execute(input)
        val trees = parser.execute(tokens)
        val validator = Validator()
        val result = validator.validate(trees)
        assertEquals(false, result)
    }

    @Test
    fun `test 003 - should return true when no assignation is present`() {
        val input =
            """
            let values : number;
            let values1 : string;
            """.trimMargin()
        val tokens = formatter.execute(input)
        val trees = parser.execute(tokens)
        val validator = Validator()
        val result = validator.validate(trees)
        assertEquals(true, result)
    }

    @Test
    fun `test 004 - should return true when no declaration is present`() {
        val input =
            """
            value1 = 1 + 2+ "3";
            value2 = "hello World";
            """.trimMargin()
        val tokens = formatter.execute(input)
        val trees = parser.execute(tokens)
        val validator = Validator()
        val result = validator.validate(trees)
        assertEquals(true, result)
    }
}
