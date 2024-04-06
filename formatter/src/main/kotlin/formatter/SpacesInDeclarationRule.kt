package org.example.formatter

import org.example.Token
import org.example.Types

class SpacesInDeclarationRule(val spaceBefore: Boolean = false, val spaceAfter: Boolean = true) : FormatRule {
    override fun applyRule(tokenList: MutableList<Token>): List<Token> {
        var isDeclarator = false
        var index = 0
        for (i in 0 until tokenList.size) {
            val token = tokenList[index]
            if (Regex(":").containsMatchIn(token.getValue())) {
                isDeclarator = true
            }
            if (isDeclarator) {
                if (tokenList[index - 1].getType() != Types.SPACE && spaceBefore) {
                    val spaceToken = Token(Types.SPACE, " ", tokenList[index - 1].getFinalPosition(), tokenList[index].getInitialPosition())
                    tokenList.add(index, spaceToken)
                    index++
                } else if (tokenList[index - 1].getType() == Types.SPACE && !spaceBefore) {
                    tokenList.removeAt(index - 1)
                    index--
                }
                if (tokenList[index + 1].getType() != Types.SPACE && spaceAfter) {
                    val spaceToken = Token(Types.SPACE, " ", tokenList[index].getFinalPosition(), tokenList[index + 1].getInitialPosition())
                    tokenList.add(index + 1, spaceToken)
                } else if (tokenList[index + 1].getType() == Types.SPACE && !spaceAfter) {
                    tokenList.removeAt(index + 1)
                }
                break
            }
            index++
        }
        return tokenList
    }
}
