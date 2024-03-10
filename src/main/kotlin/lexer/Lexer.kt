package org.example.lexer

import org.example.Token.Token

class Lexer (private var map : Mapper) {
    fun lex (string : String) : List<Token> {
        var result = ArrayList<Token>()
        var array = string.split(" ")
        var countPosition = 0
        for( value in array) {
            var type = map.getMap().get(value)
            if (type != null){
                var token = Token (type, value, )
                result.add(token)
            }
        }
    }
}