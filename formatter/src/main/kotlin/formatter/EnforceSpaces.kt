package org.example.formatter

import org.example.Position
import org.example.Token
import org.example.Types

class EnforceSpaces(val ammount: Int = 0, val defaultSpaces: Boolean = (ammount == 0)) : FormatRule {
    override fun applyRule(tokenList: MutableList<Token>): List<Token> {
        if (tokenList[0].getType() == Types.FUNCTION) return tokenList
        if (defaultSpaces) {
            return addNormalTokenSpaces(tokenList)
        } else {
            return enforceSpacing(tokenList)
        }
    }

    private fun enforceSpacing(tokenList: List<Token>): List<Token> {
        val listWithSpace = mutableListOf<Token>()
        listWithSpace.add(tokenList[0])
        for (i in 1 until tokenList.size) {
            val initialPosition = Position(tokenList[i].getInitialPosition().getRow(), tokenList[i].getInitialPosition().getColumn() - 3)
            val finalPosition = Position(tokenList[i].getInitialPosition().getRow(), tokenList[i].getInitialPosition().getColumn() - 1)
            val spaceToken = Token(Types.SPACE, " ".repeat(ammount), initialPosition, finalPosition)
            listWithSpace.add(spaceToken)
            listWithSpace.add(tokenList[i])
        }
        return listWithSpace
    }

    private fun addNormalTokenSpaces(tokenList: List<Token>): List<Token> {
        val listWithSpace = mutableListOf<Token>()
        listWithSpace.add(tokenList[0])
        for (i in 1 until tokenList.size) {
            val before = tokenList[i - 1]
            val actual = tokenList[i]
            val distance = getDistanceBetweenTokens(before, actual)
            if (distance > 1) {
                val initialPosition = Position(before.getFinalPosition().getRow(), before.getFinalPosition().getColumn() + 1)
                val finalPosition = Position(actual.getInitialPosition().getRow(), actual.getInitialPosition().getColumn() - 1)
                val spaceToken = Token(Types.SPACE, " ".repeat(distance - 1), initialPosition, finalPosition)
                listWithSpace.add(spaceToken)
            }
            listWithSpace.add(actual)
        }
        return listWithSpace
    }

    private fun getDistanceBetweenTokens(
        before: Token,
        actual: Token,
    ): Int {
        return actual.getInitialPosition().getColumn() - before.getFinalPosition().getColumn()
    }
}
