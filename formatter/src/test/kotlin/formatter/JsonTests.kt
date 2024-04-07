package formatter

import org.example.formatter.JsonDeserialization
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class JsonTests {
    @Test
    fun `test 001`() {
        val rules = JsonDeserialization().getRulesFromJson("src/main/resources/formatter/StandardRules.json")
        assertEquals(2, rules.size)
    }
}
