package org.example.formatter

import org.example.Token

class ConditionalFormatter : FormatRule {
    override fun applyRule(tokenList: MutableList<Token>): List<Token> {
        addBrackets(tokenList)
        return tokenList
    }

    private fun addBrackets(tokenList: MutableList<Token>) {
    }
}
