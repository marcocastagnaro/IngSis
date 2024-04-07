package org.example.rules.rulesImpls

import org.example.brokenRule.BrokenRule
import org.example.Token
import org.example.rules.Rules

class SnakeCase (var errorMessage: String = "All identifiers must be in snake_case") : Rules {
    private val brokenRules = mutableListOf<BrokenRule>()

   override fun applyRule(tokens: List<List<Token>>): List<BrokenRule> {
        TODO("Not yet implemented")
    }



}
