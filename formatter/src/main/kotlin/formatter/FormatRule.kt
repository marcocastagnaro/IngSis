package org.example.formatter

import org.example.Token

interface FormatRule {
    fun applyRule(tokenList: MutableList<Token>): List<Token> // Returns a token List and applies the rules in tokens
    // for example in order to add spaces you add a token with
    // space as its string
}
