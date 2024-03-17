package org.example.AST

import org.example.Token.Token

class NodeBuilder {
    private var value: Token? = null
    private var left: AbstractSyntaxTree? = null
    private var right: AbstractSyntaxTree? = null

    fun setValue(value: Token): NodeBuilder {
        this.value = value
        return this
    }

    fun getValue(): Token? {
        return this.value
    }

    fun setLeft(left: AbstractSyntaxTree): NodeBuilder {
        this.left = left
        return this
    }

    fun setRight(right: AbstractSyntaxTree): NodeBuilder {
        this.right = right
        return this
    }

    fun build(): AbstractSyntaxTree {
        if (value != null && left == null && right == null) return Leaf(value!!)
        return CompositeAbstractSyntaxTree(value!!, left, right)
    }
}