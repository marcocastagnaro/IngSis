package org.example

class Leaf(private var token: Token) : AbstractSyntaxTree {
    override fun isLeaf(): Boolean {
        return true
    }

    override fun getToken(): Token {
        return this.token
    }

}
