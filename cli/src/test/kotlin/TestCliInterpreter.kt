package org.example

import com.github.ajalt.clikt.testing.test
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TestCliInterpreter {
    @Test
    fun `test 001 test a simple println from lexer to interpreter`() {
        val cli = CLI()
        val result = cli.test("execute src/test/resources/test001.txt")
        assertEquals("\"Hello, World!\"\n", result.stdout) // wtf porque me funciona el test solo si le salto una linea
    }

    @Test
    fun `test 002 printing an assignation must be empty`() {
        val cli = CLI()
        val result = cli.test("execute src/test/resources/test002.txt")
        assertEquals("\n", result.stdout)
    }

    @Test
    fun `test003 printing variable x with assignation variable before`() {
        val cli = CLI()
        val result = cli.test("execute src/test/resources/test003.txt")
        assertEquals("10\n", result.stdout)
    }
    // TODO: @PEPE. Estoy sumando dos variables 10 + 5 y en lugar que de 15 da 105
//    @Test
//    fun `test 004 testing sum of variables`() {
//        val cli = CLI()
//        val result = cli.test("execute src/test/resources/test004.txt")
//        assertEquals("15\n", result.stdout)
//    }

    // TODO : @PEPE. 5*10 NO FUNCIONA.
//    @Test
//    fun `test 005 testing a varaible with multiplication`() {
//        val cli = CLI()
//        val result = cli.test("execute src/test/resources/test005.txt")
//        assertEquals("50\n", result.stdout)
//    }

    // todo : @Pepe no funciona.
//    @Test
//    fun `test 006 testing a println with concatenation`() {
//        val cli = CLI()
//        val result = cli.test("execute src/test/resources/test006.txt")
//        assertEquals("Hello, world!\n", result.stdout)
//    }
// todo : falla.

//    @Test
//    fun `test a declaration and then an assignation` () {
//        val cli = CLI()
//        val result = cli.test("execute src/test/resources/test007.txt")
//        assertEquals("10\n", result.stdout)
//    }

    // todo:
//    @Test
//    fun `test 008 testing a sum of number + string has to return a string concatenated ` (){
//        val cli = CLI()
//        val result = cli.test("execute src/test/resources/test008.txt")
//        assertEquals("25\n", result.stdout)
//    }

    @Test
    fun `test 009 formatter simple format with no json`() {
        val cli = CLI()
        val result = cli.test("formatter src/test/resources/test009.txt")
        assertEquals("let x: number = 8;\n\n", result.stdout)
    }

    @Test
    fun `test010 formatter with my own json`() {
        val cli = CLI()
        val result = cli.test("formatter src/test/resources/test010.txt src/test/resources/MyRules.json")
        assertEquals("let variable: string=\"this is a variable\";\n\n", result.stdout)
    }
    // Formatter funciona el resto de los tests estan en su clase

    @Test
    fun `test weird txt with words separate`() {
        val cli = CLI()
        val result = cli.test("execute src/test/resources/test011.txt")
        assertEquals("10\n", result.stdout)
    }

    @Test
    fun `test formatter a weird text`() {
        val cli = CLI()
        val result = cli.test("formatter src/test/resources/test011.txt")
        assertEquals("let variable: number = 10;\n\nprintln(variable);\n\n", result.stdout)
    }
}
