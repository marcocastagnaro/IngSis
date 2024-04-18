package org.example.formatter

import org.example.Position
import org.example.Token
import org.example.Types

class ConditionalFormatter(val indentNumber: Int) : FormatRule {
    override fun applyRule(tokenList: MutableList<Token>): List<Token> {
        var conditionalTokenIndex = 0
        for (token in tokenList) {
            if (isConditional(token)) {
                conditionalTokenIndex = tokenList.indexOf(token)
            }
        }
        if (isNotConditional(conditionalTokenIndex)) {
            return tokenList
        }
        return formatedTokenList(tokenList, conditionalTokenIndex)
    }

    private fun isOpeningBracketIndex(token: Token) = token.getType() == Types.PUNCTUATOR && token.getValue() == "{"

    private fun isNotConditional(conditionalTokenIndex: Int): Boolean {
        return conditionalTokenIndex == 0
    }

    private fun formatedTokenList(
        tokenList: MutableList<Token>,
        conditionalTokenIndex: Int,
    ): List<Token> {
        val openingBrackets = tokenList.filter { it.getType() == Types.PUNCTUATOR && it.getValue() == "{" }
        val closingBrackets = tokenList.filter { it.getType() == Types.PUNCTUATOR && it.getValue() == "}" }
        if (openingBrackets.size != closingBrackets.size) {
            throw Exception("Opening and closing brackets do not match")
        }
        var newTokenList: MutableList<Token> = mutableListOf()
        for ((index, bracket) in openingBrackets.withIndex()) {
            val openingPosition = tokenList.indexOf(bracket)
            val closingPosition = tokenList.indexOf(closingBrackets[index])
        }

        return newTokenList
    }

    private fun newToken(token: Token): Token {
        val tokenRow = token.getInitialPosition().getRow()
        val tokenInitalColumn = token.getInitialPosition().getColumn() + indentNumber
        val tokenFinalColumn = token.getFinalPosition().getColumn() + indentNumber
        val initialPosition = Position(tokenRow, tokenInitalColumn)
        val finalPosition = Position(tokenRow, tokenFinalColumn)
        return Token(token.getType(), token.getValue(), initialPosition, finalPosition)
    }

    private fun isClosingBracket(token: Token): Boolean {
        return token.getType() == Types.PUNCTUATOR && token.getValue() == "}"
    }

    private fun isConditional(token: Token): Boolean {
        return token.getType() == Types.CONDITIONAL && token.getValue() == "if" || token.getValue() == "else"
    }
}
