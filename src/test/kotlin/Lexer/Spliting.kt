package Lexer

import org.example.lexer.Lexer
import org.example.lexer.Mapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Spliting {
    val mapper = Mapper(mapOf())
    val lex = Lexer(mapper)

    @Test
    fun simpleSplit() {
        val result = lex.split("a b c")
        Assertions.assertEquals(3, result.size)
        println(result.size)
    }
}

fun main() {
    val mapper = Mapper(mapOf())
    val lex = Lexer(mapper)
    val result = lex.split("a \nhola")
    println(result.size)
    for (i in result) {
        println(
            i.getValue() + " | ("
            + i.getInitialPosition().getRow() + ", " + i.getInitialPosition().getColumn()
            + ") | ("
            + i.getFinalPosition().getRow() + ", " + i.getFinalPosition().getColumn() + ")"
        )
    }
}
