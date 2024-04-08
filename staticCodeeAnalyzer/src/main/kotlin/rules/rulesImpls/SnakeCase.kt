package org.example.rules.rulesImpls

import org.example.Token
import org.example.Types
import org.example.brokenRule.BrokenRule
import org.example.rules.Rules

class SnakeCase(var errorMessage: String = "The following identifier must be in snake_case") : Rules {
    private val brokenRules = mutableListOf<BrokenRule>()

    private fun isSnakeCase(token: Token): Boolean {
        val tokenString = token.getValue()
        for (i in 1 until tokenString.length - 1) {
            val currentChar = tokenString[i]
            // Verifica si el carácter actual es una letra en mayúscula
            if (currentChar.isUpperCase()) {
                return false
            }
        }
        return true
    }

    override fun applyRule(tokens: List<List<Token>>): List<BrokenRule> {
        for (line in tokens) {
            for (token in line) {
                if (token.getType() == Types.IDENTIFIER) {
                    if (!isSnakeCase(token)) {
                        brokenRules.add(BrokenRule(errorMessage, token.getInitialPosition()))
                    }
                }
            }
        }
        return brokenRules
    }
}
