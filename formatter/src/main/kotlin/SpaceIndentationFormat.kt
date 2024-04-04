package org.example

class SpaceIndentationFormat : FormatRule {
    override fun applyRule(tokenList: List<Token>): List<Token> {
        return eliminateIndentationSpaces(tokenList)
    }

    fun eliminateIndentationSpaces(tokenList: List<Token>): List<Token> {
        val newList = mutableListOf<Token>()
        newList.add(tokenList[0])
        for (i in 1 until tokenList.size) {
            val actualToken = tokenList[i]
            val previousToken = tokenList[i - 1]
            if (actualToken.getInitialPosition().getRow() - previousToken.getInitialPosition().getRow() > 0) {
                newList.add(eliminateSpaces(actualToken))
            } else {
                newList.add(actualToken)
            }
        }
        return newList
    }

    private fun eliminateSpaces(token: Token): Token {
        val spaces = token.getValue().takeWhile { it == ' ' }
        val newValue = token.getValue().substring(spaces.length)
        val newFinalPosition = Position(token.getFinalPosition().getRow(), token.getFinalPosition().getColumn() - spaces.length)
        val newToken = Token(token.getType(), newValue, token.getInitialPosition(), newFinalPosition)
        return newToken
    }
}
