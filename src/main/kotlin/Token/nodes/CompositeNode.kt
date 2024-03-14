package org.example.Token.nodes
import org.example.Token.Token

class CompositeNode(
    private var value: Token,
    private var left: Node? = null,
    private var right: Node? = null,
) : Node {
    override fun getValue(): Token {
        return this.value
    }

    override fun getLeft(): Node? {
        return this.left
    }

    override fun getRight(): Node? {
        return this.right
    }
}
