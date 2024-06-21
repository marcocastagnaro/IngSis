package org.example.executer

import org.junit.jupiter.api.Assertions.assertEquals
import java.io.File
import java.io.FileInputStream
import kotlin.test.Test

class TestFormatterExecuter {
    fun getFile(fileName: String): FileInputStream {
        return FileInputStream(File(fileName))
    }
    @Test
    fun testFormatExecuter_001() {
        val executer = FormatterExecuter()
        val file = getFile("src/test/resources/file009.txt")
        val result = executer.execute(file, "1.1")
        assertEquals("let x: number = 8;", result.string)
    }
//    @Test
//    fun testFormatExecuter_002(){
//        val executer = FormatterExecuter()
//        val file = getFile("src/test/resources/file010.txt")
//        val result = executer.execute(file, "1.1", "src/test/resources/MyRules.json")
//        assertEquals("let variable: string=\"this is a variable\";", result.string)
//    }
}