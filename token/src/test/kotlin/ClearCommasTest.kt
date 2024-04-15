import org.example.utils.ClearCommas
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class ClearCommasTest {
    @Test
    fun `test 001 should not throw error if string is empty`() {
        val input = ""
        val expected = ""
        val result = ClearCommas().clearCommas(input)
        assertEquals(expected, result)
    }
}
