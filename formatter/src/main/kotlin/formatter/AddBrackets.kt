package org.example.formatter

import org.example.Token
import org.example.Types

class AddBrackets : FormatRule {
    override fun applyRule(tokenList: MutableList<Token>): List<Token> {
        if (tokenList[0].getValue() == "println") {
            val openBracket = Token(Types.PUNCTUATOR, "(", tokenList[0].getInitialPosition(), tokenList[0].getInitialPosition())
            val closeBracket =
                Token(
                    Types.PUNCTUATOR,
                    ")",
                    tokenList[tokenList.size - 1].getFinalPosition(),
                    tokenList[tokenList.size - 1].getFinalPosition(),
                )
            tokenList.add(1, openBracket)
            tokenList.add(closeBracket)
        } else if (tokenList.any { it.getType() == Types.FUNCTION && it.getValue() != "println" }) {
            val indexOfFunction = tokenList.indexOfFirst { it.getType() == Types.FUNCTION && it.getValue() != "println" }
            val openBracket = Token(Types.PUNCTUATOR, "(", tokenList[0].getInitialPosition(), tokenList[0].getInitialPosition())
            val closeBracket =
                Token(
                    Types.PUNCTUATOR,
                    ")",
                    tokenList[tokenList.size - 1].getFinalPosition(),
                    tokenList[tokenList.size - 1].getFinalPosition(),
                )
            tokenList.add(indexOfFunction + 1, openBracket)
            tokenList.add(closeBracket)
        } else if (tokenList.any { it.getType() == Types.FUNCTION && it.getValue() == "println" }) {
            addBrackets(tokenList)
        }
        return tokenList
    }

    private fun addBrackets(tokenList: MutableList<Token>): List<Token> {
        val functions = tokenList.filter { it.getType() == Types.FUNCTION || Regex("^;.*\n").matches(it.getValue()) }
        var index = 0
        while (index < functions.size) {
            val start = tokenList.find { it == functions[index] }!!
            val end = tokenList.find { it == functions[index + 1] }!!
            val openBracket = Token(Types.PUNCTUATOR, "(", start.getInitialPosition(), start.getInitialPosition())
            val closeBracket = Token(Types.PUNCTUATOR, ")", end.getFinalPosition(), end.getFinalPosition())
            tokenList.add(tokenList.indexOf(start) + 1, openBracket)
            tokenList.add(tokenList.indexOf(end), closeBracket)
            index += 2
        }
        return tokenList
    }
}
