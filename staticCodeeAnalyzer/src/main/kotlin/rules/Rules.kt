package org.example.rules

import org.example.Token
import org.example.brokenRule.BrokenRule

interface Rules {
    fun applyRule(tokens: List<List<Token>>): List<BrokenRule>

    fun getRuleName(): String
}
