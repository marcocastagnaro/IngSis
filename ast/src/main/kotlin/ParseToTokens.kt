package org.example

interface ParseToTokens {
    fun parseToTokens(root: AbstractSyntaxTree): MutableList<Token>
}
