package org.example.formatter

import org.example.Token
import org.example.Types

class NewLinesBeforePrint(val ammount: Int = 1) : FormatRule {
    override fun applyRule(tokenList: MutableList<Token>): List<Token> {
        if (tokenList[0].getType() == Types.FUNCTION) {
            val newLineToken =
                Token(Types.SPACE, "\n".repeat(ammount), tokenList[0].getInitialPosition(), tokenList[0].getInitialPosition())
            tokenList.addFirst(newLineToken)
        }
        return tokenList
    }
}
