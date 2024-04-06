import org.example.*
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class TestCliInterpreter {
    @Test
    fun `test 001 test a simple println from lexer to interpreter`() {
        val cli = CLI()
        assertEquals("\"Hello world!\"", cli.run())
    }
}