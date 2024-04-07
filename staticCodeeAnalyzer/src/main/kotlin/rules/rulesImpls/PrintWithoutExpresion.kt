package org.example.rules.rulesImpls

import org.example.brokenRule.BrokenRule
import org.example.Token
import org.example.Types
import org.example.rules.Rules

class PrintWithoutExpresion(private var errorMessage: String = "Printlns must not be called with an expresion") : Rules {
    private val brokenRules = mutableListOf<BrokenRule>()

    private fun checkIsPrintln(tokenList: List<Token>): Boolean{
        for (token in tokenList) {
            if (token.getType() == Types.FUNCTION ) {
                return true
            }
        }
        return false
    }

    private fun isAnExpresion(tokenList: List<Token>): Boolean {
        var linter_numbres = 0
        for (token in tokenList) {
            if (token.getType() == Types.LITERAL){
                linter_numbres += 1
            }
        }
        return linter_numbres > 1
    }

    override fun applyRule(tokens: List<List<Token>>): List<BrokenRule> {
        for (tokenList in tokens) {
            if(checkIsPrintln(tokenList)) {
                    if (isAnExpresion(tokenList)) {
                        brokenRules.add(BrokenRule(errorMessage, tokenList[0].getInitialPosition()))
                    }
                }
        }
        return brokenRules
    }


}
