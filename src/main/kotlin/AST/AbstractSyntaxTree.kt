package org.example.AST

import org.example.Token.Token

class AbstractSyntaxTree(
    private val value: Token,
    private val left: AbstractSyntaxTree? = null,
    private val right: AbstractSyntaxTree? = null,
) {
    fun left(): AbstractSyntaxTree? {
        return left
    }

    fun right(): AbstractSyntaxTree? {
        return right
    }

    fun isLeaf(): Boolean {
        return left == null && right == null
    }

    fun getValue(): Token {
        return value
    }
}
