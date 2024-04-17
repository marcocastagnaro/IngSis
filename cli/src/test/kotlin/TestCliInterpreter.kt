package org.example

import cli.PrintScript
import com.github.ajalt.clikt.testing.test
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TestCliInterpreter {
    @Test
    fun `test 001 test a simple println from lexer to interpreter`() {
        val printScript = PrintScript()
        val result = printScript.test("1.1 execute src/test/resources/test001.txt")
        assertEquals("Hello, World!\n", result.stdout) // wtf porque me funciona el test solo si le salto una linea
    }

    @Test
    fun `test 002 printing an assignation must be empty`() {
        val printScript = PrintScript()
        val result = printScript.test("1.1 execute src/test/resources/test002.txt")
        assertEquals("\n", result.stdout)
    }

    @Test
    fun `test003 printing variable x with assignation variable before`() {
        val printScript = PrintScript()
        val result = printScript.test("1.0 execute src/test/resources/test003.txt")
        assertEquals("10\n", result.stdout)
    }

    @Test
    fun `test 004 testing sum of variables`() {
        val cli = PrintScript()
        val result = cli.test("1.0 execute src/test/resources/test004.txt")
        assertEquals("15\n", result.stdout)
    }

    @Test
    fun `test 005 testing a varaible with multiplication`() {
        val cli = PrintScript()
        val result = cli.test("1.1 execute src/test/resources/test005.txt")
        assertEquals("50\n", result.stdout)
    }

//    @Test
//    fun `test 006 testing a println with concatenation`() {
//        val cli = PrintScript()
//        val result = cli.test("execute src/test/resources/test006.txt")
//        assertEquals("Hello, world!\n", result.stdout)
//    }

    fun `test a declaration and then an assignation`() {
        val cli = PrintScript()
        val result = cli.test("1.1 execute src/test/resources/test007.txt")
        assertEquals("10\n", result.stdout)
    }

//    @Test
//    fun `test 008 testing a sum of number + string has to return a string concatenated ` (){
//        val cli = PrintScript()
//        val result = cli.test("1.1 execute src/test/resources/test008.txt")
//        assertEquals("25\n", result.stdout)
//    }

    @Test
    fun `test 009 formatter simple format with no json`() {
        val printScript = PrintScript()
        val result = printScript.test("1.1 format src/test/resources/test009.txt")
        assertEquals("let x: number = 8;\n\n", result.stdout)
    }

    @Test
    fun `test010 formatter with my own json`() {
        val printScript = PrintScript()
        val result = printScript.test("1.1 format src/test/resources/test010.txt src/test/resources/MyRules.json")
        assertEquals("let variable: string=\"this is a variable\";\n\n", result.stdout)
    }
    // Formatter funciona el resto de los tests estan en su clase

    @Test
    fun `011 test weird txt with words separate`() {
        val printScript = PrintScript()
        val result = printScript.test("1.1 execute src/test/resources/test011.txt")
        assertEquals("10\n", result.stdout)
    }

    @Test
    fun `012 test formatter a weird text`() {
        val printScript = PrintScript()
        val result = printScript.test("1.1 format src/test/resources/test011.txt")
        assertEquals("let variable: number = 10;\n\nprintln(variable);\n\n", result.stdout)
    }

    @Test
    fun `012 test simple sca`() {
        val printScript = PrintScript()
        val result = printScript.test("1.1 analyze src/test/resources/test012.txt src/test/resources/linterRules.json")
        assertEquals("Printlns must not be called with an expresion at line 0\n", result.stdout)
    }

    @Test
    fun `013 test read env`() {
        val printScript = PrintScript()
        val result = printScript.test("1.1 execute src/test/resources/test013.txt")
        assertEquals("JOAFAC_PUTO\n", result.stdout)
    }

    @Test
    fun `014 test read env with more variables `() {
        val printScript = PrintScript()
        val result = printScript.test("1.1 execute src/test/resources/test014.txt")
        assertEquals("JOAFAC_PUTO Y GAY\n", result.stdout)
    }

    @Test
    fun `test 015 testing declaration, assignation and re assignation`() {
        val cli = PrintScript()
        val result = cli.test("1.1 execute src/test/resources/test0015.txt")
        assertEquals("15\n", result.stdout)
    }
}
