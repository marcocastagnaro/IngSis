package org.example

class ConditionalNode(
    private val token: Token,
    private val right: AbstractSyntaxTree?,
    private val left: AbstractSyntaxTree,
) : AbstractSyntaxTree {
    override fun getLeft(): AbstractSyntaxTree {
        return left
    }

    override fun getRight(): AbstractSyntaxTree? {
        return right
    }

    override fun isLeaf(): Boolean {
        return false
    }

    override fun getToken(): Token {
        return this.token
    }
}
