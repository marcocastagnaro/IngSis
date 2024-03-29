package org.example

class ReadSpacesFormat : FormatRule {
    override fun applyRule(tokenList: List<Token>): List<Token> {
        return findSpaces(tokenList)
    }

    private fun findSpaces(tokenList: List<Token>): List<Token> {
        var index = 1
        val newTokenList: MutableList<Token> = listOf<Token>().toMutableList()
        while (index < tokenList.size) {
            val currentToken = tokenList[index]
            val previousToken = tokenList[index - 1]
            newTokenList.addLast(previousToken)
            if (hasSpaceSeparation(currentToken, previousToken)) {
                newTokenList.addLast(addSpaceTokens(previousToken, currentToken))
            }
            newTokenList.addLast(currentToken)
            index++
        }
        return newTokenList
    }

    private fun addSpaceTokens(
        previousToken: Token,
        currentToken: Token,
    ): Token {
        val difference = currentToken.getInitialPosition().getColumn() - previousToken.getFinalPosition().getColumn() - 1

        var spaces = ""
        for (i in 0 until difference) {
            spaces += " "
        }
        return Token(Types.PUNCTUATOR, spaces, previousToken.getFinalPosition(), currentToken.getInitialPosition())
    }

    private fun hasSpaceSeparation(
        currentToken: Token,
        previousToken: Token,
    ): Boolean {
        val diff = currentToken.getInitialPosition().getColumn() - previousToken.getFinalPosition().getColumn() - 1
        return diff > 0
    }
}
