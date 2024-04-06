package org.example.formatter

import org.example.Token
import org.example.Types

class NoSpacesInAssignationRule : FormatRule {
    override fun applyRule(tokenList: MutableList<Token>): List<Token> {
        var index = 0
        for (token in tokenList) {
            if (token.getValue() == "=") {
                removeSpaceToken(tokenList, index + 1)
                removeSpaceToken(tokenList, index - 1)
                break
            }
            index++
        }
        return tokenList
    }

    private fun removeSpaceToken(
        tokenList: MutableList<Token>,
        i: Int,
    ) {
        if (tokenList[i].getType() == Types.SPACE) tokenList.removeAt(i)
    }
}
