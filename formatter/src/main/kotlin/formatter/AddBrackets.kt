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
        }
        return tokenList
    }
}
