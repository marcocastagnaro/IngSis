package org.example.Token.nodes

import org.example.Token.Token

class Leaf(private var value: Token) : Node {
    override fun getValue(): Token {
        return this.value
    }
}
