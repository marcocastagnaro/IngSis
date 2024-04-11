import cli.PrintScript
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class TestReadFile {
    @Test
    fun testReadFile() {
        val printScript = PrintScript()
        val file = printScript.getFile("src/test/resources/test001.txt")

        assertEquals("println(\"Hello, World!\");", file)
    }

    @Test
    fun testReadFile2() {
        val printScript = PrintScript()
        val file = printScript.getFile("src/test/resources/test002.txt")

        assertEquals("let x : number = 10;", file)
    }

    @Test
    fun testReadFile3() {
        val printScript = PrintScript()
        val file = printScript.getFile("src/test/resources/test003.txt")

        assertEquals("let x : number = 10;println (x);", file)
    }
}
