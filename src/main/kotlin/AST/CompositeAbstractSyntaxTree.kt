package org.example.AST
import org.example.Token.Token

class CompositeAbstractSyntaxTree(
    private var value: Token,
    private var left: AbstractSyntaxTree? = null,
    private var right: AbstractSyntaxTree? = null,
) : AbstractSyntaxTree {
    override fun isLeaf(): Boolean {
        return false
    }

    override fun getValue(): Token {
        return this.value
    }

    override fun getLeft(): AbstractSyntaxTree? {
        return this.left
    }

    override fun getRight(): AbstractSyntaxTree? {
        return this.right
    }
}
