package org.example.rules.rulesImpls

import org.example.Token
import org.example.Types
import org.example.brokenRule.BrokenRule
import org.example.rules.Rules

class CamelCase(private var errorMessage: String = "The following identifier must be in camelCase") : Rules {
    private val brokenRules = mutableListOf<BrokenRule>()

    private fun isCamelCase(token: Token): Boolean {
        val parts = token.getValue().split("_")
        return parts.size <= 1
    }

    override fun applyRule(tokens: List<List<Token>>): List<BrokenRule> {
        for (line in tokens) {
            for (token in line) {
                if (token.getType() == Types.IDENTIFIER) {
                    if (!isCamelCase(token)) {
                        brokenRules.add(BrokenRule(errorMessage, token.getInitialPosition()))
                    }
                }
            }
        }
        return brokenRules
    }
}
