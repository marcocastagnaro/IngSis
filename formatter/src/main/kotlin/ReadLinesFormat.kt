package org.example

class ReadLinesFormat : FormatRule {
    override fun applyRule(tokenList: List<Token>): List<Token> {
        return findLines(tokenList)
    }

    private fun findLines(tokenList: List<Token>): List<Token> {
        val tokenListWithLines = listOf<Token>().toMutableList()
        tokenListWithLines.add(tokenList[0])
        for (i in 1 until tokenList.size) {
            val previousToken = tokenList[i - 1]
            val actualToken = tokenList[i]
            if (actualToken.getInitialPosition().getRow() - previousToken.getFinalPosition().getRow() > 0) {
                tokenListWithLines.addLast(addNewLines(previousToken, actualToken))
            }
            tokenListWithLines.addLast(actualToken)
        }
        return tokenListWithLines
    }

    private fun addNewLines(
        previousToken: Token,
        actualToken: Token,
    ): Token {
        val tokenValue = ";\n"
        val lines = actualToken.getInitialPosition().getRow() - previousToken.getFinalPosition().getRow()
        for (i in 1 until lines) {
            tokenValue + "\n"
        }
        return Token(Types.PUNCTUATOR, tokenValue, previousToken.getFinalPosition(), actualToken.getInitialPosition())
    }
}
