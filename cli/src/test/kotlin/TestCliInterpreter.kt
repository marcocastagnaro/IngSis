package org.example

import com.github.ajalt.clikt.testing.test
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TestCliInterpreter {
    @Test
    fun `test 001 test a simple println from lexer to interpreter`() {
        val cli = CLI()
        val result = cli.test("execute src/test001/resources/test001.txt")
        assertEquals("\"Hello, World!\"\n", result.stdout) //wtf porque me funciona el test solo si le salto una linea
    }
    fun `test 002 assignation from lexer to interpreter`() {
        val cli = CLI()
        val result = cli.test("execute src/test/resources/test2.txt")
        assertEquals("10\n", result.stdout)
    }
}