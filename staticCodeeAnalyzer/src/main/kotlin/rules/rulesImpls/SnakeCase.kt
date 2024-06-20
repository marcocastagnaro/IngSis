package org.example.staticCodeeAnalyzer

import org.example.Token
import org.example.Types
import org.example.staticCodeeAnalyzer.BrokenRule
import org.example.staticCodeeAnalyzer.Rules

class SnakeCase(var errorMessage: String = "The following identifier must be in snake_case") : Rules {
    private val brokenRules = mutableListOf<BrokenRule>()

    private fun isSnakeCase(token: Token): Boolean {
        val tokenString = token.getValue()
        for (i in 1 until tokenString.length - 1) {
            val currentChar = tokenString[i]
            if (currentChar.isUpperCase()) {
                return false
            }
        }
        return true
    }

    override fun applyRule(tokens: List<List<Token>>): List<BrokenRule> {
        for (line in tokens) {
            for (token in line) {
                if (isIdentifier(token)) {
                    if (!isSnakeCase(token)) {
                        brokenRules.add(BrokenRule(errorMessage, token.getInitialPosition()))
                    }
                }
            }
        }
        return brokenRules
    }

    private fun isIdentifier(token: Token) = token.getType() == Types.IDENTIFIER

    override fun getRuleName(): String {
        return "SnakeCase"
    }
}
