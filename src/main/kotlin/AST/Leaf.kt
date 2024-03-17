package org.example.AST

import org.example.Token.Token

class Leaf(private var value: Token) : AbstractSyntaxTree {
    override fun isLeaf(): Boolean {
        return true
    }

    override fun getToken(): Token {
        return this.value
    }
}
