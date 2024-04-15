package org.example

class ConditionalLeaf(private val token: Token, private val body: List<AbstractSyntaxTree>) : AbstractSyntaxTree {
    override fun isLeaf(): Boolean {
        return true
    }

    override fun getToken(): Token {
        return this.token
    }

    override fun getBody(): List<AbstractSyntaxTree> {
        return this.body
    }
}
