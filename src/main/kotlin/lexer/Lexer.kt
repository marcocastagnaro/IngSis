package org.example.lexer

import org.example.Token.Position
import org.example.Token.Token

class Lexer (private var map : Mapper) {
    fun lex (string : String) : List<Token> {
        var result = ArrayList<Token>()
        var array = split(string)
        for( value in array) {
            var type = map.getMap().get(value.getValue())
            if (type != null){
                var token = Token (type, value.getValue(), value.getInitialPosition(), value.getFinalPosition())
                result.add(token)
            }
        }
        return result
    }
    fun split (string : String) : List<SplitToken>  {
        val finalList = ArrayList<SplitToken>()
        val caracteres = string.split("") //[a,b,c, ,d,e,f, ,g // ]
        var countRow = 0
        var countColumn = 0
        var value = ""
        var initialPositionColumn = 0
        for (caracter in caracteres){
            if (caracter == "\n"){
                finalList.add(SplitToken(Position(countRow, initialPositionColumn),Position(countRow, countColumn), value))
                countRow += 1
                countColumn = 0
                initialPositionColumn = 0
            }
            if (caracter != " ") {
                countColumn +=1
                value += caracter
            }
            else if (caracter == " "){
                finalList.add(SplitToken(Position(countRow, initialPositionColumn),Position(countRow, countColumn), value))
                initialPositionColumn = countColumn + 2
            }
        }
        return finalList
    }
}