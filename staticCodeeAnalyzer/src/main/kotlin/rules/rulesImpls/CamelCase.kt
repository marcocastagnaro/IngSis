package org.example.staticCodeeAnalyzer

import org.example.Token
import org.example.Types

class CamelCase(private var errorMessage: String = "The following identifier must be in camelCase") : Rules {
    private val brokenRules = mutableListOf<BrokenRule>()

    private fun isCamelCase(token: Token): Boolean {
        val parts = token.getValue().split("_")
        return parts.size <= 1
    }

    override fun applyRule(tokens: List<List<Token>>): List<BrokenRule> {
        for (line in tokens) {
            for (token in line) {
                if (isIdentifier(token)) {
                    if (!isCamelCase(token)) {
                        brokenRules.add(BrokenRule(errorMessage, token.getInitialPosition()))
                    }
                }
            }
        }
        return brokenRules
    }

    private fun isIdentifier(token: Token) = token.getType() == Types.IDENTIFIER

    override fun getRuleName(): String {
        return "CamelCase"
    }
}
