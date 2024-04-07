import org.example.CLI
import org.junit.jupiter.api.Assertions.assertEquals
import java.io.File
import kotlin.test.Test

class TestReadFile {
    @Test
    fun testReadFile() {
        val cli = CLI()
        val file = cli.getFile("src/test/resources/test001.txt")

        assertEquals("println(\"Hello, World!\")", file)
    }
}