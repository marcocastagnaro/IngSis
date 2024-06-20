package org.example.staticCodeeAnalyzer

import org.example.Token
import org.example.Types

class ReadInputWithoutExpresion(private var errorMessage: String = "ReadInputs must not be called with an expresion") : Rules {
    private val brokenRules = mutableListOf<BrokenRule>()

    private fun checkIsReadInput(tokenList: List<Token>): Boolean {
        for (token in tokenList) {
            if (isReadInput(token)) {
                return true
            }
        }
        return false
    }

    private fun isReadInput(token: Token) = token.getType() == Types.FUNCTION && token.getValue() == "readInput"

    private fun isAnExpression(tokenList: List<Token>): Boolean {
        for (token in tokenList) {
            if (isExpresionType(token)) {
                return true
            }
        }
        return false
    }

    private fun isExpresionType(token: Token) =
        token.getType() != Types.PUNCTUATOR && token.getType() != Types.LITERAL && token.getType() != Types.IDENTIFIER

    override fun applyRule(tokens: List<List<Token>>): List<BrokenRule> {
        for (tokenList in tokens) {
            if (checkIsReadInput(tokenList)) {
                if (isAnExpression(splitTokens(tokenList))) {
                    brokenRules.add(BrokenRule(errorMessage, tokenList[0].getInitialPosition()))
                }
            }
        }
        return brokenRules
    }

    override fun getRuleName(): String {
        return "ReadInputWithoutExpresion"
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
