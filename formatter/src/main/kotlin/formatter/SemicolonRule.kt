package org.example.formatter

import org.example.Position
import org.example.Token
import org.example.Types

class SemicolonRule : FormatRule {
    override fun applyRule(tokenList: MutableList<Token>): List<Token> {
        val lastTokenPosition = tokenList[tokenList.size - 1].getFinalPosition()
        val position = Position(lastTokenPosition.getRow(), lastTokenPosition.getColumn() + 1)
        val semicolonAndNewLine = Token(Types.PUNCTUATOR, ";\n", position, position)
        tokenList.add(semicolonAndNewLine)
        return tokenList
    }
}
