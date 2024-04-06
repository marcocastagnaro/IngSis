package org.example

class AddSemicolonAndNewLinesRule : FormatRule {
    override fun applyRule(tokenList: MutableList<Token>): List<Token> {
        val lastTokenPosition = tokenList[tokenList.size - 1].getFinalPosition()
        val position = Position(lastTokenPosition.getRow(), lastTokenPosition.getColumn() + 1)
        val semicolonAndNewLine = Token(Types.PUNCTUATOR, ";\n", position, position)
        tokenList.add(semicolonAndNewLine)
        return tokenList
    }
}
