package org.example.rules.rulesImpls

import org.example.Token
import org.example.Types
import org.example.brokenRule.BrokenRule
import org.example.rules.Rules

class PrintWithoutExpresion(private var errorMessage: String = "Println must not be called with an expression") : Rules {
    private val brokenRules = mutableListOf<BrokenRule>()

    private fun checkIsPrintln(tokenList: List<Token>): Boolean {
        for (token in tokenList) {
            if (token.getType() == Types.FUNCTION && token.getValue().lowercase() == "println") {
                return true
            }
        }
        return false
    }

    private fun isAnExpresion(tokenList: List<Token>): Boolean {
        for (token in tokenList) {
            if (token.getType() != Types.PUNCTUATOR && token.getType() != Types.LITERAL && token.getType() != Types.IDENTIFIER) {
                return true
            }
        }
        return false
    }

    override fun applyRule(tokens: List<List<Token>>): List<BrokenRule> {
        for (tokenList in tokens) {
            if (checkIsPrintln(tokenList)) {
                var tokenSublist = tokenList.subList(1, tokenList.size)
                if (isAnExpresion(tokenSublist)) {
                    brokenRules.add(BrokenRule(errorMessage, tokenList[0].getInitialPosition()))
                }
            }
        }
        return brokenRules
    }
}
