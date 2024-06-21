package org.example.executer

import org.junit.jupiter.api.Assertions.assertEquals
import java.io.File
import java.io.FileInputStream
import kotlin.test.Test

class TestLinterExecuter() {
    fun getFile(fileName: String): FileInputStream {
        return FileInputStream(File(fileName))
    }
    @Test
    fun testFormatExecuter_001() {
        val executer = LinterExecuter()
        val file = getFile("src/test/resources/testlinter.txt")
        val result = executer.execute(file, "1.1", "src/test/resources/linterRules.json")
        val brokenRules: MutableList<String> = result.flatMap { it.getBrokenRules() }.toMutableList()
        assertEquals("Println must not be called with an expression at line 0", brokenRules.joinToString("\n"))
    }
}