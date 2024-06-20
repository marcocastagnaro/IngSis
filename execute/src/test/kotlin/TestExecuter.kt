package org.example.executer

import org.junit.jupiter.api.Assertions.assertEquals
import java.io.File
import java.io.FileInputStream
import kotlin.test.Test

class TestExecuter {
    fun getFile(fileName: String): FileInputStream {
        return FileInputStream(File(fileName))
    }

    @Test
    fun testExecuter_001() {
        val executer = Executer()
        val file = getFile("src/test/resources/test001.txt")
        val result = executer.execute(file, "1.0")
        assertEquals("Hello, World!", result.string)
    }

    @Test
    fun testExecuter_002() {
        val executer = Executer()
        val file = getFile("src/test/resources/test015.txt")
        val result = executer.execute(file, "1.0")
        assertEquals("15", result.string)
    }

    @Test
    fun testExecuter_003() {
        val executer = Executer()
        val file = getFile("src/test/resources/test017.txt")
        val result = executer.execute(file, "1.1")
        assertEquals("outside of conditional", result.string)
    }

    @Test
    fun testExecuter_004() {
        val executer = Executer()
        val file = getFile("src/test/resources/test018.txt")
        val result = executer.execute(file, "1.1")
        assertEquals("JOAFAC_PUTO Y GAY", result.string)
    }
}
