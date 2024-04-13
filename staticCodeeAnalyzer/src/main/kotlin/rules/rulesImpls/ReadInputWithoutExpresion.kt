package rules.rulesImpls

import org.example.Token
import org.example.Types
import org.example.brokenRule.BrokenRule
import org.example.rules.Rules

class ReadInputWithoutExpresion(private var errorMessage: String = "ReadInputs must not be called with an expresion") : Rules {
    private val brokenRules = mutableListOf<BrokenRule>()

    private fun checkIsReadInput(tokenList: List<Token>): Boolean {
        for (token in tokenList) {
            if (token.getType() == Types.FUNCTION && token.getValue() == "readInput") {
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
            if (checkIsReadInput(tokenList)) {
                if (isAnExpresion(splitTokens(tokenList))) {
                    brokenRules.add(BrokenRule(errorMessage, tokenList[0].getInitialPosition()))
                }
            }
        }
        return brokenRules
    }

    private fun splitTokens(tokens: List<Token>): List<Token> {
        var readInputPosition = 0
        for (token in tokens) {
            if (token.getValue() == "readInput") {
                readInputPosition = tokens.indexOf(token)
            }
        }
        return tokens.subList(readInputPosition + 1, tokens.size)
    }
}
