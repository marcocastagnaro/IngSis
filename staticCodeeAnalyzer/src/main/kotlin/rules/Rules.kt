package org.example.staticCodeeAnalyzer

import org.example.Token

interface Rules {
    fun applyRule(tokens: List<List<Token>>): List<BrokenRule>

    fun getRuleName(): String
}
