package org.example.Token.nodes

import org.example.Token.Token

class NodeBuilder {
    private var value: Token? = null
    private var left: Node? = null
    private var right: Node? = null

    fun setValue(value: Token): NodeBuilder {
        this.value = value
        return this
    }

    fun getValue(): Token? {
        return this.value
    }

    fun setLeft(left: Node): NodeBuilder {
        this.left = left
        return this
    }

    fun setRight(right: Node): NodeBuilder {
        this.right = right
        return this
    }

    fun build(): Node {
        return CompositeNode(value!!, left, right)
    }
}