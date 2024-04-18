package org.example.rules.rulesImpls

import org.example.Token
import org.example.Types
import org.example.brokenRule.BrokenRule
import org.example.rules.Rules

class PrintWithoutExpresion(private var errorMessage: String = "Println must not be called with an expression") : Rules {
    private val brokenRules = mutableListOf<BrokenRule>()

    private fun checkIsPrintln(tokenList: List<Token>): Boolean {
        for (token in tokenList) {
            if (isPrintLn(token)) {
                return true
            }
        }
        return false
    }

    private fun isPrintLn(token: Token) =
        token.getType() == Types.FUNCTION &&
            token.getValue().lowercase() == "println"

    private fun isAnExpresion(tokenList: List<Token>): Boolean {
        for (token in tokenList) {
            if (isExpresionType(token)) {
                return true
            }
        }
        return false
    }

    private fun isExpresionType(token: Token) =
        token.getType() != Types.PUNCTUATOR &&
            token.getType() != Types.LITERAL &&
            token.getType() != Types.IDENTIFIER

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

    override fun getRuleName(): String {
        return "PrintWithoutExpresion"
    }
}
