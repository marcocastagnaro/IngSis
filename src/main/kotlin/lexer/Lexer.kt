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

    fun split(string: String): List<SplitToken> { // [absdf f  k]
        val result = ArrayList<SplitToken>()
        var columnCounter = 0
        var rowCounter = 0
        var wordStartingColumn = 0
        var actualWord = ""
        for (char in string) {
            if (char == '\n') {
                if (actualWord != "") {
                    result.add(
                        createToken(actualWord, rowCounter, wordStartingColumn, columnCounter-1)
                    )
                }
                rowCounter += 1
                columnCounter = 0
                wordStartingColumn = 0
                actualWord = ""
            }
            else if (char == ' ') {
                if (actualWord == "") {
                    columnCounter += 1
                    wordStartingColumn += 1
                    continue
                }
                result.add(
                    createToken(actualWord, rowCounter, wordStartingColumn, columnCounter-1)
                )
                columnCounter += 1
                wordStartingColumn = columnCounter
                actualWord = ""
            }
            else {
                actualWord += char
                columnCounter += 1
            }
        }
        if (actualWord != "") {
            result.add(
                createToken(actualWord, rowCounter, wordStartingColumn, columnCounter-1)
            )
        }
        return result
    }

    private fun createToken(word : String, row : Int, firsCol : Int, lastCol: Int) : SplitToken {
        val startingPos = Position(row, firsCol)
        val endingPos = Position(row, lastCol)
        return SplitToken(startingPos, endingPos, word)
    }
}