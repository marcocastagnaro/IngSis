package org.example

class CompositeAbstractSyntaxTree(
    private var token: Token,
    private var left: AbstractSyntaxTree? = null,
    private var right: AbstractSyntaxTree? = null,
) : AbstractSyntaxTree {
    override fun isLeaf(): Boolean {
        return false
    }

    override fun getToken(): Token {
        return this.token
    }

     fun setLeft(build: AbstractSyntaxTree) {
        this.left = build
    }

     fun setRight(build: AbstractSyntaxTree) {
        this.right = build
    }

    override fun getLeft(): AbstractSyntaxTree? {
        return this.left
    }

    override fun getRight(): AbstractSyntaxTree? {
        return this.right
    }
}
