package org.example.rules.rulesImpls

import org.example.brokenRule.BrokenRule
import org.example.Token
import org.example.rules.Rules

class PrintWithoutExpresion(private var errorMessage: String = "Printlns must not be called with an expresion") : Rules {
    private val brokenRules = mutableListOf<BrokenRule>()

    override fun applyRule(tokens: List<List<Token>>): List<BrokenRule> {
        TODO("Not yet implemented")
    }

}
