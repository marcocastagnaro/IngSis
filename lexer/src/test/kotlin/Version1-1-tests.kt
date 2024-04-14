package org.example

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class `Version1-1-tests` {
    val lexer = Lexer()

    @Test
    fun `test 001- should recognize the const keyword const`() {
        val input = "const"
        val expected = listOf(Token(Types.KEYWORD, "const", Position(0, 0), Position(0, 4)))
        val result = lexer.execute(input)
        assertEquals(result[0].getType(), expected[0].getType())
    }

    @Test
    fun `test 002- should recognize if and else as a keyword and {} as punctuators`() {
        val input = "if { } else { }" // TODO this test fails when there is nos space between brackets {}
        val expected =
            listOf(
                Token(Types.CONDITIONAL, "if", Position(0, 0), Position(0, 1)),
                Token(Types.PUNCTUATOR, "{", Position(0, 4), Position(0, 4)),
                Token(Types.PUNCTUATOR, "}", Position(0, 5), Position(0, 5)),
                Token(Types.CONDITIONAL, "else", Position(0, 7), Position(0, 10)),
                Token(Types.PUNCTUATOR, "{", Position(0, 12), Position(0, 12)),
                Token(Types.PUNCTUATOR, "}", Position(0, 13), Position(0, 13)),
            )
        val result = lexer.execute(input)
        assertEquals(result[0].getType(), expected[0].getType())
        assertEquals(result[1].getType(), expected[1].getType())
        assertEquals(result[2].getType(), expected[2].getType())
        assertEquals(result[3].getType(), expected[3].getType())
        assertEquals(result[4].getType(), expected[4].getType())
        assertEquals(result[5].getType(), expected[5].getType())
    }

    @Test
    fun `test 003- should recognize readInput as function`() {
        val input = "readInput"
        val expected = listOf(Token(Types.FUNCTION, "readInput", Position(0, 0), Position(0, 8)))
        val result = lexer.execute(input)
        assertEquals(result[0].getType(), expected[0].getType())
    }

    @Test
    fun `test004 test if with conditionand result `() {
        val input = "if (\"true\"){ println(\"hola\"); x=5; let n :string = hola; } else { (\"chau\"); }"
        val result = lexer.execute(input)
        System.out.println(result.map { it.getValue() })
    }
}
/*
nodo conditional (if, un else)
if -> izq
    izq -> condicion (true)
    der -> body (println)
        der -> hola
            der -> =
                izq -> x
                der -> 5
                    der -> =
                        etc (recursivo con el parser)

else -> der

clase conditionalState
isreading if
isreading else

 */
