package org.example.rules

import org.example.brokenRule.BrokenRule
import org.example.Token

interface Rules {

    fun applyRule(tokens: List<List<Token>>) : List<BrokenRule>


}